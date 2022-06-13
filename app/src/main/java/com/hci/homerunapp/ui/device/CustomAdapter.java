package com.hci.homerunapp.ui.device;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private final List<ControlData> controls;
    private final DeviceFragment deviceFragment;

    CustomAdapter(List<ControlData> controls, DeviceFragment deviceFragment) {
        this.controls = controls;
        this.deviceFragment = deviceFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);


        return switch(viewType) {
            case R.layout.slider_item -> new SliderData.ViewHolder(view);
            default ->
                throw new IllegalStateException("Unexpected value: " + viewType);
        };
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return controls.get(position).getLayoutId();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

//        holder.getDeviceButton().setText(dataSet.get(position));
        ControlData controlData = controls.get(position);
        holder.getControlText().setText(controlData.getName());

        switch(controlData.getLayoutId()) {
            case R.layout.slider_item:
                SliderData sliderData = (SliderData) controlData;
                SliderData.ViewHolder viewHolder = (SliderData.ViewHolder) holder;
                Slider slider = viewHolder.getSlider();
                slider.setValueFrom(sliderData.getMinValue());
                slider.setValueTo(sliderData.getMaxValue());
                slider.setValue(sliderData.getValue());
//                slider.setOnDragListener((sliderElem, event) -> {
//                    sliderData.setValue((int) slider.getValue());
//                    Log.d("HOLAAA", String.valueOf(slider.getValue()));
//                });
                slider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        sliderData.setValue((int) value);
                        Log.d("HOLAAA", String.valueOf(value));
                    }
                });
        }


//        deviceButton.setOnClickListener(deviceFragment.getButtonClickListener(deviceText.getText(),deviceRoom.getText(), deviceId.getText()));


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
