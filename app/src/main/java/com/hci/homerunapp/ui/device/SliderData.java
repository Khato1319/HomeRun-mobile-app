package com.hci.homerunapp.ui.device;

import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

public class SliderData extends ControlData{
    private int minValue, maxValue, value;

    SliderData(String actionLabel, int minValue, int maxValue) {
        super(R.layout.slider_item, actionLabel);
        this.minValue = minValue;
        this.maxValue = maxValue;

    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getActionLabel() {
        return String.format(super.getActionLabel(), value);
    }

    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
        super.setupViewHolder(holder);
        SliderData.ViewHolder sliderViewHolder = (SliderData.ViewHolder) holder;
        TextView controlText = holder.getControlText();
        Slider slider = sliderViewHolder.getSlider();
        slider.setValueFrom(getMinValue());
        slider.setValueTo(getMaxValue());
        slider.setValue(getValue());
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                setValue((int) value);
                controlText.setText(getActionLabel());
            }
        });
    }

    public static class ViewHolder extends CustomAdapter.ViewHolder {
        private final Slider slider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slider = itemView.findViewById(R.id.slider);
        }

        public Slider getSlider() {
            return slider;
        }

    }
}
