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
    protected List<Data> elements = new ArrayList<>();
    private final MediatorLiveData<Resource<List<RoutineData>>> routines = new MediatorLiveData<>();

    //Guarda la que está seleccionada
    private final MutableLiveData<String> routineId = new MutableLiveData<>();
    private final LiveData<Resource<RoutineData>> routine;

//    public List<Data> getElements() {
//        return elements;
//    }

    public RoutinesViewModel(RoutineRepository routineRepository) {
        super(routineRepository);
        routine = Transformations.switchMap(routineId, routineId -> {
            if (routineId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getRoutine(routineId);
            }
        });
    }

    public LiveData<Resource<List<RoutineData>>> getRoutines(){
        loadRoutines();
        return routines;
    }

    public LiveData<Resource<RoutineData>> getRoutine() {
        return routine;
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