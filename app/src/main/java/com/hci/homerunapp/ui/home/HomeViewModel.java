package com.hci.homerunapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.data.Resource;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.Status;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends RepositoryViewModel<RoomRepository> {
    protected List<Data> elements = new ArrayList<>();
    private final MediatorLiveData<Resource<List<RoomData>>> rooms = new MediatorLiveData<>();

    public List<Data> getElements() {
        return elements;
    }


    public HomeViewModel(RoomRepository roomRepository) {
            super(roomRepository);

    }

    public LiveData<Resource<List<RoomData>>> getRooms() {
        loadRooms();
        return rooms;
    }

    private void loadRooms() {
        rooms.addSource(repository.getRooms(), resource -> {
            if (resource.status == Status.SUCCESS) {
                rooms.setValue(Resource.success(resource.data));
            } else {
                rooms.setValue(resource);
            }
        });
    }

}
