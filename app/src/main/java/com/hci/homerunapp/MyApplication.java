package com.hci.homerunapp;

import android.app.Application;

import androidx.room.Room;

import com.hci.homerunapp.data.AppExecutors;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.remote.ApiClient;
import com.hci.homerunapp.data.remote.room.ApiRoomService;

public class MyApplication extends Application {

    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).build();
        roomRepository = new RoomRepository(appExecutors, roomService, database);
    }
}
