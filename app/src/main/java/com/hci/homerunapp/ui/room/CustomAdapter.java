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
import com.hci.homerunapp.ui.home.RoomData;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<DeviceData> devices;
    private final RoomData roomData;
    private final RoomFragment roomFragment;



    CustomAdapter(List<DeviceData> devices, RoomData roomData, RoomFragment roomFragment) {
        this.devices = devices;
        this.roomFragment = roomFragment;
        this.roomData = roomData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

//        holder.getDeviceButton().setText(dataSet.get(position));
        DeviceData deviceData = devices.get(position);
        holder.getDeviceRoomText().setText(deviceData.getRoomData().getName());
        holder.getDeviceText().setText(deviceData.getName());

//        TextView deviceText = holder.getDeviceText();
//
//        TextView deviceId = holder.getDeviceId();
//
//        TextView deviceRoom = holder.getDeviceRoomText();

        FloatingActionButton deviceButton = holder.getDeviceButton();

        deviceButton.setOnClickListener(roomFragment.getButtonClickListener(deviceData));


    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FloatingActionButton deviceButton;
        private final TextView deviceText;
        private final TextView roomText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           deviceButton = itemView.findViewById(R.id.device_button);
           deviceText = itemView.findViewById(R.id.text_device_name);
           roomText = itemView.findViewById(R.id.text_control);
        }

        public FloatingActionButton getDeviceButton() {
            return deviceButton;
        }
        public TextView getDeviceRoomText() {
            return roomText;
        }
        public TextView getDeviceText() {
            return deviceText;
        }




    }
}
