package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;

public class DeviceOven extends Device{
    public DeviceOven(DeviceData deviceData) {
        super(deviceData);
    }

    @Override
    public List<ControlData> getControls() {
        return null;
    }
}
