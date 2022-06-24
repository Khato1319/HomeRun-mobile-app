package com.hci.homerunapp.ui.room;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.data.DeviceRepository;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.Status;
import com.hci.homerunapp.ui.DataRepositoryViewModel;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;


public class RoomViewModel extends DataRepositoryViewModel<DeviceRepository, RoomData> {
    private final MediatorLiveData<Resource<List<Device>>> devices = new MediatorLiveData<>();

    public RoomViewModel(DeviceRepository repository, RoomData data) {
        super(repository, data);
    }

    public LiveData<Resource<List<Device>>> getDevices() {
        loadDevices();
        return devices;
    }

    private void loadDevices() {
        devices.addSource(repository.getDevices(), resource -> {
            if (resource.status == Status.SUCCESS) {
                devices.setValue(Resource.success(resource.data));
            } else {
                devices.setValue(resource);
            }
        });
    }
}