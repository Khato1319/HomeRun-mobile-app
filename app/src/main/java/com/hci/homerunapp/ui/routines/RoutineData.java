package com.hci.homerunapp.ui.routines;

import com.hci.homerunapp.ui.Data;

import java.util.List;


public class RoutineData implements Data {
    private String name;
    private String id;

    public List<RoutineAction>getActions() {
        return actions;
    }

    public void setActions(List<RoutineAction> actions) {
        this.actions = actions;
    }

    private List<RoutineAction> actions;

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
