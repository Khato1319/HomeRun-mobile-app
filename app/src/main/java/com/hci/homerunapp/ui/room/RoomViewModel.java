package com.hci.homerunapp.ui.room;


import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;


public class RoomViewModel extends SimpleDeviceButtonViewModel {
    private RoomData roomData;

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
        devices = new ArrayList<>();
        devices.add(new DeviceData("Aspiradora", "1", roomData, DeviceData.Type.VACUUM));
        devices.add(new DeviceData("Luz", "1", roomData, DeviceData.Type.LIGHT));
        devices.add(new DeviceData("Blindss", "1", roomData, DeviceData.Type.BLINDS));
    }

    public RoomData getRoomData() {
        return roomData;
    }

}