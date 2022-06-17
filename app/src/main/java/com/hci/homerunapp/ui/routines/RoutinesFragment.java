package com.hci.homerunapp.ui.routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.home.HomeViewModel;
import com.hci.homerunapp.ui.home.SimpleButtonAdapter;
import com.hci.homerunapp.ui.home.SimpleButtonFragment;
import com.hci.homerunapp.ui.home.SimpleButtonViewModel;

public class RoutinesFragment extends SimpleButtonFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        label = "Rutinas";
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected SimpleButtonViewModel getViewModel() {
        return new ViewModelProvider(this).get(RoutinesViewModel.class);
    }
    @Override
    public View.OnClickListener getButtonClickListener(Data data) {
        return getButtonClickListener(data, "routineData", R.id.action_navigation_routines_to_routineFragment);

    }


}