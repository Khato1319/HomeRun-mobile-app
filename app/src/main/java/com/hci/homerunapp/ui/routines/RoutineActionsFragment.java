package com.hci.homerunapp.ui.routines;

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

import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.databinding.FragmentRoutinesBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.DataRepositoryViewModel;
import com.hci.homerunapp.ui.DataRepositoryViewModelFactory;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.PrimaryFragment;
import com.hci.homerunapp.ui.RepositoryViewModelFactory;
import com.hci.homerunapp.ui.SecondaryFragment;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoutineActionsFragment extends SecondaryFragment implements ButtonListenerMaker {

    private FragmentRoutinesBinding binding;
    RoutineActionsAdapter adapter;
    RoutineActionsViewModel routinesViewModel;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutinesBinding.inflate(inflater, container, false);

        MyApplication application = (MyApplication) getActivity().getApplication();
        activity = (MainActivity) getActivity();

        Bundle args = getArguments();
        RoutineData routineData = null;
        if (args != null)
            routineData = (RoutineData)args.get("routineData");

        DataRepositoryViewModelFactory<RoutineRepository, RoutineData> viewModelFactory = new DataRepositoryViewModelFactory<RoutineRepository, RoutineData>(RoutineRepository.class, application.getRoutineRepository(), RoutineData.class, routineData);
        routinesViewModel = new ViewModelProvider(this,viewModelFactory).get(RoutineActionsViewModel.class);

        label = routinesViewModel.getData().getName();

        adapter = new RoutineActionsAdapter(routinesViewModel.getData().getActions(), this);

//        routinesViewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
//            switch (resource.status) {
//                case LOADING -> activity.showProgressBar();
//                case SUCCESS -> {
//                    activity.hideProgressBar();
//                    routines.clear();
//                    if (resource.data != null &&
//                            resource.data.size() > 0) {
//                        routines.addAll(resource.data);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });


        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int orientation = this.getResources().getConfiguration().orientation;

        if (isTablet) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.routinesRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            else
                binding.routinesRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));

        }
        else {
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.routinesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            else
                binding.routinesRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        }
        binding.routinesRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null)
            mainActivity.showBottomNav();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View.OnClickListener getButtonClickListener(Data data) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

    }


    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }


}