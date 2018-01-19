package com;

public class Widget {

    private final String color;
    private final Integer brightness;
    private final Integer contrast;

    public String getColor() {
        return color;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public Integer getContrast() {
        return contrast;
    }

    public Widget(String green, int i, int i1) {
        this.color = green;
        this.brightness = i;
        this.contrast = i1;
    }
}
