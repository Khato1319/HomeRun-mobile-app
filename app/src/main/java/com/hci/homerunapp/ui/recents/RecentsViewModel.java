package com.hci.homerunapp.ui.recents;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.ArrayList;
import java.util.List;

public class RecentsViewModel extends ViewModel {
    private RoomData roomData;
    private List<DeviceData> devices;


    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
        devices = new ArrayList<>();



    }

    public RoomData getRoomData() {
        return roomData;
    }

    public List<DeviceData> getDevices() {
        return devices;
    }
}