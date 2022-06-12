package com.hci.homerunapp.ui.device;

public class ControlData {
    private int ID;
    private String actionLabel;

    ControlData(int ID, String actionLabel) {
        this.ID = ID;
        this.actionLabel = actionLabel;
    }

    public int getLayout() {
        return ID;
    }

    public String getActionLabel() {
        return actionLabel;
    }
}
