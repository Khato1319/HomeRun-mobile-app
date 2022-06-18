package com.hci.homerunapp.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hci.homerunapp.ui.ButtonListenerMaker;
import com.hci.homerunapp.ui.Data;

import java.util.List;

public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<? extends Data> elements;
    private final ButtonListenerMaker buttonListenerMaker;
    private final int buttonId, layoutId;


    HomeViewAdapter(List<? extends Data> elements, ButtonListenerMaker buttonListenerMaker, int buttonId, int layoutId) {
        this.elements = elements;
        this.buttonListenerMaker = buttonListenerMaker;
        this.buttonId = buttonId;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        return new ViewHolder(view, buttonId);
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

        public ViewHolder(@NonNull View itemView, int viewId) {
            super(itemView);
            button = itemView.findViewById(viewId);
        }

        public Button getButton() {
            return button;
        }
    }
}
