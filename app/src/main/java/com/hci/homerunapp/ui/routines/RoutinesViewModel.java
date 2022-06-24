package com.hci.homerunapp.ui.routines;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.data.AbsentLiveData;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.data.Status;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoutinesViewModel  extends RepositoryViewModel<RoutineRepository> {
    private final MediatorLiveData<Resource<List<RoutineData>>> routines = new MediatorLiveData<>();

    public RoutinesViewModel(RoutineRepository routineRepository) {
        super(routineRepository);

    }

    public LiveData<Resource<List<RoutineData>>> getRoutines(){
        loadRoutines();
        return routines;
    }

    public LiveData<Resource<Void>> executeRoutine(RoutineData routine) {
        return repository.executeRoutine(routine);
    }

    private void loadRoutines() {
        routines.addSource(repository.getRoutines(), resource -> {
            if (resource.status == Status.SUCCESS) {
                 routines.setValue(Resource.success(resource.data));
            } else {
                routines.setValue(resource);
            }
        });
    }
}