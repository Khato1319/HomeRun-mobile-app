package com.hci.homerunapp.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceState {

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("mode")
    @Expose
    private String mode;

    // AC
    @SerializedName("temperature")
    @Expose
    private int temperature;

    @SerializedName("verticalSwing")
    @Expose
    private String verticalSwing;

    @SerializedName("horizontalSwing")
    @Expose
    private String horizontalSwing;

    @SerializedName("fanSpeed")
    @Expose
    private String fanSpeed;

    // Vacuum
    @SerializedName("batteryLevel")
    @Expose
    private int batteryLevel;

    @SerializedName("location")
    @Expose
    private RemoteDeviceRoom location;

    // Lamp
    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("brightness")
    @Expose
    private int brightness;

    // Blinds
    @SerializedName("level")
    @Expose
    private int level;

    @SerializedName("currentLevel")
    @Expose
    private int currentLevel;

    // Oven
    @SerializedName("heat")
    @Expose
    private String heat;

    @SerializedName("grill")
    @Expose
    private String grill;

    @SerializedName("convection")
    @Expose
    private String convection;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getVerticalSwing() {
        return verticalSwing;
    }

    public void setVerticalSwing(String verticalSwing) {
        this.verticalSwing = verticalSwing;
    }

    public String getHorizontalSwing() {
        return horizontalSwing;
    }

    public void setHorizontalSwing(String horizontalSwing) {
        this.horizontalSwing = horizontalSwing;
    }

    public String getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(String fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public RemoteDeviceRoom getLocation() {
        return location;
    }

    public void setLocation(RemoteDeviceRoom location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getGrill() {
        return grill;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

    public String getConvection() {
        return convection;
    }

    public void setConvection(String convection) {
        this.convection = convection;
    }
}
