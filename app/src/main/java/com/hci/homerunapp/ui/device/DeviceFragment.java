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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentDeviceBinding;


import java.util.Arrays;
import java.util.List;

public class DeviceFragment extends Fragment {

    private DeviceViewModel mViewModel;
//    List<String> deviceNames = Arrays.asList("Aspiradora 1", "Cortinas", "Speaker");
//    List<String> deviceRooms = Arrays.asList("Bedroom", "Kitchen", "Living");
//    CustomAdapter adapter;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        FragmentDevicesBinding

        FragmentDeviceBinding binding = FragmentDeviceBinding.inflate(inflater, container, false);

        TextView tv = binding.textView;
        tv.setText(getArguments().getCharSequence("deviceName"));


//        adapter = new CustomAdapter(deviceNames, deviceRooms, this);
//
//        binding.roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
//        binding.roomRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        // TODO: Use the ViewModel
    }

}