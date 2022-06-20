package com.hci.homerunapp.ui.device;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.light.ColorPickerData;
import com.hci.homerunapp.ui.device.vacuum.ChangeLocationDropDownData;

import java.util.List;

public class ControlDataAdapter extends RecyclerView.Adapter<ControlData.ControlDataViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<ControlData> controls;
    private final DeviceFragment deviceFragment;

    ControlDataAdapter(List<ControlData> controls, DeviceFragment deviceFragment) {
        this.controls = controls;
        this.deviceFragment = deviceFragment;
    }

    @NonNull
    @Override
    public ControlData.ControlDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return switch(viewType) {
            case R.layout.slider_item -> new SliderData.ViewHolder(view);
            case R.layout.color_picker_item -> new ColorPickerData.ViewHolder(view);
            case R.layout.drop_down_container_item -> new DropDownData.ViewHolder(view);
            case R.layout.change_location_drop_down_container_item -> new ChangeLocationDropDownData.ViewHolder(view);
            case R.layout.switch_on_item -> new TurnOnButtonData.ViewHolder(view);
            case R.layout.toggle_button_item -> new ToggleButtonData.ViewHolder(view);
            case R.layout.progress_bar_item -> new ProgressBarData.ViewHolder(view);
            default ->
                throw new IllegalStateException("Unexpected value: " + viewType);
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ControlData.ControlDataViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

//        ControlData.ControlDataViewHolder<ControlData> viewHolder = (ControlData.ControlDataViewHolder<ControlData>) holder;

        ControlData controlData = controls.get(position);
//        controlData.setupViewHolder(holder);
        holder.bindTo(controlData);
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return controls.get(position).getLayoutId();
    }


    @Override
    public int getItemCount() {
        return controls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView controlText;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           controlText = itemView.findViewById(R.id.text_control);
        }


        public TextView getControlText() {
            return controlText;
        }


    }
}
