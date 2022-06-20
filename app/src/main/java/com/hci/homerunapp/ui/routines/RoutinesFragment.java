package com.hci.homerunapp.ui.routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.data.RoomRepository;
import com.hci.homerunapp.data.RoutineRepository;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.PrimaryFragment;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentRoutinesBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.RepositoryViewModelFactory;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.ArrayList;
import java.util.List;

public class RoutinesFragment extends PrimaryFragment implements ButtonListenerMaker {

    private FragmentRoutinesBinding binding;
    RoutinesAdapter adapter;
    RoutinesViewModel routinesViewModel;
    private MainActivity activity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutinesBinding.inflate(inflater, container, false);
        label = getResources().getString(R.string.title_routines);

        MyApplication application = (MyApplication) getActivity().getApplication();
        activity = (MainActivity) getActivity();

        RepositoryViewModelFactory<RoutineRepository> viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        routinesViewModel = new ViewModelProvider(this,viewModelFactory).get(RoutinesViewModel.class);

        List<RoutineData> routines = new ArrayList<>();
        adapter = new RoutinesAdapter(routines, this);

        routinesViewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING -> activity.showProgressBar();
                case SUCCESS -> {
                    activity.hideProgressBar();
                    routines.clear();
                    if (resource.data != null &&
                            resource.data.size() > 0) {
                        routines.addAll(resource.data);
                        adapter.notifyDataSetChanged();
//                        binding.list.setVisibility(View.VISIBLE);
//                        binding.empty.setVisibility(View.GONE);
                    } else {
//                        binding.list.setVisibility(View.GONE);
//                        binding.empty.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        binding.routinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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