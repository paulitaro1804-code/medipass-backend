package com.medipass.dto.admin;

public class ModuleUsageDto {
    private String name;
    private int value;
    private int percentage;

    public ModuleUsageDto() {}

    public ModuleUsageDto(String name, int value) {
        this.name = name;
        this.value = value;
        this.percentage = value; // default: use value as percentage
    }

    public ModuleUsageDto(String name, int value, int percentage) {
        this.name = name;
        this.value = value;
        this.percentage = percentage;
    }

    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public int getValue() { return value; }
    public void setValue(int v) { this.value = v; }
    public int getPercentage() { return percentage; }
    public void setPercentage(int v) { this.percentage = v; }
}
