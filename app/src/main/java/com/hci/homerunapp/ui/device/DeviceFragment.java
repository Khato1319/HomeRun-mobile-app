package com.hci.homerunapp.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentDeviceBinding;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;
import com.hci.homerunapp.ui.room.RoomViewModel;


import java.util.Arrays;
import java.util.List;

public class DeviceFragment extends Fragment {

    private DeviceViewModel model;
    CustomAdapter adapter;
    List<String> deviceControls = Arrays.asList("Temperatura", "Volumen");
    public static final String DEVICE_DATA = "com.hci.homerunapp.ui.device/deviceData";
    private DeviceData deviceData;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(DeviceViewModel.class);

        Bundle args = getArguments();

        if (args != null) {
            deviceData = (DeviceData)args.get("deviceData");
            model.setDeviceData(deviceData);
        }

//        FragmentDevicesBinding

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


        // Save counter variable current value so it can be restored
        // when activity is recreated
        outState.putSerializable(DEVICE_DATA, model.getDeviceData());
    }

}