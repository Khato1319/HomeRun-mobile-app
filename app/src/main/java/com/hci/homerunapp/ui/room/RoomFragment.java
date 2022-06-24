package com.hci.homerunapp.ui.room;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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
import java.util.Random;
import java.util.stream.Collectors;

public class RoomFragment extends SecondaryFragment implements ButtonListenerMaker {

    protected RoomViewModel model;
    CustomAdapter adapter;
    MainActivity activity;
    RoomData roomData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        if (args != null)
            roomData = (RoomData)args.get("roomData");
        else if (savedInstanceState.getSerializable("roomData")!=null) {
            roomData = (RoomData)savedInstanceState.getSerializable("roomData");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);


        FragmentRoomBinding binding = FragmentRoomBinding.inflate(inflater, container, false);

        activity = (MainActivity)getActivity();
        MyApplication application = (MyApplication) activity.getApplication();

        Bundle args = getArguments();

        if (args != null)
            roomData = (RoomData)args.get("roomData");
        else if (savedInstanceState.getSerializable("roomData")!=null) {
            roomData = (RoomData)savedInstanceState.getSerializable("roomData");
        }


        DataRepositoryViewModelFactory viewModelFactory = new DataRepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository(), RoomData.class, roomData);
        model = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);
        label = model.getData().getName();


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
                        devices.addAll(resource.data.stream().filter(
                                d -> d.getDeviceData().getRoomData().getName()
                                        .equals(model.getData().getName()))
                                .collect(Collectors.toList()));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int orientation = this.getResources().getConfiguration().orientation;

        if (isTablet) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.roomRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            else
                binding.roomRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));

        }
        else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.roomRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            else
                binding.roomRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        }
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

        if (roomData != null) {
            outState.putSerializable(ROOM_DATA, roomData);
        }
    }

    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }
}