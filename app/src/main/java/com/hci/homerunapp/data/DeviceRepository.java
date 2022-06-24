package com.hci.homerunapp.data;

import static java.util.stream.Collectors.toList;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.local.device.DeviceDao;
import com.hci.homerunapp.data.local.device.LocalDevice;
import com.hci.homerunapp.data.local.room.LocalRoom;
import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.ApiDeviceService;
import com.hci.homerunapp.data.remote.device.ModifiedDevice;
import com.hci.homerunapp.data.remote.device.RemoteDevice;
import com.hci.homerunapp.data.remote.device.RemoteDeviceMeta;
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

    private Device mapDeviceRemoteToModel(RemoteDevice remote) {
        RemoteDeviceRoom room = remote.getRoom();
        RoomData roomData = new RoomData(room.getName(), room.getId());
        DeviceData deviceData;
        DeviceData.Type type;
        RemoteDeviceState state;



        LocalDevice localDevice = null;


        switch (remote.getType().getName()) {
            case "ac" -> {
                type = DeviceData.Type.AC;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                deviceData.setGroup(remote.getMeta().getGroup());
                executors.diskIO().execute(() -> {
                    LocalDevice localDevicee  = database.deviceDao().findById(remote.getId());
                    deviceData.setNotifications(localDevicee != null ? DeviceData.NotificationState.ON : DeviceData.NotificationState.OFF);
                });
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
                deviceData.setGroup(remote.getMeta().getGroup());
                executors.diskIO().execute(() -> {
                    LocalDevice localDevicee  = database.deviceDao().findById(remote.getId());
                    deviceData.setNotifications(localDevicee != null ? DeviceData.NotificationState.ON : DeviceData.NotificationState.OFF);
                });
                DeviceBlinds deviceBlinds = (DeviceBlinds) deviceData.getDeviceInstance(context);
                state = remote.getState();
                deviceBlinds.getClosePercentageSlider().setValue(state.getLevel());
                deviceBlinds.getStateProgressBar().setProgress(state.getCurrentLevel());
                deviceBlinds.getToggleStateButton().setState(switch(state.getStatus()){
                    case "opened" -> 0;
                    case "closed" -> 1;
                    case "opening" -> 2;
                    case "closing" -> 3;
                    default -> throw new IllegalStateException();
                });
                return deviceBlinds;
            }
            case "lamp" -> {
                type = DeviceData.Type.LIGHT;
                deviceData = new DeviceData(remote.getName(), remote.getId(), roomData, type);
                deviceData.setGroup(remote.getMeta().getGroup());
                executors.diskIO().execute(() -> {
                    LocalDevice localDevicee  = database.deviceDao().findById(remote.getId());
                    deviceData.setNotifications(localDevicee != null ? DeviceData.NotificationState.ON : DeviceData.NotificationState.OFF);
                });
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
                deviceData.setGroup(remote.getMeta().getGroup());
                executors.diskIO().execute(() -> {
                    LocalDevice localDevicee  = database.deviceDao().findById(remote.getId());
                    deviceData.setNotifications(localDevicee != null ? DeviceData.NotificationState.ON : DeviceData.NotificationState.OFF);
                });
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
                deviceData.setGroup(remote.getMeta().getGroup());
                executors.diskIO().execute(() -> {
                    LocalDevice localDevicee  = database.deviceDao().findById(remote.getId());
                    deviceData.setNotifications(localDevicee != null ? DeviceData.NotificationState.ON : DeviceData.NotificationState.OFF);
                });
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

    public Call<RemoteResult<List<RemoteDevice>>> fetchDevices() {
        return service.fetchDevices();
    }



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

    public LiveData<Resource<Void>> putAction(DeviceData deviceData, String actionName, ActionBody action, ControlDataAdapter.ViewHolder viewHolder, boolean b, int progress) {

            Log.d(TAG, "DeviceRepository - putAction()");
        Call<RemoteResult<Boolean>> call = service.putAction(deviceData.getId(), actionName, action);
        call.enqueue(new Callback<RemoteResult<Boolean>>() {

            @Override
            public void onResponse(Call<RemoteResult<Boolean>> call, Response<RemoteResult<Boolean>> response) {
                Log.d("Response", String.valueOf(response.code()));
                if (response.code() != 200 ||
                        response.body() == null ||
                        response.body().getResult() == null || !response.body().getResult())
                    return;

                executors.diskIO().execute(() -> {
                    LocalDevice device = database.deviceDao().findById(deviceData.getId());
                    if (device != null || deviceData.getNotifications() == DeviceData.NotificationState.ON) {
                        switch(actionName) {
                            case "turnOn" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "on",
                                        0));
                            }
                            case "turnOff" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "off",
                                        0));
                            }
                            case "start" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "active",
                                        progress));
                            }
                            case "pause" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "inactive",
                                        progress));
                            }
                            case "dock" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "docked",
                                        progress));
                            }
                            case "open" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "opened",
                                        0));
                            }
                            case "closed" -> {
                                database.deviceDao().insert(new LocalDevice(deviceData.getId(),
                                        "closed",
                                        0));
                            }
                        }
                    }
                });

                if (b){
                    viewHolder.refreshDevice();

                }
            }

            @Override
            public void onFailure(Call<RemoteResult<Boolean>> call, Throwable t) {

            }

        });
        return null;
        }

//    public LiveData<Resource<Void>> setNotifications(DeviceData data, DeviceData.NotificationState notifications) {
//
//        Log.d(TAG, "DeviceRepository - putDevice()");
//        RemoteDeviceMeta meta = new RemoteDeviceMeta();
//        meta.setGroup(data.getGroup());
//        meta.setNotifications(notifications == DeviceData.NotificationState.ON);
//        ModifiedDevice modifiedDevice = new ModifiedDevice();
//        modifiedDevice.setMeta(meta);
//        modifiedDevice.setName(data.getName());
//        Call<ApiResponse<RemoteResult<Object>>> call = service.updateDevice(data.getId(), modifiedDevice);
//        call.enqueue(new Callback<ApiResponse<RemoteResult<Object>>>() {
//
//            @Override
//            public void onResponse(@NonNull Call<ApiResponse<RemoteResult<Object>>> call, @NonNull Response<ApiResponse<RemoteResult<Object>>> response) {
//                Log.d("Response", String.valueOf(response.code()));
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ApiResponse<RemoteResult<Object>>> call, Throwable t) {
//
//            }
//        });
//        return null;
//    }


    public void setNotifications(DeviceData deviceData, DeviceData.NotificationState notificationState, String state, int level) {
        DeviceDao deviceDao = database.deviceDao();
        executors.diskIO().execute(() -> {
            if (notificationState.equals(DeviceData.NotificationState.ON)) {
                deviceDao.insert(new LocalDevice(deviceData.getId(), state, level));
            }
            else {
                deviceDao.delete(deviceData.getId());
            }

        });

    }
}
