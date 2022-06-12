package com.hci.homerunapp.ui.device;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.hci.homerunapp.databinding.FragmentDeviceBinding;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;



import java.util.Arrays;
import java.util.List;

public class DeviceFragment extends Fragment {

    private DeviceViewModel model;
    CustomAdapter adapter;
    public static final String DEVICE_DATA = "com.hci.homerunapp.ui.device/deviceData";

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(DeviceViewModel.class);


        Bundle args = getArguments();
        DeviceData deviceData = model.getDeviceData();

        if (deviceData == null) {
            if (args != null) {
                deviceData = (DeviceData)args.get("roomData");
                if (deviceData != null)
                    model.setDeviceData(deviceData);
            }
            if (deviceData == null && savedInstanceState != null){
                deviceData = (DeviceData)savedInstanceState.getSerializable(DEVICE_DATA);
            }

            if (deviceData != null)
                model.setDeviceData(deviceData);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);


        FragmentDeviceBinding binding = FragmentDeviceBinding.inflate(inflater, container, false);


        adapter = new CustomAdapter(model.getControls(), this);

        binding.deviceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.deviceRecyclerView.setAdapter(adapter);





        return binding.getRoot();
    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


        DeviceData deviceData = model.getDeviceData();


        if (model != null && deviceData != null) {
            outState.putSerializable(DEVICE_DATA, deviceData);
        }
    }

}