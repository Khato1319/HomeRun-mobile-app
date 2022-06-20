package com.hci.homerunapp.ui.room;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.data.DeviceRepository;
import com.hci.homerunapp.ui.DataRepositoryViewModelFactory;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.SecondaryFragment;
import com.hci.homerunapp.databinding.FragmentRoomBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends SecondaryFragment implements ButtonListenerMaker {

    protected RoomViewModel model;
    CustomAdapter adapter;
    MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        model = new ViewModelProvider(this).get(RoomViewModel.class);
//
//        Bundle args = getArguments();
//
//        RoomData roomData = model.getRoomData();
//
//        if (roomData == null) {
//            if (args != null) {
//                roomData = (RoomData)args.get("roomData");
//            }
//            if (roomData == null && savedInstanceState != null){
//                roomData = (RoomData)savedInstanceState.getSerializable(ROOM_DATA);
//            }
//
//            if (roomData != null)
//                model.setRoomData(roomData);
//        }
//
//        roomData = model.getRoomData();
//        label = roomData.getName();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);


        FragmentRoomBinding binding = FragmentRoomBinding.inflate(inflater, container, false);

        MyApplication application = (MyApplication) getActivity().getApplication();
        activity = (MainActivity)getActivity();


        Bundle args = getArguments();
        RoomData roomData = (RoomData)args.get("roomData");
        label = roomData.getName();


        DataRepositoryViewModelFactory viewModelFactory = new DataRepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository(), RoomData.class, roomData);
        model = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);
//

//        if (args != null) {
//
//
//        }

        List<Device> devices =  new ArrayList<>();

        adapter = new CustomAdapter(devices, model.getData(), this);

        model.getDevices().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING -> activity.showProgressBar();
                case SUCCESS -> {
                    activity.hideProgressBar();
                    devices.clear();
                    if (resource.data != null &&
                            resource.data.size() > 0) {
                        devices.addAll(resource.data);
                        adapter.notifyDataSetChanged();
//                        binding.list.setVisibility(View.VISIBLE);
//                        binding.empty.setVisibility(View.GONE);
                    }
                }
            }
        });

        binding.roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.roomRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }


    @Override
    public View.OnClickListener getButtonClickListener(Data deviceData) {
        return (it) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("deviceData", deviceData);

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null)
                mainActivity.hideBottomNav();

            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_room_to_navigation_device, bundle);
        };
    }

    public static final String ROOM_DATA = "com.hci.homerunapp.ui.room/roomId";


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


        RoomData roomData = model.getData();


        if (model != null && roomData != null) {
            outState.putSerializable(ROOM_DATA, roomData);
        }



    }

    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }



}