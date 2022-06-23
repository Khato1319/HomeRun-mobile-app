package com.hci.homerunapp.ui.routines;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;

import java.util.List;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private final List<RoutineData> elements;
    private final ButtonListenerMaker buttonListenerMaker;

    RoutinesAdapter(List<RoutineData> elements, ButtonListenerMaker buttonListenerMaker) {
        this.elements = elements;
        this.buttonListenerMaker = buttonListenerMaker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Button button = holder.getButton();
        Data data = elements.get(position);

        button.setOnClickListener(buttonListenerMaker.getButtonClickListener(data));

        button.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.routine_button);
        }

        public Button getButton() {
            return button;
        }
    }
}
