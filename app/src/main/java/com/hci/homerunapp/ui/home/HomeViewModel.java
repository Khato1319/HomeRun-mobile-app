package com.hci.homerunapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private List<RoomData> rooms = new ArrayList<>();

    public HomeViewModel() {
        rooms.add(new RoomData("Bedroom", "1"));
        rooms.add(new RoomData("Bathroom", "1"));
        rooms.add(new RoomData("Kitchen", "1"));
        rooms.add(new RoomData("Gameroom", "1"));
        rooms.add(new RoomData("Dining room", "1"));
    }

    public List<RoomData> getRooms() {
        return rooms;
    }
}