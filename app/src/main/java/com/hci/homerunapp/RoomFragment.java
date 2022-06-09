package com.hci.homerunapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hci.homerunapp.databinding.FragmentRoomBinding;

public class RoomFragment extends Fragment {

    private RoomViewModel mViewModel;

    public static RoomFragment newInstance() {
        return new RoomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentRoomBinding binding = FragmentRoomBinding.inflate(inflater, container, false);

        assert getArguments() != null;

        binding.roomText.setText(getArguments().getString("roomName"));

//        return inflater.inflate(R.layout.fragment_room, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        // TODO: Use the ViewModel
    }

}