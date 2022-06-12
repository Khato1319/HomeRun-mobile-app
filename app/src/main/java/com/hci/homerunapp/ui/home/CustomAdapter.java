package com.hci.homerunapp.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hci.homerunapp.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<RoomData> rooms;
    private final HomeFragment homeFragment;



    CustomAdapter(List<RoomData> rooms, HomeFragment homeFragment) {
        this.rooms = rooms;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item, parent, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Button roomButton = holder.getButton();
        RoomData roomData = rooms.get(position);

        roomButton.setOnClickListener(homeFragment.getButtonClickListener(roomData));

        roomButton.setText(roomData.getName());

    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button roomButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomButton = itemView.findViewById(R.id.room_button);
        }

        public Button getButton() {
            return roomButton;
        }
    }
}
