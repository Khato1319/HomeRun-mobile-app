package com.hci.homerunapp.data.remote.routine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hci.homerunapp.data.remote.device.RemoteDevice;

import java.util.List;

public class RemoteRoutineAction {

    @SerializedName("actionName")
    @Expose
    private String actionName;

    @SerializedName("params")
    @Expose
    private List<Object> params;

    public RemoteDevice getDevice() {
        return device;
    }

    @SerializedName("device")
    @Expose
    private RemoteDevice device;

    public String getActionName() {
        return actionName;
    }

    public List<Object> getParams() {
        return params;
    }
}
