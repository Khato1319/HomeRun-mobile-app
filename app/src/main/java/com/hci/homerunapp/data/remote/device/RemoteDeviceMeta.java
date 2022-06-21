package com.hci.homerunapp.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceMeta {

    @SerializedName("group")
    @Expose
    private String group;

    @SerializedName("notifications")
    @Expose
    private boolean notifications;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}