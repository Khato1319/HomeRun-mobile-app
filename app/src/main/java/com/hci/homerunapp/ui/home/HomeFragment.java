package com.hci.homerunapp.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends PrimaryFragment implements ButtonListenerMaker {
    private FragmentHomeBinding binding;
    HomeViewAdapter adapter;
    HomeViewModel homeViewModel;
    private MainActivity activity;
    List<RoomData> rooms;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        label = getResources().getString(R.string.title_rooms);

        MyApplication application = (MyApplication) getActivity().getApplication();
        activity = (MainActivity)getActivity();

        RepositoryViewModelFactory<RoomRepository> viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
        homeViewModel =  new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);

        rooms = new ArrayList<>();
        adapter =  new HomeViewAdapter(rooms, this, R.id.simple_button, R.layout.simple_button_layout);

        homeViewModel.getRooms().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING -> activity.showProgressBar();
                case SUCCESS -> {
                    activity.hideProgressBar();
                    rooms.clear();
//                    activity.setRooms(rooms);
                    if (resource.data != null &&
                            resource.data.size() > 0) {
                        rooms.addAll(resource.data);
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

//        binding.list.setHasFixedSize(true);
//        binding.list.setLayoutManager(new LinearLayoutManager(activity));
//        binding.list.setAdapter(adapter);

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.homeRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public View.OnClickListener getButtonClickListener(Data roomData) {
        return getButtonClickListener(roomData, "roomData", R.id.action_navigation_home_to_navigation_room);
    }


    @Override
    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }


}