package com.hci.homerunapp.ui.routines;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.hci.homerunapp.data.AbsentLiveData;
import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.data.Status;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.DataRepositoryViewModel;
import com.hci.homerunapp.ui.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoutineActionsViewModel extends DataRepositoryViewModel<RoutineRepository, RoutineData>{



    public RoutineActionsViewModel(RoutineRepository routineRepository, RoutineData routineData) {
        super(routineRepository, routineData);

    }


    public LiveData<Resource<Void>> executeRoutine(RoutineData routine) {
        return repository.executeRoutine(routine);
    }

}