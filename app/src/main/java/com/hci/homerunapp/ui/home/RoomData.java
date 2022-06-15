package com.hci.homerunapp.ui.home;

import android.os.Parcelable;

import com.hci.homerunapp.ui.Data;

import java.io.Serializable;

public class RoomData implements Data {
    private String name;
    private String id;

    public RoomData(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
