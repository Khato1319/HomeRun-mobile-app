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
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<SimpleDeviceButtonAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final RoomData roomData;
    private final List<Device> devices;
    private final ButtonListenerMaker buttonListenerMaker;

    CustomAdapter(List<Device> devices, RoomData roomData, ButtonListenerMaker buttonListenerMaker) {
        this.devices = devices;
        this.buttonListenerMaker = buttonListenerMaker;
        this.roomData = roomData;
    }

    @NonNull
    public SimpleDeviceButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item, parent, false);
        return new SimpleDeviceButtonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleDeviceButtonAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        DeviceData deviceData = devices.get(position).getDeviceData();
        holder.getDeviceRoomText().setText(deviceData.getRoomData().getName());
        holder.getDeviceText().setText(deviceData.getName());
        holder.getDeviceButton().setImageResource(deviceData.getType().getIcon());

        FloatingActionButton deviceButton = holder.getDeviceButton();

        deviceButton.setOnClickListener(buttonListenerMaker.getButtonClickListener(deviceData));
    }

    @Override
    public int getItemCount() {
        return devices == null ? 0 : devices.size();
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
