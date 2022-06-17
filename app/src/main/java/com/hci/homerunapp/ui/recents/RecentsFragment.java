package com.hci.homerunapp.ui.recents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.room.SimpleDeviceButtonAdapter;
import com.hci.homerunapp.ui.room.PrimarySimpleDeviceButtonFragment;

public class RecentsFragment extends PrimarySimpleDeviceButtonFragment<RecentsViewModel> {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        label = getResources().getString(R.string.title_recents);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected RecentsViewModel getViewModel() {
        return new ViewModelProvider(this).get(RecentsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null)
            mainActivity.showBottomNav();
    }

    @Override
    protected SimpleDeviceButtonAdapter getAdapter() {
        return new SimpleDeviceButtonAdapter(model.getDevices(), this);
    }

    @Override
    protected int getNavigationId() {
        return R.id.action_navigation_recents_to_navigation_device;
    }
}