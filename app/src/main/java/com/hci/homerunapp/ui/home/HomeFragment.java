package com.hci.homerunapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    CustomAdapter adapter;
    HomeViewModel homeViewModel;

    public View.OnClickListener getButtonClickListener(RoomData roomData) {
        return (it) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("roomData", roomData);

                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.hideBottomNav();

                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_room, bundle);
        };
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        adapter = new CustomAdapter(homeViewModel.getRooms(), this);

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.homeRecyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}