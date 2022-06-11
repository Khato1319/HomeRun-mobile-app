package com.hci.homerunapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hci.homerunapp.MainActivity;
import com.hci.homerunapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<String> dataSet;
    private final HomeFragment homeFragment;



    CustomAdapter(List<String> dataSet, HomeFragment homeFragment) {
        this.dataSet = dataSet;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item, parent, false);

        Button roomButton = view.findViewById(R.id.room_button);

        roomButton.setOnClickListener(homeFragment.getButtonClickListener(roomButton));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        holder.getButton().setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button roomButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomButton = itemView.findViewById(R.id.room_button);
        }

        public TextView getButton() {
            return roomButton;
        }
    }
}
