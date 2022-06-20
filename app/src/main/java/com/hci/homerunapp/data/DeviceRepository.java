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
import com.hci.homerunapp.data.remote.device.action.ActionBody;
import com.hci.homerunapp.ui.device.ControlDataAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                deviceAC.getSpeedDropDown().setSelectedApi(state.getFanSpeed());
                deviceAC.getVerticalSwingDropDown().setSelectedApi(state.getVerticalSwing());
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
                Log.d("Brightness", String.valueOf(state.getBrightness()));
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
                deviceVacuum.getSetModeDropDownData().setSelectedApi(state.getMode());
                deviceVacuum.getDockButton().setState(state.getStatus().equals("docked"));
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

    public LiveData<Resource<Device>> getDevice(String id) {
        Log.d(TAG, "DeviceRepository - getDevice()");
        return new NetworkBoundResource<Device, LocalRoom, RemoteDevice>(
                executors,
                null,
                null,
                this::mapDeviceRemoteToModel) {


            @Override
            protected void saveCallResult(@NonNull LocalRoom local) {

            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoom local) {
                return false;
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteDevice remote) {
                return false;
            }

            @Override
            protected LiveData<LocalRoom> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteDevice>>> createCall() {
                return service.getDevice(id);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> putAction(String deviceId, String actionName, ActionBody action, ControlDataAdapter.ViewHolder viewHolder, boolean b) {

            Log.d(TAG, "DeviceRepository - putAction()");
        Call<ApiResponse<RemoteResult<Object>>> call = service.putAction(deviceId, actionName, action);
        call.enqueue(new Callback<ApiResponse<RemoteResult<Object>>>() {

            @Override
            public void onResponse(Call<ApiResponse<RemoteResult<Object>>> call, Response<ApiResponse<RemoteResult<Object>>> response) {
                Log.d("Response", String.valueOf(response.code()));
                if (b)
                    viewHolder.refreshDevice();
            }

            @Override
            public void onFailure(Call<ApiResponse<RemoteResult<Object>>> call, Throwable t) {

            }
        });
//            var result =  new NetworkBoundResource<Void, Void, Object>(
//                    executors,
//                    local -> null,
//                    remote -> null,
//                    remote -> null) {
//
//                @Override
//                protected void saveCallResult(@NonNull Void local) {
//
//                }
//
//                @Override
//                protected boolean shouldFetch(@Nullable Void local) {
//                    return true;
//                }
//
//                @Override
//                protected boolean shouldPersist(@Nullable Object remote) {
//                    return false;
//                }
//
//
//                @Override
//                protected LiveData<Void> loadFromDb() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                protected LiveData<ApiResponse<RemoteResult<Object>>> createCall() {
//                    return service.putAction(deviceId, actionName, action);
//                }
//            }.asLiveData();
        return null;
        }



}
