package com.hci.homerunapp.ui.device;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.hci.homerunapp.data.DeviceRepository;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.Status;
import com.hci.homerunapp.data.remote.ApiClient;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.ApiDeviceService;
import com.hci.homerunapp.data.remote.device.RemoteDevice;
import com.hci.homerunapp.ui.DataRepositoryViewModel;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;


public class DeviceViewModel extends DataRepositoryViewModel<DeviceRepository, DeviceData> {

    private final MediatorLiveData<Resource<Device>> device = new MediatorLiveData<>();
    ApiDeviceService deviceService = ApiClient.create(ApiDeviceService.class);

    public DeviceViewModel(DeviceRepository repository, DeviceData data) {
        super(repository, data);
    }

    public LiveData<Resource<Device>> getDevice() {
        loadDevice();
        return device;
    }

    public void loadDevice() {
        device.addSource(repository.getDevice(getData().getId()), resource -> {
            if (resource.status == Status.SUCCESS) {
                device.postValue(Resource.success(resource.data));
            } else {
                device.postValue(resource);
            }
        });
    }

}