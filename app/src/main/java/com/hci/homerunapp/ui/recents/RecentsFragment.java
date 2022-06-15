package com.hci.homerunapp.ui.recents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentRecentsBinding;
import com.hci.homerunapp.ui.room.CustomAdapter;
import com.hci.homerunapp.ui.room.RoomViewModel;
import com.hci.homerunapp.ui.room.SimpleDeviceButtonAdapter;
import com.hci.homerunapp.ui.room.SimpleDeviceButtonFragment;
import com.hci.homerunapp.ui.room.SimpleDeviceButtonViewModel;

public class RecentsFragment extends SimpleDeviceButtonFragment<RecentsViewModel> {


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