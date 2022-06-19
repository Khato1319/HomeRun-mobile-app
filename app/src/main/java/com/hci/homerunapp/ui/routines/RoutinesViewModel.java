package com.hci.homerunapp.ui.routines;


import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.Data;

import java.util.ArrayList;
import java.util.List;

public class RoutinesViewModel  extends ViewModel {
    protected List<Data> elements = new ArrayList<>();


    public List<Data> getElements() {
        return elements;
    }
    public RoutinesViewModel() {
//        elements.add(new RoutineData("A dormir", "1"));
//        elements.add(new RoutineData("A despertarse", "1"));
//        elements.add(new RoutineData("Juegos", "1"));
//        elements.add(new RoutineData("Chill", "1"));
    }
}