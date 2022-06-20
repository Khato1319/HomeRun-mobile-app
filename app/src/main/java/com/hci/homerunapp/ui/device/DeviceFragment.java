package com.hci.homerunapp.ui.device;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.data.DeviceRepository;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.ui.DataRepositoryViewModelFactory;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.SecondaryFragment;
import com.hci.homerunapp.databinding.FragmentDeviceBinding;
import com.hci.homerunapp.ui.device.vacuum.ChangeLocationDropDownData;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeviceFragment extends SecondaryFragment {

    private DeviceViewModel model;
    ControlDataAdapter adapter;
    public static final String DEVICE_DATA = "com.hci.homerunapp.ui.device/deviceData";
    MainActivity activity;
    Device device;
    Disposable disposable;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.d("ONCREATE", "ONCREATE");
//
//        model = new ViewModelProvider(this).get(DeviceViewModel.class);
//
//        Bundle args = getArguments();
//        device = model.getDevice();
//        DeviceData deviceData = null;
//
//        if (device == null) {
//            if (args != null) {
//                deviceData = (DeviceData)args.get("deviceData");
//            }
//            if (deviceData == null && savedInstanceState != null){
//                deviceData = (DeviceData)savedInstanceState.getSerializable(DEVICE_DATA);
//            }
//            if (deviceData != null) {
//                model.setDevice(deviceData.getDeviceInstance(getContext()));
//                device = model.getDevice();
//            }
//        }
//        label = device.getDeviceData().getName();

        disposable = Observable.interval(1000, 5000,
                        TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::refreshDevice, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


    }

    private void refreshDevice(Long aLong) {
        refreshDevice();
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
//        for (DeviceData device : mainActivity.getRecentDevices())
//            Log.d("device", String.valueOf(device));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);
        FragmentDeviceBinding binding = FragmentDeviceBinding.inflate(inflater, container, false);

        activity = (MainActivity)getActivity();
        MyApplication application = (MyApplication) activity.getApplication();


        Bundle args = getArguments();
        DeviceData deviceData = null;
        if (args != null)
            deviceData = (DeviceData)args.get("deviceData");



        DataRepositoryViewModelFactory viewModelFactory = new DataRepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository(), DeviceData.class, deviceData);
        model = new ViewModelProvider(this, viewModelFactory).get(DeviceViewModel.class);
        label = model.getData().getName();

        device = model.getData().getDeviceInstance(activity);
        List<ControlData> controls =  new ArrayList<>();

        adapter = new ControlDataAdapter(controls, this);


        Observer<Resource<List<RoomData>>> observer = new Observer<Resource<List<RoomData>>>() {
            @Override
            public void onChanged(Resource<List<RoomData>> listResource) {
                switch (listResource.status) {
                    case SUCCESS -> {
                        if (listResource.data != null &&
                                listResource.data.size() > 0) {
                            for (ControlData control : controls)
                                if (control instanceof ChangeLocationDropDownData) {
                                    ((ChangeLocationDropDownData) control).setRooms(listResource.data);
                                    adapter.notifyDataSetChanged();
                                }
                        }
                    }
                }
            }
        };


        model.getDevice().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING -> {
                    if (controls.size() == 0)
                        activity.showProgressBar();
                }
                case SUCCESS -> {
                    if (controls.size() == 0)
                        activity.hideProgressBar();
                    controls.clear();
                    if (resource.data != null) {
                        Log.d("Refreshing Device", "fetched new data");
                        controls.addAll(resource.data.getControls());
                        activity.getRooms().observe(getViewLifecycleOwner(), observer);
                        device = resource.data;
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        binding.deviceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.deviceRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    public void refreshDevice() {
        Log.d("Refreshing device", "REFRESHING DEVICE");
        model.loadDevice();
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