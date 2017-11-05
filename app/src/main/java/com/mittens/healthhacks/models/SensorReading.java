package com.mittens.healthhacks.models;

public class SensorReading {
    private double HR;
    private double SpO2;
    private double age;
    private String sex;
    private double systolicBP;
    private double temperature;
    private String time;

    public SensorReading() {

    }

    public double getHR() {
        return HR;
    }

    public void setHR(double HR) {
        this.HR = HR;
    }

    public double getSpO2() {
        return SpO2;
    }

    public void setSpO2(double spO2) {
        SpO2 = spO2;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getSystolicBP() {
        return systolicBP;
    }

    public void setSystolicBP(double systolicBP) {
        this.systolicBP = systolicBP;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

