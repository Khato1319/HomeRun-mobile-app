package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;

public class DeviceBlinds extends Device{
    public DeviceBlinds(DeviceData deviceData) {
        super(deviceData);
    }

    @Override
    public List<ControlData> getControls() {
        return null;
    }
}
