package com.hci.homerunapp.ui.device;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.SecondaryFragment;
import com.hci.homerunapp.databinding.FragmentDeviceBinding;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;



import java.util.Arrays;
import java.util.List;

public class DeviceFragment extends SecondaryFragment {

    private DeviceViewModel model;
    CustomAdapter adapter;
    public static final String DEVICE_DATA = "com.hci.homerunapp.ui.device/deviceData";
    Device device;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ONCREATE", "ONCREATE");

        model = new ViewModelProvider(this).get(DeviceViewModel.class);

        Bundle args = getArguments();
        device = model.getDevice();
        DeviceData deviceData = null;

        if (device == null) {
            if (args != null) {
                deviceData = (DeviceData)args.get("deviceData");
            }
            if (deviceData == null && savedInstanceState != null){
                deviceData = (DeviceData)savedInstanceState.getSerializable(DEVICE_DATA);
            }
            if (deviceData != null) {
                model.setDevice(deviceData.getDeviceInstance(getContext()));
                device = model.getDevice();
            }
        }
        label = device.getDeviceData().getName();


    }

    @Override
    protected void executeActions(MainActivity mainActivity) {
        super.executeActions(mainActivity);
        ImageButton notificationsButton = mainActivity.getNotificationsButton();
        notificationsButton.setImageResource(device.getNotificationState().getIconId());
        notificationsButton.setVisibility(View.VISIBLE);
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device.toggleNotificationState();
                notificationsButton.setImageResource(device.getNotificationState().getIconId());
            }
        });
//        Log.d("DEVICE", String.valueOf(device));
        mainActivity.addToRecents(device.getDeviceData());
        for (DeviceData device : mainActivity.getRecentDevices())
            Log.d("device", String.valueOf(device));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);
        FragmentDeviceBinding binding = FragmentDeviceBinding.inflate(inflater, container, false);


        adapter = new CustomAdapter(device.getControls(), this);

        binding.deviceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.deviceRecyclerView.setAdapter(adapter);





        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


        DeviceData deviceData = device.getDeviceData();


        if (model != null && deviceData != null) {
            outState.putSerializable(DEVICE_DATA, deviceData);
        }
    }

}