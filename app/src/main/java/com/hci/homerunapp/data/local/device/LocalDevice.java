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
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "size")
    public String size;
    @ColumnInfo(name = "color")
    public String color;

    public LocalDevice(String id, String name, String size, String color) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.color = color;
    }
}