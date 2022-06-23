package com.hci.homerunapp.ui.device;

import android.os.Handler;
import android.os.Looper;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.logging.LogRecord;

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
            case R.layout.slider_item -> new SliderData.ViewHolder(view, deviceFragment);
            case R.layout.dock_item -> new DockButtonData.ViewHolder(view, deviceFragment);
            case R.layout.color_picker_item -> new ColorPickerData.ViewHolder(view, deviceFragment);
            case R.layout.drop_down_container_item -> new DropDownData.ViewHolder(view, deviceFragment);
            case R.layout.change_location_drop_down_container_item -> new ChangeLocationDropDownData.ViewHolder(view, deviceFragment);
            case R.layout.switch_on_item -> new TurnOnButtonData.ViewHolder(view, deviceFragment);
            case R.layout.toggle_button_item -> new ToggleButtonData.ViewHolder(view, deviceFragment);
            case R.layout.progress_bar_item -> new ProgressBarData.ViewHolder(view, deviceFragment);
            default ->
                throw new IllegalStateException("Unexpected value: " + viewType);
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ControlData.ControlDataViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        ControlData controlData = controls.get(position);
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
        protected final DeviceFragment deviceFragment;

        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView);
           controlText = itemView.findViewById(R.id.text_control);
           this.deviceFragment = deviceFragment;
        }

        public void refreshDevice() {
            deviceFragment.refreshDevice();
        }

        public TextView getControlText() {
            return controlText;
        }
    }
}
