package com.hci.homerunapp.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.hci.homerunapp.data.local.MyDatabase;
import com.hci.homerunapp.data.local.routine.LocalRoutine;
import com.hci.homerunapp.data.remote.ApiResponse;
import com.hci.homerunapp.data.remote.RemoteResult;
import com.hci.homerunapp.data.remote.routine.ApiRoutineService;
import com.hci.homerunapp.data.remote.routine.RemoteRoutine;
import com.hci.homerunapp.ui.routines.RoutineData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoutineRepository {
    private static final String TAG="data";
    private static final String RATE_LIMITER_ALL_KEY="@@all@@";

    private AppExecutors executors;
    private ApiRoutineService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public RoutineRepository(AppExecutors executors, ApiRoutineService service, MyDatabase database){
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private RoutineData mapRoutineLocalToModel(LocalRoutine local){
        return new RoutineData(local.name, local. id);
    }

    private LocalRoutine mapRoutineRemoteToLocal(RemoteRoutine remote){
        return new LocalRoutine(remote.getId(), remote.getName());
    }

    private RoutineData mapRoutineRemoteToModel(RemoteRoutine remote){
        return new RoutineData(remote.getName(), remote.getId());
    }

    private RemoteRoutine mapRoutineModelToRemoet(RoutineData model){
        RemoteRoutine remote=new RemoteRoutine();
        remote.setId(model.getId());
        remote.setName(model.getName());
        return remote;
    }

    public LiveData<Resource<List<RoutineData>>> getRoutines() {
        Log.d(TAG, "RoutineRepository - getRoutines()");
        return new NetworkBoundResource<List<RoutineData>, List<LocalRoutine>, List<RemoteRoutine>>(
                executors,
                locals -> {
                    return locals.stream()
                            .map(this::mapRoutineLocalToModel)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoutineRemoteToLocal)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoutineRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoutine> locals) {
                database.routineDao().deleteAll();
                database.routineDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoutine> locals) {
                return ((locals == null) || (locals.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoutine> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoutine>> loadFromDb() {
                return database.routineDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> createCall() {
                return service.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<RoutineData>> getRoutine(String routineId) {
        Log.d(TAG, "getRoutine()");
        return new NetworkBoundResource<RoutineData, LocalRoutine, RemoteRoutine>(
                executors,
                this::mapRoutineLocalToModel,
                this::mapRoutineRemoteToLocal,
                this::mapRoutineRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                database.routineDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteRoutine remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                return database.routineDao().findById(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> createCall() {
                return service.getRoutine(routineId);
            }
        }.asLiveData();
    }
}
