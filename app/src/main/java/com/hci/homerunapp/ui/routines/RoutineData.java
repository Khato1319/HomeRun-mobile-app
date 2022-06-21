package com.hci.homerunapp.ui.routines;

import com.hci.homerunapp.ui.Data;

import java.util.Map;


public class RoutineData implements Data {
    private String name;
    private String id;

    public Map<String, Object> getActions() {
        return actions;
    }

    public void setActions(Map<String, Object> actions) {
        this.actions = actions;
    }

    private Map<String, Object> actions;

    public RoutineData(String name, String id) {
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
