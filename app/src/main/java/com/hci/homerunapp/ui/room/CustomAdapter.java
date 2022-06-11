package com.hci.homerunapp.ui.room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.home.HomeFragment;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<String> deviceNames;
    private final List<String> roomNames;
    private final RoomFragment roomFragment;



    CustomAdapter(List<String> deviceNames, List<String> roomNames, RoomFragment roomFragment) {
        this.deviceNames = deviceNames;
        this.roomNames = roomNames;
        this.roomFragment = roomFragment;
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
        holder.getDeviceRoomText().setText(roomNames.get(position));
        holder.getDeviceText().setText(deviceNames.get(position));

        TextView deviceText = holder.getDeviceText();

        TextView deviceId = holder.getDeviceId();

        TextView deviceRoom = holder.getDeviceRoomText();

        FloatingActionButton deviceButton = holder.getDeviceButton();

        deviceButton.setOnClickListener(roomFragment.getButtonClickListener(deviceText.getText(),deviceRoom.getText(), deviceId.getText()));


    }

    @Override
    public int getItemCount() {
        return roomNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FloatingActionButton deviceButton;
        private final TextView deviceText;
        private final TextView roomText;
        private final TextView idText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           deviceButton = itemView.findViewById(R.id.device_button);
           deviceText = itemView.findViewById(R.id.text_device_name);
           roomText = itemView.findViewById(R.id.text_device_room);
           idText = itemView.findViewById(R.id.device_id);
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
        public TextView getDeviceId() {
            return idText;
        }



    }
}
