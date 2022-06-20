package com.hci.homerunapp.data.local;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hci.homerunapp.data.local.room.LocalRoom;
import com.hci.homerunapp.data.local.room.RoomDao;
import com.hci.homerunapp.data.local.routine.LocalRoutine;
import com.hci.homerunapp.data.local.routine.RoutineDao;


@Database(entities = {LocalRoom.class, LocalRoutine.class}, exportSchema = true, version = 2,
        autoMigrations = {
                @AutoMigration(from = 1, to = 2)
        })

public abstract class MyDatabase extends RoomDatabase {
    abstract public RoomDao roomDao();
    abstract public RoutineDao routineDao();
}
