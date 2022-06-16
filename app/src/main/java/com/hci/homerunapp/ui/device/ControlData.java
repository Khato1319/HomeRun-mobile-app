package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.ui.Data;

import java.io.Serializable;

public class ControlData implements Serializable {
    private int ID;
    private String actionLabel;

    ControlData(int ID, String actionLabel) {
        this.ID = ID;
        this.actionLabel = actionLabel;
    }

    public int getLayoutId() {
        return ID;
    }

    public String getActionLabel() {
        return actionLabel;
    }
}
