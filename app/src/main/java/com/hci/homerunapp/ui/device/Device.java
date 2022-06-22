package com.hci.homerunapp.ui.device;

import android.content.Context;

import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;

public abstract class Device implements Data {
    private final DeviceData deviceData;
    protected Context context;



    public abstract List<ControlData> getControls();

    public Device(DeviceData deviceData, Context context) {
        this.deviceData = deviceData;
        this.context = context;

    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public void toggleNotificationState() {
        if (deviceData.getNotifications().equals(DeviceData.NotificationState.OFF))
            deviceData.setNotifications(DeviceData.NotificationState.ON);
        else
            deviceData.setNotifications(DeviceData.NotificationState.OFF);
    }

    public DeviceData.NotificationState getNotificationState() {
        return deviceData.getNotifications();
    }


    @Override
    public String getName() {
        return deviceData.getName();
    }

    @Override
    public String getId() {
        return deviceData.getId();
    }



}
