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
//    private RoomData roomData;
private final MediatorLiveData<Resource<List<Device>>> devices = new MediatorLiveData<>();

    public RoomViewModel(DeviceRepository repository, RoomData data) {
        super(repository, data);
    }


//    public void setRoomData(RoomData roomData) {
////        devices = new ArrayList<>();
////        devices.add(new DeviceData("Aspiradora", "1", roomData, DeviceData.Type.VACUUM));
////        devices.add(new DeviceData("Luz", "2", roomData, DeviceData.Type.LIGHT));
////        devices.add(new DeviceData("Blindss", "3", roomData, DeviceData.Type.BLINDS));
////        devices.add(new DeviceData("AC 2", "4", roomData, DeviceData.Type.AC));
////        devices.add(new DeviceData("Oven 1", "5", roomData, DeviceData.Type.OVEN));
//    }

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