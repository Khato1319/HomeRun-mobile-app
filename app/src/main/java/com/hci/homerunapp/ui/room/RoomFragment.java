package com.hci.homerunapp.ui.room;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;
import com.hci.homerunapp.databinding.FragmentRoomBinding;
import com.hci.homerunapp.ui.room.CustomAdapter;

import java.util.Arrays;
import java.util.List;

public class RoomFragment extends Fragment {

    private RoomViewModel mViewModel;
    List<String> deviceNames = Arrays.asList("Aspiradora 1", "Cortinas", "Speaker");
    List<String> deviceRooms = Arrays.asList("Bedroom", "Kitchen", "Living");
    CustomAdapter adapter;

    public static RoomFragment newInstance() {
        return new RoomFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentRoomBinding binding = FragmentRoomBinding.inflate(inflater, container, false);

        adapter = new CustomAdapter(deviceNames, deviceRooms, this);

        binding.roomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        binding.roomRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    public View.OnClickListener getButtonClickListener(CharSequence deviceName, CharSequence deviceRoom, CharSequence deviceId) {
        return (it) -> {
            Bundle bundle = new Bundle();
            bundle.putCharSequence("deviceName", deviceName);
            bundle.putCharSequence("deviceRoom", deviceRoom);
            bundle.putCharSequence("deviceId", deviceId);

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null)
                mainActivity.hideBottomNav();
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_room_to_navigation_device, bundle);
        };
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        // TODO: Use the ViewModel
    }

}