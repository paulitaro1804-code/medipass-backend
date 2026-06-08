package com.medipass.service;

import com.medipass.dto.lab.*;
import com.medipass.entity.*;
import com.medipass.enums.LabStatus;
import com.medipass.exception.ResourceNotFoundException;
import com.medipass.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabService {

    private final LabResultRepository labResultRepository;
    private final UserRepository userRepository;

    public LabService(LabResultRepository labResultRepository, UserRepository userRepository) {
        this.labResultRepository = labResultRepository;
        this.userRepository = userRepository;
    }

    public LabStatsDto getStats() {
        long pending = labResultRepository.countByEstado(LabStatus.PENDIENTE);
        long urgent = labResultRepository.findByEstado(LabStatus.PENDIENTE).stream()
                .filter(l -> "URGENTE".equals(l.getPrioridad())).count();
        long processing = labResultRepository.countByEstado(LabStatus.EN_PROCESO);
        long published = labResultRepository.findByValidadoTrue().stream()
                .filter(l -> l.getFechaPublicacion() != null
                        && l.getFechaPublicacion().toLocalDate().equals(LocalDate.now()))
                .count();
        long critical = labResultRepository.countByAlertaCriticaTrue();

        return LabStatsDto.builder()
                .pendingOrders(pending)
                .urgentOrders(urgent)
                .processing(processing)
                .publishedToday(published)
                .criticalAlerts(critical)
                .build();
    }

    public List<LabOrderDto> getPendingOrders() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return labResultRepository.findByEstadoOrderByFechaSolicitudAsc(LabStatus.PENDIENTE)
                .stream()
                .map(l -> LabOrderDto.builder()
                        .id(l.getId())
                        .patient(l.getPacienteNombre() != null ? l.getPacienteNombre() : "Paciente")
                        .exam(l.getTipoExamen())
                        .doctor(l.getMedicoNombre() != null ? l.getMedicoNombre() : "Médico")
                        .requested(l.getFechaSolicitud() != null
                                ? l.getFechaSolicitud().format(fmt) : "—")
                        .priority("URGENTE".equals(l.getPrioridad()) ? "urgent" : "normal")
                        .build())
                .toList();
    }

    public List<CriticalAlertDto> getCriticalAlerts() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        return labResultRepository.findByAlertaCriticaTrue()
                .stream()
                .filter(l -> !l.isPacienteNotificado())
                .map(l -> CriticalAlertDto.builder()
                        .id(l.getId())
                        .patient(l.getPacienteNombre() != null ? l.getPacienteNombre() : "Paciente")
                        .exam(l.getTipoExamen())
                        .value(l.getValorResultado() != null ? l.getValorResultado() : "Valor crítico")
                        .normal(l.getRangoNormal() != null ? l.getRangoNormal() : "—")
                        .doctor(l.getMedicoNombre() != null ? l.getMedicoNombre() : "Médico")
                        .time(l.getFechaSolicitud() != null ? l.getFechaSolicitud().format(fmt) : "—")
                        .build())
                .toList();
    }

    public List<PublishedResultDto> getPublishedResults() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return labResultRepository.findByValidadoTrue()
                .stream()
                .map(l -> PublishedResultDto.builder()
                        .id(l.getId())
                        .patient(l.getPacienteNombre() != null ? l.getPacienteNombre() : "Paciente")
                        .exam(l.getTipoExamen())
                        .date(l.getFechaPublicacion() != null
                                ? l.getFechaPublicacion().format(fmt) : "—")
                        .status(l.isAlertaCritica() ? "viewed" : "pending")
                        .build())
                .toList();
    }

    public String notifyDoctor(String alertId) {
        labResultRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada"));
        return "Médico notificado correctamente";
    }

    public String notifyPatient(String alertId) {
        LabResult lab = labResultRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada"));
        lab.setPacienteNotificado(true);
        labResultRepository.save(lab);
        return "Paciente notificado correctamente sobre el valor crítico";
    }

    public String submitResult(String orderId, SubmitResultRequest req) {
        LabResult lab = labResultRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        // Convert request items to entity items
        List<LabResult.ResultItem> items = new ArrayList<>();
        if (req.getItems() != null) {
            for (SubmitResultRequest.ResultItemRequest r : req.getItems()) {
                items.add(new LabResult.ResultItem(
                        r.getParametro(), r.getValor(), r.getUnidad(), r.getRangoNormal(), r.isCritico()));
            }
        }

        lab.setResultItems(items);
        lab.setObservaciones(req.getObservaciones());
        lab.setAlertaCritica(req.isAlertaCritica());
        if (req.getLaboratorioNombre() != null && !req.getLaboratorioNombre().isBlank()) {
            lab.setLaboratorioNombre(req.getLaboratorioNombre());
        }
        lab.setEstado(LabStatus.PUBLICADO);
        lab.setValidado(true);
        lab.setFechaPublicacion(LocalDateTime.now());

        // Keep backward-compat single value fields from first critical item
        items.stream().filter(LabResult.ResultItem::isCritico).findFirst().ifPresent(ci -> {
            lab.setValorResultado(ci.getValor());
            lab.setRangoNormal(ci.getRangoNormal());
        });

        labResultRepository.save(lab);
        return "Resultado ingresado y publicado correctamente";
    }

    public LabResultDetailDto getResultDetail(String id) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LabResult lab = labResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resultado no encontrado"));

        List<LabResultDetailDto.ResultItemDto> items = new ArrayList<>();
        if (lab.getResultItems() != null) {
            for (LabResult.ResultItem ri : lab.getResultItems()) {
                items.add(new LabResultDetailDto.ResultItemDto(
                        ri.getParametro(), ri.getValor(), ri.getUnidad(), ri.getRangoNormal(), ri.isCritico()));
            }
        }

        return LabResultDetailDto.builder()
                .id(lab.getId())
                .patient(lab.getPacienteNombre() != null ? lab.getPacienteNombre() : "Paciente")
                .patientId(lab.getPacienteId())
                .exam(lab.getTipoExamen())
                .doctor(lab.getMedicoNombre() != null ? lab.getMedicoNombre() : "Médico")
                .doctorId(lab.getMedicoSolicitanteId())
                .laboratorioNombre(lab.getLaboratorioNombre() != null ? lab.getLaboratorioNombre() : "Laboratorio MediPass")
                .requested(lab.getFechaSolicitud() != null ? lab.getFechaSolicitud().format(fmt) : "—")
                .published(lab.getFechaPublicacion() != null ? lab.getFechaPublicacion().format(fmt) : "—")
                .items(items)
                .observaciones(lab.getObservaciones())
                .alertaCritica(lab.isAlertaCritica())
                .status(lab.getEstado() != null ? lab.getEstado().name() : "PENDIENTE")
                .build();
    }
}
