package com.hci.homerunapp.data.local.device;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Device", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalDevice {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "battery")
    public int battery;

    public LocalDevice(@NonNull String id, String status, int battery) {
        this.id = id;
        this.status = status;
        this.battery = battery;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
//
//    public int getBlindsState() {
//        return blindsState;
//    }

    public int getBattery() {
        return battery;
    }
}