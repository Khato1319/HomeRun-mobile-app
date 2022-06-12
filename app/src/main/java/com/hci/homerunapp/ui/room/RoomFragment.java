package com.hci.homerunapp.ui.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentRoomBinding;
import com.hci.homerunapp.ui.home.RoomData;

import java.util.List;

public class RoomFragment extends Fragment {

    private RoomViewModel model;
    CustomAdapter adapter;
    public static final String ROOM_DATA = "com.hci.homerunapp.ui.room/roomId";
    private RoomData roomData;

    public static RoomFragment newInstance() {
        return new RoomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        if (firstTime) {
//            deviceNames = Arrays.asList("Aspiradora 1", "Cortinas", "Speaker");
//            deviceRooms = Arrays.asList("Bedroom", "Kitchen", "Living");
//            firstTime = false;
//        }
        Log.d("CREATED", "CREAAADOOOOOO");
        model = new ViewModelProvider(this).get(RoomViewModel.class);

        Bundle args = getArguments();

        if (args != null) {
            roomData = (RoomData)args.get("roomData");
            model.setRoomData(roomData);
        }

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            model.setRoomData((RoomData)savedInstanceState.getSerializable(ROOM_DATA));
        }

        FragmentRoomBinding binding = FragmentRoomBinding.inflate(inflater, container, false);

        adapter = new CustomAdapter(model.getDevices(), model.getRoomData(), this);

        binding.roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.roomRecyclerView.setAdapter(adapter);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(roomData.getName());
        Log.d("MODELCREATE", String.valueOf(model));


        return binding.getRoot();
    }

    public View.OnClickListener getButtonClickListener(DeviceData deviceData) {
        return (it) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("deviceData", deviceData);
//            bundle.putCharSequence("deviceRoom", deviceRoom);
//            bundle.putCharSequence("deviceId", deviceId);

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null)
                mainActivity.hideBottomNav();
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_room_to_navigation_device, bundle);
        };
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("MODEL", String.valueOf(model));
        Log.d("DATA", String.valueOf(roomData));

        // Save counter variable current value so it can be restored
        // when activity is recreated
        outState.putSerializable(ROOM_DATA, roomData);
    }





}