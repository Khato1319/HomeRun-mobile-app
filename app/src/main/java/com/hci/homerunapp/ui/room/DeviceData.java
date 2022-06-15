package com.hci.homerunapp.ui.room;

import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.home.RoomData;

import java.io.Serializable;

public class DeviceData implements Data {
    private String name;
    private RoomData roomData;
    private String id;

    public DeviceData(String name, String id, RoomData roomData) {
        this.name = name;
        this.id = id;
        this.roomData = roomData;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    @Override
    public String toString() {
        return name;
    }
}
