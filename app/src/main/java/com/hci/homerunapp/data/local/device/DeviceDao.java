package com.hci.homerunapp.data.local.device;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class DeviceDao {

    @Query("SELECT * FROM Room")
    public abstract LiveData<List<LocalDevice>> findAll();

    @Query("SELECT * FROM Room LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<LocalDevice>> findAll(int limit, int offset);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(LocalDevice... room);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<LocalDevice> rooms);

    @Update
    public abstract void update(LocalDevice room);

    @Delete
    public abstract void delete(LocalDevice room);

    @Query("DELETE FROM Room WHERE id = :id")
    public abstract void delete(String id);

    @Query("DELETE FROM Room WHERE id IN (SELECT id FROM Room LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM Room")
    public abstract void deleteAll();

    @Query("SELECT * FROM Room WHERE id = :id")
    public abstract LiveData<LocalDevice> findById(String id);
}