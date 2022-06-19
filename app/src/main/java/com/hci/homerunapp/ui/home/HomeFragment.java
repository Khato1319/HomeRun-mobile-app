package com.hci.homerunapp.ui.home;

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
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.PrimaryFragment;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentHomeBinding;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.RepositoryViewModelFactory;


public class HomeFragment extends PrimaryFragment implements ButtonListenerMaker {
    private FragmentHomeBinding binding;
    HomeViewAdapter adapter;
    HomeViewModel homeViewModel;
    private MainActivity activity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        label = getResources().getString(R.string.title_rooms);

        MyApplication application = (MyApplication) getActivity().getApplication();
        activity = (MainActivity)getActivity();

        adapter = getAdapter();

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(RoomRepository.class, application.getRoomRepository());
        homeViewModel =  new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.homeRecyclerView.setAdapter(adapter);

        View root = binding.getRoot();

        return root;
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


    public View.OnClickListener getButtonClickListener(Data roomData) {
        return getButtonClickListener(roomData, "roomData", R.id.action_navigation_home_to_navigation_room);
    }

    private HomeViewAdapter getAdapter(int buttonId, int layoutId) {
        return new HomeViewAdapter(homeViewModel.getElements(), this, R.id.simple_button, R.layout.simple_button_layout);
    }

    protected HomeViewAdapter getAdapter() {
        return getAdapter(R.id.simple_button, R.layout.simple_button_layout);
    }

    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }


}