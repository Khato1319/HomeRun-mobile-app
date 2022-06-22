package com.hci.homerunapp.ui.recents;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.PrimaryFragment;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentRecentsBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.room.SimpleDeviceButtonAdapter;

public class RecentsFragment extends PrimaryFragment implements ButtonListenerMaker {

    protected RecentsViewModel model;
    SimpleDeviceButtonAdapter adapter;
    MainActivity activity;

//    public static SimpleDeviceButtonFragment newInstance() {
//        return new SimpleDeviceButtonFragment();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(RecentsViewModel.class);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        onCreate(savedInstanceState);

        label = getResources().getString(R.string.title_recents);

        activity = (MainActivity) getActivity();

        FragmentRecentsBinding binding = FragmentRecentsBinding.inflate(inflater, container, false);

        model.setDevices(((MainActivity)getActivity()).getRecentDevices());
        adapter = new SimpleDeviceButtonAdapter(model.getDevices(), this);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int orientation = this.getResources().getConfiguration().orientation;

        if (isTablet) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.recentsRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            else
                binding.recentsRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));

        }
        else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.recentsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            else
                binding.recentsRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        }
        binding.recentsRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

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



    protected int getNavigationId() {
        return R.id.action_navigation_recents_to_navigation_device;
    }


    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }


}