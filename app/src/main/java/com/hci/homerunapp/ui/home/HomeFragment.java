package com.hci.homerunapp.ui.home;

import android.view.View;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;


public class HomeFragment extends SimpleButtonFragment {

    @Override
    protected SimpleButtonViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    public View.OnClickListener getButtonClickListener(Data roomData) {
        return getButtonClickListener(roomData, "roomData", R.id.action_navigation_home_to_navigation_room);
    }


}