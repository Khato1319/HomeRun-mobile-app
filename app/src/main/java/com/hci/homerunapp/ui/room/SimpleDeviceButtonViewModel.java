package com.hci.homerunapp.ui.room;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleDeviceButtonViewModel extends ViewModel {
    protected List<DeviceData> devices;

    public List<DeviceData> getDevices() {
        return devices;
    }
}