package com.hci.homerunapp.ui.routines;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.ButtonListenerMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoutineActionsAdapter extends RecyclerView.Adapter<RoutineActionsAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private List<Map.Entry<String, Object>> actions;
    private final ButtonListenerMaker buttonListenerMaker;


    RoutineActionsAdapter(Map<String, Object> actions, ButtonListenerMaker buttonListenerMaker) {
        this.actions = new ArrayList<>(actions.entrySet());
        this.buttonListenerMaker = buttonListenerMaker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_action, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        Map.Entry<String, Object> action = actions.get(position);

        holder.getDeviceText().setText(action.getKey());
        holder.getActionText().setText(String.valueOf(action.getValue()));


    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView deviceText;
        private final TextView actionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceText = itemView.findViewById(R.id.text_device);
            actionText = itemView.findViewById(R.id.text_action);
        }

        public TextView getDeviceText() {
            return deviceText;
        }

        public TextView getActionText() {
            return actionText;
        }
    }
}
