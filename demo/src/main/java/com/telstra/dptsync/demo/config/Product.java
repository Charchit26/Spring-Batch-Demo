package com.telstra.dptsync.demo.config;

public final class Product {
    private String technology;
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String onceOff;
    private String annual;
    private String pet;

    public Product(String technology, String v1, String v2, String v3, String v4, String onceOff, String annual, String pet) {
        this.technology = technology;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.onceOff = onceOff;
        this.annual = annual;
        this.pet = pet;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }

    public String getOnceOff() {
        return onceOff;
    }

    public void setOnceOff(String onceOff) {
        this.onceOff = onceOff;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }
}