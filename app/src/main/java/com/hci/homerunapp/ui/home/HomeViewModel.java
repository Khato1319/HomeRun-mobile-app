package com.hci.homerunapp.ui.home;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.Data;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    protected List<Data> elements = new ArrayList<>();


    public List<Data> getElements() {
        return elements;
    }


    public HomeViewModel() {
        elements.add(new RoomData("Bedroom", "1"));
        elements.add(new RoomData("Bathroom", "1"));
        elements.add(new RoomData("Kitchen", "1"));
        elements.add(new RoomData("Gameroom", "1"));
        elements.add(new RoomData("Dining room", "1"));
        elements.add(new RoomData("Dining room", "1"));
        elements.add(new RoomData("Dining room", "1"));
    }
}
