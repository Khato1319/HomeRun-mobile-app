package com.hci.homerunapp.ui.device;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;


import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.SecondaryFragment;
import com.hci.homerunapp.databinding.FragmentDeviceBinding;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Objects;

public class DeviceFragment extends SecondaryFragment {

    private DeviceViewModel model;
    ControlDataAdapter adapter;
    public static final String DEVICE_DATA = "com.hci.homerunapp.ui.device/deviceData";
    Device device;
    public static final String CHANNEL_ID= "CHANNEL_ID";
    public static final String CHANNEL_NAME= "channel";

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
        createNotificationChannel();
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device.toggleNotificationState();
                notificationsButton.setImageResource(device.getNotificationState().getIconId());
                NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext(),CHANNEL_ID);
                builder.setContentTitle("Este es el titulo");
                builder.setContentText("hola soy el aleca");
                builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(v.getContext());
                notificationManagerCompat.notify(1,builder.build());
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


        adapter = new ControlDataAdapter(device.getControls(), this);

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, importance);
            channel.setDescription("esto es una notificacion");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = requireView().getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}