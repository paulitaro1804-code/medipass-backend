package com.medipass.dto.patient;

public class AppointmentDto {
    private String id;
    private String doctor;
    private String specialty;
    private String date;
    private String time;
    private String status;
    private String modalidad;
    private String fechaHoraIso;

    public AppointmentDto() {}

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id, doctor, specialty, date, time, status, modalidad, fechaHoraIso;
        public Builder id(String v) { this.id = v; return this; }
        public Builder doctor(String v) { this.doctor = v; return this; }
        public Builder specialty(String v) { this.specialty = v; return this; }
        public Builder date(String v) { this.date = v; return this; }
        public Builder time(String v) { this.time = v; return this; }
        public Builder status(String v) { this.status = v; return this; }
        public Builder modalidad(String v) { this.modalidad = v; return this; }
        public Builder fechaHoraIso(String v) { this.fechaHoraIso = v; return this; }
        public AppointmentDto build() {
            AppointmentDto d = new AppointmentDto();
            d.id = id; d.doctor = doctor; d.specialty = specialty;
            d.date = date; d.time = time; d.status = status;
            d.modalidad = modalidad; d.fechaHoraIso = fechaHoraIso;
            return d;
        }
    }

    public String getId() { return id; }
    public void setId(String v) { this.id = v; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String v) { this.doctor = v; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String v) { this.specialty = v; }
    public String getDate() { return date; }
    public void setDate(String v) { this.date = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String v) { this.modalidad = v; }
    public String getFechaHoraIso() { return fechaHoraIso; }
    public void setFechaHoraIso(String v) { this.fechaHoraIso = v; }
}
