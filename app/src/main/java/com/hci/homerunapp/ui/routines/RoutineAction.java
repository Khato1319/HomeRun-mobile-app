package com.hci.homerunapp.ui.routines;

public class RoutineAction {
    String device;
    String deviceType;
    String actionName;
    Object param;
    String room;

    public String getDevice() {
        return device;
    }

    public String getActionName() {
        return actionName;
    }

    public Object getParam() {
        return param;
    }

    public String getRoom() {
        return room;
    }

    public RoutineAction(String device, String deviceType, String actionName, Object param, String room) {
        this.device = device;
        this.deviceType = deviceType;
        this.actionName = actionName;
        this.param = param;
        this.room = room;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
