package com.hci.homerunapp.ui.room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.List;

public class CustomAdapter extends SimpleDeviceButtonAdapter {

    private static final String TAG = "CustomAdapter";
    private final RoomData roomData;




    CustomAdapter(List<DeviceData> devices, RoomData roomData, ButtonListenerMaker buttonListenerMaker) {
        super(devices, buttonListenerMaker);
        this.roomData = roomData;
    }


}
