package com.hci.homerunapp.ui.routines;

import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.databinding.FragmentRoutineActionsBinding;

import com.hci.homerunapp.ui.DataRepositoryViewModelFactory;
import com.hci.homerunapp.ui.MainActivity;

import com.hci.homerunapp.ui.SecondaryFragment;


public class RoutineActionsFragment extends SecondaryFragment {
    private FragmentRoutineActionsBinding binding;
    RoutineActionsAdapter adapter;
    RoutineActionsViewModel routinesViewModel;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutineActionsBinding.inflate(inflater, container, false);

        MyApplication application = (MyApplication) getActivity().getApplication();
        activity = (MainActivity) getActivity();

        Bundle args = getArguments();
        RoutineData routineData = null;
        if (args != null)
            routineData = (RoutineData)args.get("routineData");

        DataRepositoryViewModelFactory<RoutineRepository, RoutineData> viewModelFactory = new DataRepositoryViewModelFactory<RoutineRepository, RoutineData>(RoutineRepository.class, application.getRoutineRepository(), RoutineData.class, routineData);
        routinesViewModel = new ViewModelProvider(this,viewModelFactory).get(RoutineActionsViewModel.class);

        label = routinesViewModel.getData().getName();

        adapter = new RoutineActionsAdapter(routinesViewModel.getData().getActions());

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int orientation = this.getResources().getConfiguration().orientation;

        if (isTablet) {
            binding.executeButton.setVisibility(View.INVISIBLE);
            binding.executeButtonTablet.setVisibility(View.VISIBLE);
            binding.executeButtonTablet.setOnClickListener(this::executeRoutine);


            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.routinesRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            else
                binding.routinesRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        }
        else {
            binding.executeButton.setVisibility(View.VISIBLE);
            binding.executeButtonTablet.setVisibility(View.INVISIBLE);
            binding.executeButton.setOnClickListener(this::executeRoutine);
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                binding.routinesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
            else
                binding.routinesRecyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        }
        binding.routinesRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    private void executeRoutine(View view) {
        routinesViewModel.executeRoutine().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    activity.showProgressBar();
                    break;
                case SUCCESS:
                    activity.hideProgressBar();
                    Snackbar.make(view, R.string.routine_exec_success, Snackbar.LENGTH_LONG).show();
                    break;
                case ERROR:
                    activity.hideProgressBar();
                    Snackbar.make(view, R.string.routine_exec_failure, Snackbar.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}