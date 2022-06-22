package com.hci.homerunapp;

import android.app.Application;

import androidx.room.Room;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


import com.hci.homerunapp.data.AppExecutors;
import com.hci.homerunapp.data.DeviceRepository;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.remote.ApiClient;
import com.hci.homerunapp.data.remote.device.ApiDeviceService;
import com.hci.homerunapp.data.remote.room.ApiRoomService;
import com.hci.homerunapp.data.remote.routine.ApiRoutineService;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    private static final long MY_SCHEDULE_TIME = 15;
    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;

    DeviceRepository deviceRepository;

    RoutineRepository routineRepository;

    MyDatabase database;



    public RoomRepository getRoomRepository() {
        return roomRepository;
    }
    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    public MyDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {


        super.onCreate();
//        Log.d("String de prueba", getApplicationContext().getResources().getString(R.string.vacuum_location));
        appExecutors = new AppExecutors();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
        database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).build();
      
        roomRepository = new RoomRepository(appExecutors, roomService, database);
      
        ApiDeviceService deviceService = ApiClient.create(ApiDeviceService.class);
        deviceRepository = new DeviceRepository(deviceService, appExecutors, getApplicationContext(), database);

        ApiRoutineService routineService = ApiClient.create(ApiRoutineService.class);
        routineRepository = new RoutineRepository(appExecutors, routineService, database);

        PeriodicWorkRequest notificationWorkRequest =
                new PeriodicWorkRequest.Builder(MyNotificationWorker.class, MY_SCHEDULE_TIME, TimeUnit.MINUTES)
                        .setInitialDelay(3, TimeUnit.SECONDS).build();

        WorkManager
                .getInstance(this)
                .enqueueUniquePeriodicWork("MyNotificationWorker", ExistingPeriodicWorkPolicy.KEEP, notificationWorkRequest);

    }
}
