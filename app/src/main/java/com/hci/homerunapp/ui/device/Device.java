package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;

public abstract class Device {
    private DeviceData deviceData;

    public abstract List<ControlData> getControls();

    public Device(DeviceData deviceData) {
        this.deviceData = deviceData;
    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

}
