package com.hci.homerunapp.data.local;


import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.hci.homerunapp.data.local.device.DeviceDao;
import com.hci.homerunapp.data.local.device.LocalDevice;
import com.hci.homerunapp.data.local.room.LocalRoom;
import com.hci.homerunapp.data.local.room.RoomDao;



@Database(entities = {LocalRoom.class, LocalDevice.class}, exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    abstract public RoomDao roomDao();

    abstract public DeviceDao deviceDao();
}
