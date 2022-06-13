package com.hci.homerunapp.ui.home;

public class HomeViewModel extends SimpleButtonViewModel{

    public HomeViewModel() {
        elements.add(new RoomData("Bedroom", "1"));
        elements.add(new RoomData("Bathroom", "1"));
        elements.add(new RoomData("Kitchen", "1"));
        elements.add(new RoomData("Gameroom", "1"));
        elements.add(new RoomData("Dining room", "1"));
    }
}
