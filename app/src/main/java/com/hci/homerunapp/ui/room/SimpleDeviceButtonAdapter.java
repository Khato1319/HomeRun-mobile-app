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

import java.util.List;

public class SimpleDeviceButtonAdapter extends RecyclerView.Adapter<SimpleDeviceButtonAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<DeviceData> devices;
    private final ButtonListenerMaker buttonListenerMaker;



    public SimpleDeviceButtonAdapter(List<DeviceData> devices, ButtonListenerMaker buttonListenerMaker) {
        this.devices = devices;
        this.buttonListenerMaker = buttonListenerMaker;
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
        holder.getDeviceButton().setImageResource(deviceData.getType().getIcon());



        FloatingActionButton deviceButton = holder.getDeviceButton();

        deviceButton.setOnClickListener(buttonListenerMaker.getButtonClickListener(deviceData));


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
