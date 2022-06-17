package com.hci.homerunapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;


public class HomeFragment extends SimpleButtonFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        label = "Habitaciones";
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected SimpleButtonViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    public View.OnClickListener getButtonClickListener(Data roomData) {
        return getButtonClickListener(roomData, "roomData", R.id.action_navigation_home_to_navigation_room);
    }


}