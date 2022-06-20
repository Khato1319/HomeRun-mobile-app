package com.hci.homerunapp.data;

import static java.util.stream.Collectors.toList;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.local.room.LocalRoom;
import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.ApiDeviceService;
import com.hci.homerunapp.data.remote.device.RemoteDevice;
import com.hci.homerunapp.data.remote.device.RemoteDeviceRoom;
import com.hci.homerunapp.data.remote.device.RemoteDeviceState;
import com.hci.homerunapp.data.remote.room.RemoteRoom;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.ac.DeviceAC;
import com.hci.homerunapp.ui.device.blinds.DeviceBlinds;
import com.hci.homerunapp.ui.device.light.DeviceLight;
import com.hci.homerunapp.ui.device.oven.DeviceOven;
import com.hci.homerunapp.ui.device.vacuum.DeviceVacuum;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeviceRepository {

    private static final String TAG = "deviceData";
//    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";
//
    private final AppExecutors executors;
    private final ApiDeviceService service;
    private final Context context;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public DeviceRepository(ApiDeviceService service, AppExecutors executors, Context context, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.context = context;
        this.database = database;

    }

//    private Device mapDeviceLocalToModel(LocalDevice local) {
//        return new RoomData(local.name, local.id);
//    }
//
//    private Device mapDeviceRemoteToLocal(RemoteDevice remote) {
//        return new LocalRoom(remote.getId(), remote.getName(), remote.getMeta().getSize(), remote.getMeta().getColor());
//    }

    private Device mapDeviceRemoteToModel(RemoteDevice remote) {
        RemoteDeviceRoom room = remote.getRoom();
        RoomData roomData = new RoomData(room.getName(), room.getId());
        DeviceData deviceData;
        DeviceData.Type type;
        RemoteDeviceState state;


        switch (remote.getType().getName()) {
            case "ac" -> {
                type = DeviceData.Type.AC;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                DeviceAC deviceAC = (DeviceAC) deviceData.getDeviceInstance(context);
                state = remote.getState();
                deviceAC.getCoolingModeDropDown().setSelectedApi(state.getMode());
                deviceAC.getHorizontalSwingDropDown().setSelectedApi(state.getHorizontalSwing());
                deviceAC.getSpeedDropDown().setSelectedApi(state.getVerticalSwing());
                deviceAC.getVerticalSwingDropDown().setSelectedApi(state.getFanSpeed());
                deviceAC.getTemperatureSlider().setValue(state.getTemperature());
                deviceAC.getTurnOnButton().setState(state.getStatus().equals("on"));
                return deviceAC;
            }
            case "blinds" -> {
                type = DeviceData.Type.BLINDS;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                DeviceBlinds deviceBlinds = (DeviceBlinds) deviceData.getDeviceInstance(context);
                state = remote.getState();
                deviceBlinds.getClosePercentageSlider().setValue(state.getLevel());
                deviceBlinds.getStateProgressBar().setProgress(state.getCurrentLevel());
                deviceBlinds.getToggleStateButton().setState(state.getStatus().equals("opened"));
                return deviceBlinds;
            }
            case "lamp" -> {
                type = DeviceData.Type.LIGHT;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                DeviceLight deviceLamp = (DeviceLight) deviceData.getDeviceInstance(context);
                state = remote.getState();
                deviceLamp.getBrightnessSlider().setValue(state.getBrightness());
                deviceLamp.getTurnOnButton().setState(state.getStatus().equals("on"));
                deviceLamp.getColorPicker().setRGB(state.getColor());
                return deviceLamp;
            }
            case "oven" -> {
                type = DeviceData.Type.OVEN;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                DeviceOven deviceOven = (DeviceOven) deviceData.getDeviceInstance(context);
                state = remote.getState();
                deviceOven.getChangeHeatSourceDropDown().setSelectedApi(state.getHeat());
                deviceOven.getSetConvectionModeDropDown().setSelectedApi(state.getConvection());
                deviceOven.getTemperatureSlider().setValue(state.getTemperature());
                deviceOven.getTurnOnButton().setState(state.getStatus().equals("on"));
                deviceOven.getSetGrillModeDropDown().setSelectedApi(state.getGrill());
                return deviceOven;
            }
            case "vacuum" -> {
                type = DeviceData.Type.VACUUM;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                DeviceVacuum deviceVacuum = (DeviceVacuum) deviceData.getDeviceInstance(context);
                state = remote.getState();
                deviceVacuum.getBatteryProgressBar().setProgress(state.getBatteryLevel());
                RemoteDeviceRoom deviceRoom = state.getLocation();
                deviceVacuum.getChangeLocationDropDown().setSelected(new RoomData(deviceRoom.getName(), deviceRoom.getId()));
                deviceVacuum.getTurnOnButton().setState(state.getStatus().equals("active"));
                return deviceVacuum;
            }
        }
        return null;
    }

//    private RemoteRoom mapRoomModelToRemote(RoomData model) {
//        RemoteRoomMeta remoteMeta = new RemoteRoomMeta();
//
//        RemoteRoom remote = new RemoteRoom();
//        remote.setId(model.getId());
//        remote.setName(model.getName());
//        remote.setMeta(remoteMeta);
//
//        return remote;
//    }

    public LiveData<Resource<List<Device>>> getDevices() {
        Log.d(TAG, "DeviceRepository - getDevices()");
        return new NetworkBoundResource<List<Device>, List<LocalRoom>, List<RemoteDevice>>(
                executors,
               null,
                null,
                remotes -> {
                    return remotes.stream()
                            .map(this::mapDeviceRemoteToModel)
                            .collect(toList());
                }) {

            @Override
            protected void saveCallResult(@NonNull List<LocalRoom> locals) {
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoom> locals) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteDevice> remote) {
                return false;
            }

            @Override
            protected LiveData<List<LocalRoom>> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> createCall() {
                return service.getDevices();
            }
        }.asLiveData();
    }

//    public LiveData<Resource<RoomData>> getRoom(String roomId) {
//        Log.d(TAG, "getRoom()");
//        return new NetworkBoundResource<RoomData, LocalRoom, RemoteRoom>(
//                executors,
//                this::mapRoomLocalToModel,
//                this::mapRoomRemoteToLocal,
//                this::mapRoomRemoteToModel) {
//
//            @Override
//            protected void saveCallResult(@NonNull LocalRoom local) {
//                database.roomDao().insert(local);
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable LocalRoom local) {
//                return (local == null);
//            }
//
//            @Override
//            protected boolean shouldPersist(@Nullable RemoteRoom remote) {
//                return true;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<LocalRoom> loadFromDb() {
//                return database.roomDao().findById(roomId);
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ApiResponse<RemoteResult<RemoteRoom>>> createCall() {
//                return service.getRoom(roomId);
//            }
//        }.asLiveData();
//    }
}
