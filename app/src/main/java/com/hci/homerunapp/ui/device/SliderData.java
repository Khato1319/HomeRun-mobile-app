package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

public class SliderData extends ControlData{
    private int minValue, maxValue, value;
    private String apiAction;

    public SliderData(Context context, String actionLabel, int minValue, int maxValue, String apiAction, String deviceId) {
        super(context, R.layout.slider_item, actionLabel, deviceId);
        this.minValue = this.value = minValue;
        this.maxValue = maxValue;
        this.apiAction = apiAction;
    }

    public String getApiAction() {
        return apiAction;
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
//
//    public void setupViewHolder(ControlDataAdapter.ViewHolder holder) {
//        super.setupViewHolder(holder);
//        SliderData.ViewHolder sliderViewHolder = (SliderData.ViewHolder) holder;
//        TextView controlText = holder.getControlText();
//        Slider slider = sliderViewHolder.getSlider();
//        slider.setValueFrom(getMinValue());
//        slider.setValueTo(getMaxValue());
//        slider.setValue(getValue());
//        slider.addOnChangeListener(new Slider.OnChangeListener() {
//            @Override
//            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
//                setValue((int) value);
//                controlText.setText(getActionLabel());
//            }
//        });
//    }

    public static class ViewHolder extends ControlDataViewHolder<SliderData> {
        private final Slider slider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slider = itemView.findViewById(R.id.slider);
        }

        @Override
        public void bindTo(SliderData controlData) {
            super.bindTo(controlData);
//            SliderData.ViewHolder sliderViewHolder = (SliderData.ViewHolder) holder;
            TextView controlText = getControlText();
            Slider slider = getSlider();
            slider.setValueFrom(controlData.getMinValue());
            slider.setValueTo(controlData.getMaxValue());
            slider.setValue(controlData.getValue());
            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    controlData.setValue((int) value);
                    controlText.setText(controlData.getActionLabel());
                }
            });
        }

        public Slider getSlider() {
            return slider;
        }

    }
}
