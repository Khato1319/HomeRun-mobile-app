package com.hci.homerunapp.ui.routines;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.home.HomeViewModel;
import com.hci.homerunapp.ui.home.SimpleButtonAdapter;
import com.hci.homerunapp.ui.home.SimpleButtonFragment;
import com.hci.homerunapp.ui.home.SimpleButtonViewModel;

public class RoutinesFragment extends SimpleButtonFragment {


    @Override
    protected SimpleButtonViewModel getViewModel() {
        return new ViewModelProvider(this).get(RoutinesViewModel.class);
    }
    @Override
    public View.OnClickListener getButtonClickListener(Data data) {
        return getButtonClickListener(data, "routineData", R.id.action_navigation_routines_to_routineFragment);

    }


}