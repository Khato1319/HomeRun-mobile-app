package com.hci.homerunapp;

import static com.hci.homerunapp.ui.room.DeviceData.Type.getType;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.hci.homerunapp.data.local.device.DeviceDao;
import com.hci.homerunapp.data.local.device.LocalDevice;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.device.RemoteDevice;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


import retrofit2.Call;
import retrofit2.Response;

public class MyNotificationWorker extends Worker {

    private final MyApplication application;
    private final String CHANNEL = "channel";

    public MyNotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.application = (MyApplication) context;
    }

    @Override
    public Result doWork() {

        DeviceDao deviceDao = application.getDatabase().deviceDao();

        Call<RemoteResult<List<RemoteDevice>>> call = application.getDeviceRepository().fetchDevices();
        try {
            Response<RemoteResult<List<RemoteDevice>>> response = call.execute();

            if (response.code() == 200) {
                List<RemoteDevice> devices = response.body().getResult();

                 List<LocalDevice> dbDevices = deviceDao.findAll();

                 if (dbDevices.size() > 0) {

                     dbDevices.removeIf(localDevice -> devices.stream().noneMatch(d -> d.getId().equals(localDevice.getId())));

                     for (RemoteDevice device : devices) {

                             Optional<LocalDevice> oldDevice = dbDevices.stream().filter(d -> d.getId().equals(device.getId())).findFirst();
                             oldDevice.ifPresent(localDevice -> {
                                 sendNotificationIfStateChanged(device, localDevice);
                                 dbDevices.remove(localDevice);
                                 dbDevices.add(new LocalDevice(device.getId(),
                                         device.getState().getStatus(),
                                         device.getState().getBatteryLevel()));
                             });
                     }

                     deviceDao.deleteAll();
                     deviceDao.insert(dbDevices);

                 }

            } else {
                return Result.retry();
            }
            // ...
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure();
    }

    private void sendNotificationIfStateChanged(RemoteDevice newDevice, LocalDevice oldDevice) {
        createNotificationChannel();
//        showNotification(newDevice, R.string.notification_low_battery);
        switch(newDevice.getType().getName()) {
            case "vacuum" -> {
                if (!newDevice.getState().getStatus().equals(oldDevice.getStatus())) {
                    int resId = switch(newDevice.getState().getStatus()) {
                        case "active" -> R.string.notification_turn_on;
                        case "inactive" -> R.string.notification_turn_off;
                        case "docked" -> R.string.notification_docked;
                        default -> throw new IllegalStateException();
                    };
                    showNotification(newDevice,resId);
                }
                else if(newDevice.getState().getBatteryLevel() <= 10 && oldDevice.getBattery() > 10) {
                    showNotification(newDevice, R.string.notification_low_battery);
                }
                else if (newDevice.getState().getBatteryLevel() == 100 && oldDevice.getBattery() < 100) {
                    showNotification(newDevice, R.string.notification_charged);
                }
            }
            case "blinds" -> {
                if (!newDevice.getState().getStatus().equals(oldDevice.getStatus())) {
                    switch(newDevice.getState().getStatus()) {
                        case "opened" -> {
                            showNotification(newDevice, R.string.notification_open);
                        }
                        case "closed" -> {
                            showNotification(newDevice, R.string.notification_close);
                        }
                        default -> {}
                    }


                }
            }

            default -> {
                if (!newDevice.getState().getStatus().equals(oldDevice.getStatus())) {
                    switch(newDevice.getState().getStatus()) {
                        case "on" -> {
                            showNotification(newDevice, R.string.notification_turn_on);
                        }
                        case "off" -> {
                            showNotification(newDevice, R.string.notification_turn_off);
                        }
                        default -> {}
                    }
                }
            }

        }
    }

    private void createNotificationChannel() {

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL, "name", importance);
        channel.setDescription("description");
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = application.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showNotification(RemoteDevice device, int resId) {
        // Create the intent to start Activity when notification in action bar is
        // clicked.
        RoomData roomData = new RoomData(device.getRoom().getName(), device.getRoom().getId());

        DeviceData deviceData = new DeviceData(device.getName(), device.getId(),roomData, getType(device.getType().getName()));

        Bundle bundle = new Bundle();
        bundle.putSerializable("deviceData", deviceData);

        PendingIntent pendingIntent = new NavDeepLinkBuilder(application)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.navigation_device)
                .setArguments(bundle)
                .createPendingIntent();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(application, CHANNEL)
                .setSmallIcon(deviceData.getType().getIcon())
                .setContentTitle(String.format(application.getString(R.string.device_from), device.getName(), device.getRoom().getName()))
                .setContentText(application.getString(resId))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(application.getString(resId)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from (application);
        notificationManager.notify(device.getId().hashCode(), builder.build());
        // podria llegar a darse el caso de que dos ids distintos generen el mismo hashcode. Se puede solucionar agregando el id a la base de datos
    }
    }