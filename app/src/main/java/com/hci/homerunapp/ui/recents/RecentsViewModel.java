package com.hci.homerunapp.ui.recents;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;
import com.hci.homerunapp.ui.room.SimpleDeviceButtonViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecentsViewModel extends ViewModel {
    protected List<DeviceData> devices;


public RecentsViewModel() {
//    devices = new ArrayList<>();
//    devices.add(new DeviceData("Aspiradora", "1", new RoomData("Banio", "1") , DeviceData.Type.OVEN));
//    devices.add(new DeviceData("Luz", "1", new RoomData("Comedor", "1"), DeviceData.Type.OVEN));
//
//    devices.add(new DeviceData("Speaker", "1", new RoomData("Living", "1"), DeviceData.Type.OVEN));
}


public void setDevices(List<DeviceData> devices) {
    if (this.devices == null)
        this.devices = devices;
    else {
        for(DeviceData device : devices) {
            if (!this.devices.contains(device))
                this.devices.add(device);
        }
    }
}

    public List<DeviceData> getDevices() {
        return devices;
    }

}