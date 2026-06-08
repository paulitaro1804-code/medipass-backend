package com.medipass.dto.support;

public class TicketMetricDto {
    private String day;
    private long abiertos;
    private long resueltos;

    public TicketMetricDto() {}

    public TicketMetricDto(String day, long abiertos, long resueltos) {
        this.day = day;
        this.abiertos = abiertos;
        this.resueltos = resueltos;
    }

    public String getDay() { return day; }
    public void setDay(String v) { this.day = v; }
    public long getAbiertos() { return abiertos; }
    public void setAbiertos(long v) { this.abiertos = v; }
    public long getResueltos() { return resueltos; }
    public void setResueltos(long v) { this.resueltos = v; }
}
