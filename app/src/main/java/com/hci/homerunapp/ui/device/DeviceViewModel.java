package com.hci.homerunapp.ui.device;

import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.ArrayList;
import java.util.List;

public class DeviceViewModel extends ViewModel {

    private Device device;

    public DeviceViewModel() {
        super();

    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

}