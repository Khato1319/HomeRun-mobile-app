package com.hci.homerunapp.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.local.room.LocalRoom;
import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.room.ApiRoomService;
import com.hci.homerunapp.data.remote.room.RemoteRoom;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoomRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoomService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }


    public RoomRepository(AppExecutors executors, ApiRoomService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private RoomData mapRoomLocalToModel(LocalRoom local) {
        return new RoomData(local.name, local.id);
    }

    private LocalRoom mapRoomRemoteToLocal(RemoteRoom remote) {
        return new LocalRoom(remote.getId(), remote.getName());
    }

    private RoomData mapRoomRemoteToModel(RemoteRoom remote) {
        return new RoomData(remote.getName(), remote.getId());
    }

    private RemoteRoom mapRoomModelToRemote(RoomData model) {
        RemoteRoom remote = new RemoteRoom();
        remote.setId(model.getId());
        remote.setName(model.getName());
        return remote;
    }

    public LiveData<Resource<List<RoomData>>> getRooms() {
        Log.d(TAG, "RoomRepository - getRooms()");
        return new NetworkBoundResource<List<RoomData>, List<LocalRoom>, List<RemoteRoom>>(
                executors,
                locals -> {
                    return locals.stream()
                            .map(this::mapRoomLocalToModel)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoomRemoteToLocal)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoomRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoom> locals) {
                database.roomDao().deleteAll();
                database.roomDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoom> locals) {
                return ((locals == null) || (locals.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoom> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoom>> loadFromDb() {
                return database.roomDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoom>>>> createCall() {
                return service.getRooms();
            }
        }.asLiveData();
    }
}
