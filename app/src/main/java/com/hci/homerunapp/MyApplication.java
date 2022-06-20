package com.hci.homerunapp;

import android.app.Application;

import androidx.room.Room;

import com.hci.homerunapp.data.AppExecutors;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.remote.ApiClient;
import com.hci.homerunapp.data.remote.room.ApiRoomService;
import com.hci.homerunapp.data.remote.routine.ApiRoutineService;

public class MyApplication extends Application {

    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;
    RoutineRepository routineRepository;

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }
    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).build();
        roomRepository = new RoomRepository(appExecutors, roomService, database);

        ApiRoutineService routineService = ApiClient.create(ApiRoutineService.class);
        routineRepository = new RoutineRepository(appExecutors, routineService, database);
    }
}
