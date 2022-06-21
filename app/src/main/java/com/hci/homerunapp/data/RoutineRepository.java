package com.hci.homerunapp.data;

import static java.util.stream.Collectors.toList;

import android.app.RemoteAction;
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
import com.hci.homerunapp.data.remote.routine.RemoteRoutineAction;
import com.hci.homerunapp.ui.routines.RoutineAction;
import com.hci.homerunapp.ui.routines.RoutineData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//    private RoutineData mapRoutineLocalToModel(LocalRoutine local){
//        return new RoutineData(local.name, local. id);
//    }
//
//    private LocalRoutine mapRoutineRemoteToLocal(RemoteRoutine remote){
//        return new LocalRoutine(remote.getId(), remote.getName());
//    }

    private RoutineData mapRoutineRemoteToModel(RemoteRoutine remote){
        RoutineData routineData = new RoutineData(remote.getName(), remote.getId());
        List<RoutineAction> actions = new ArrayList<>();
        for (RemoteRoutineAction action : remote.getActions()) {
            actions.add(new RoutineAction(action.getDevice().getName(),
                    action.getDevice().getType().getName(),
                    action.getActionName(),
                    action.getParams().size() == 0 ? null : action.getParams().get(0),
                    action.getDevice().getRoom().getName()));
        }

        routineData.setActions(actions);
        return routineData;
    }

//    private RemoteRoutine mapRoutineModelToRemote(RoutineData model){
//        RemoteRoutine remote=new RemoteRoutine();
//        remote.setId(model.getId());
//        remote.setName(model.getName());
//        return remote;
//    }

    public LiveData<Resource<List<RoutineData>>> getRoutines() {
        Log.d(TAG, "RoutineRepository - getRoutines()");
        return new NetworkBoundResource<List<RoutineData>, List<LocalRoutine>, List<RemoteRoutine>>(
                executors,
                null,
                null,
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoutineRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoutine> locals) {
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoutine> locals) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoutine> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoutine>> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> createCall() {
                return service.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> executeRoutine(RoutineData routine) {
        Log.d(TAG, "RoutineRepository - executeRoutine()");
        return new NetworkBoundResource<Void, LocalRoutine, Object>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                //database.roomDao().delete(room.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Object remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Object>>> createCall() {
                return service.executeRoutine(routine.getId());
            }
        }.asLiveData();
    }
}
