package com.hci.homerunapp.ui.routines;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.home.SimpleButtonViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoutinesViewModel extends SimpleButtonViewModel {

    public RoutinesViewModel() {
        elements.add(new RoutineData("A dormir", "1"));
        elements.add(new RoutineData("A despertarse", "1"));
        elements.add(new RoutineData("Juegos", "1"));
        elements.add(new RoutineData("Chill", "1"));
    }
}