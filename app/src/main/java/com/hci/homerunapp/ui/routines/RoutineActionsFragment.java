package com.hci.homerunapp.ui.routines;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.hci.homerunapp.databinding.FragmentRoutineActionsBinding;
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
        binding.executeButton.setOnClickListener((it) -> executeRoutine());


        return binding.getRoot();
    }

    private void executeRoutine() {
        // Removed getRoom() observer to avoid null value update notification after delete.
        routinesViewModel.executeRoutine().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    activity.showProgressBar();
                    break;
                case SUCCESS:
                    activity.hideProgressBar();
                    //activity.popBackStack();
                    Toast.makeText(activity, R.string.routine_exec_success, Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    activity.hideProgressBar();
                    Toast.makeText(activity, resource.error.getDescription(), Toast.LENGTH_SHORT).show();
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
        if(mainActivity != null)
            mainActivity.showBottomNav();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





}