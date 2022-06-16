package com.hci.homerunapp.ui.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.SecondaryFragment;
import com.hci.homerunapp.databinding.FragmentRoomBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.home.RoomData;
import com.hci.homerunapp.ui.home.SimpleButtonAdapter;

public abstract class SimpleDeviceButtonFragment<T extends SimpleDeviceButtonViewModel> extends SecondaryFragment implements ButtonListenerMaker {

    protected T model;
    SimpleDeviceButtonAdapter adapter;

//    public static SimpleDeviceButtonFragment newInstance() {
//        return new SimpleDeviceButtonFragment();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = getViewModel();

        

    }

    protected abstract T getViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);


        FragmentRoomBinding binding = FragmentRoomBinding.inflate(inflater, container, false);

        adapter = getAdapter();

        binding.roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.roomRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    protected abstract SimpleDeviceButtonAdapter getAdapter();

    @Override
    public View.OnClickListener getButtonClickListener(Data deviceData) {
        return (it) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("deviceData", deviceData);


            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null)
                mainActivity.hideBottomNav();
            NavHostFragment.findNavController(this).navigate(getNavigationId(), bundle);
        };
    }

    protected abstract int getNavigationId();


    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }


}