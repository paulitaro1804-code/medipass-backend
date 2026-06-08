package com.medipass.config;

import com.medipass.entity.*;
import com.medipass.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataCleanupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataCleanupRunner.class);

    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final AppointmentRepository appointmentRepository;
    private final LabResultRepository labResultRepository;
    private final ClinicalRecordRepository clinicalRecordRepository;
    private final PqrsRepository pqrsRepository;

    public DataCleanupRunner(UserRepository userRepository,
                             PolicyRepository policyRepository,
                             AppointmentRepository appointmentRepository,
                             LabResultRepository labResultRepository,
                             ClinicalRecordRepository clinicalRecordRepository,
                             PqrsRepository pqrsRepository) {
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.appointmentRepository = appointmentRepository;
        this.labResultRepository = labResultRepository;
        this.clinicalRecordRepository = clinicalRecordRepository;
        this.pqrsRepository = pqrsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Set<String> validUserIds = userRepository.findAll()
                .stream().map(User::getId).collect(Collectors.toSet());

        List<Policy> orphanPolicies = policyRepository.findAll().stream()
                .filter(p -> p.getPacienteId() == null || !validUserIds.contains(p.getPacienteId()))
                .collect(Collectors.toList());

        List<Appointment> orphanAppts = appointmentRepository.findAll().stream()
                .filter(a -> a.getPacienteId() == null || !validUserIds.contains(a.getPacienteId()))
                .collect(Collectors.toList());

        List<LabResult> orphanLabs = labResultRepository.findAll().stream()
                .filter(l -> l.getPacienteId() == null || !validUserIds.contains(l.getPacienteId()))
                .collect(Collectors.toList());

        List<ClinicalRecord> orphanClinical = clinicalRecordRepository.findAll().stream()
                .filter(c -> c.getPacienteId() == null || !validUserIds.contains(c.getPacienteId()))
                .collect(Collectors.toList());

        List<Pqrs> orphanPqrs = pqrsRepository.findAll().stream()
                .filter(p -> p.getPacienteId() == null || !validUserIds.contains(p.getPacienteId()))
                .collect(Collectors.toList());

        policyRepository.deleteAllById(orphanPolicies.stream().map(Policy::getId).collect(Collectors.toList()));
        appointmentRepository.deleteAllById(orphanAppts.stream().map(Appointment::getId).collect(Collectors.toList()));
        labResultRepository.deleteAllById(orphanLabs.stream().map(LabResult::getId).collect(Collectors.toList()));
        clinicalRecordRepository.deleteAllById(orphanClinical.stream().map(ClinicalRecord::getId).collect(Collectors.toList()));
        pqrsRepository.deleteAllById(orphanPqrs.stream().map(Pqrs::getId).collect(Collectors.toList()));

        long total = orphanPolicies.size() + orphanAppts.size() + orphanLabs.size()
                   + orphanClinical.size() + orphanPqrs.size();

        if (total > 0) {
            log.info("DataCleanup: {} registros huérfanos eliminados ({} pólizas, {} citas, {} labs, {} historias, {} pqrs)",
                    total, orphanPolicies.size(), orphanAppts.size(),
                    orphanLabs.size(), orphanClinical.size(), orphanPqrs.size());
        }
    }
}
