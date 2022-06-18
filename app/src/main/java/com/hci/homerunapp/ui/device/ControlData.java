package com.hci.homerunapp.ui.device;

import android.content.Context;

import java.io.Serializable;

public class ControlData implements Serializable {
    private int layoutId;
    private String actionLabel, deviceId;
    protected Context context;

    public ControlData(Context context, int layoutId, String actionLabel, String deviceId) {
        this.layoutId = layoutId;
        this.actionLabel = actionLabel;
        this.context = context;
        this.deviceId = deviceId;

    }

    public String getDeviceId() {
        return deviceId;
    }



    public int getLayoutId() {
        return layoutId;
    }

    public String getActionLabel() {
        return actionLabel;
    }

    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
        holder.getControlText().setText(getActionLabel());
    }
}
