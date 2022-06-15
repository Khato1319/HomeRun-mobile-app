package com.hci.homerunapp.ui.routines;

import com.hci.homerunapp.ui.Data;


public class RoutineData implements Data {
    private String name;
    private String id;

    RoutineData(String name, String id) {
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
