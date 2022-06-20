package com.hci.homerunapp;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.hci.homerunapp.data.AppExecutors;
import com.hci.homerunapp.data.DeviceRepository;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.remote.ApiClient;
import com.hci.homerunapp.data.remote.device.ApiDeviceService;
import com.hci.homerunapp.data.remote.room.ApiRoomService;

public class MyApplication extends Application {

    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;
    DeviceRepository deviceRepository;

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d("String de prueba", getApplicationContext().getResources().getString(R.string.vacuum_location));
        appExecutors = new AppExecutors();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
        ApiDeviceService deviceService = ApiClient.create(ApiDeviceService.class);
        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).build();
        roomRepository = new RoomRepository(appExecutors, roomService, database);
        deviceRepository = new DeviceRepository(deviceService, appExecutors, getApplicationContext(), database);


    }
}
