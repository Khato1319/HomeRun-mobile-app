package com.hci.homerunapp.ui.room;


import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;


public class RoomViewModel extends ViewModel {
    private RoomData roomData;
    protected List<DeviceData> devices;


    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
//        devices = new ArrayList<>();
//        devices.add(new DeviceData("Aspiradora", "1", roomData, DeviceData.Type.VACUUM));
//        devices.add(new DeviceData("Luz", "2", roomData, DeviceData.Type.LIGHT));
//        devices.add(new DeviceData("Blindss", "3", roomData, DeviceData.Type.BLINDS));
//        devices.add(new DeviceData("AC 2", "4", roomData, DeviceData.Type.AC));
//        devices.add(new DeviceData("Oven 1", "5", roomData, DeviceData.Type.OVEN));
    }

    public RoomData getRoomData() {
        return roomData;
    }




    public List<DeviceData> getDevices() {
        return devices;
    }

}