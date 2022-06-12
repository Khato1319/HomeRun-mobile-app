package com.hci.homerunapp.ui.room;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoomViewModel extends ViewModel {
    private RoomData roomData;
    private List<DeviceData> devices;


    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
        devices = new ArrayList<>();
        devices.add(new DeviceData("Aspiradora", "1", roomData));
        devices.add(new DeviceData("Luz", "1", roomData));

        devices.add(new DeviceData("Speaker", "1", roomData));


    }

    public RoomData getRoomData() {
        return roomData;
    }

    public List<DeviceData> getDevices() {
        return devices;
    }
}