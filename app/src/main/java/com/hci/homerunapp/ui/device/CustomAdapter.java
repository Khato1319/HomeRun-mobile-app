package com.hci.homerunapp.ui.device;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
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
            case R.layout.color_picker_item -> new ColorPickerData.ViewHolder(view);
            case R.layout.drop_down_container_item -> new DropDownData.ViewHolder(view);
            case R.layout.switch_on_item -> new TurnOnButtonData.ViewHolder(view);
            case R.layout.toggle_button_item -> new ToggleButtonData.ViewHolder(view);
            case R.layout.progress_bar_item -> new ProgressBarData.ViewHolder(view);
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
        holder.getControlText().setText(controlData.getActionLabel());

        switch(controlData.getLayoutId()) {
            case R.layout.progress_bar_item:
                ProgressBarData progressBarData = (ProgressBarData) controlData;
                ProgressBarData.ViewHolder progressBarViewHolder = (ProgressBarData.ViewHolder) holder;
                LinearProgressIndicator progressIndicator = progressBarViewHolder.getProgressBar();
                progressIndicator.setProgress(progressBarData.getProgress());
                progressBarData.setupProgressBar(progressIndicator);
                break;
            case R.layout.toggle_button_item:
                ToggleButtonData toggleButtonData = (ToggleButtonData) controlData;
                ToggleButtonData.ViewHolder toggleButtonViewHolder = (ToggleButtonData.ViewHolder) holder;
                MaterialButton toggleButton = toggleButtonViewHolder.getButton();
                toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleButtonData.setState(!toggleButtonData.getState());
                        toggleButton.setText(toggleButtonData.getButtonText());
                        holder.getControlText().setText(controlData.getActionLabel());

                    }
                });
                break;
            case R.layout.switch_on_item:
                TurnOnButtonData turnOnButtonData = (TurnOnButtonData) controlData;
                TurnOnButtonData.ViewHolder turnOnButtonViewHolder = (TurnOnButtonData.ViewHolder) holder;
                FloatingActionButton turnOnbutton = turnOnButtonViewHolder.getButton();
                turnOnbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (turnOnButtonData.isOn()) {
                            turnOnButtonData.setState(false);
                            turnOnbutton.getBackground().setTint(Color.DKGRAY);

                            turnOnbutton.setSupportImageTintList(ColorStateList.valueOf(deviceFragment.getResources().getColor(R.color.primary)));

                        }
                        else {
                            turnOnButtonData.setState(true);
                            turnOnbutton.getBackground().setTint(deviceFragment.getResources().getColor(R.color.primary));
//                            button.setSupportBackgroundTintList(ColorStateList.valueOf(deviceFragment.getResources().getColor(R.color.primary)));
                            turnOnbutton.setSupportImageTintList(ColorStateList.valueOf(Color.WHITE));
                        }
                        holder.getControlText().setText(controlData.getActionLabel());

                    }
                });
                break;
            case R.layout.slider_item:
                SliderData sliderData = (SliderData) controlData;
                SliderData.ViewHolder sliderViewHolder = (SliderData.ViewHolder) holder;
                Slider slider = sliderViewHolder.getSlider();
                slider.setValueFrom(sliderData.getMinValue());
                slider.setValueTo(sliderData.getMaxValue());
                slider.setValue(sliderData.getValue());
                slider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        sliderData.setValue((int) value);
                    }
                });
                break;
            case R.layout.drop_down_container_item:
                DropDownData dropDownData = (DropDownData) controlData;
                DropDownData.ViewHolder dropDownViewHolder = (DropDownData.ViewHolder) holder;
                AutoCompleteTextView autoCompleteTextView = dropDownViewHolder.getAutoCompleteTextView();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(deviceFragment.getContext(), R.layout.drop_down_item, dropDownData.getItems());
                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    dropDownData.setSelected(arrayAdapter.getItem(position));
                }
            });
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.setHint(dropDownData.getHint());
                autoCompleteTextView.setText(dropDownData.getSelected(), false);
                break;
            case R.layout.color_picker_item:
                ColorPickerData colorPickerData = (ColorPickerData) controlData;
                ColorPickerData.ViewHolder colorPickerViewHolder = (ColorPickerData.ViewHolder) holder;
                CardView colorCard = colorPickerViewHolder.getColorCard();
                Slider redSlider = colorPickerViewHolder.getRedSlider();
                redSlider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        colorPickerData.setRed((int) value);
                        colorCard.setCardBackgroundColor(colorPickerData.getCardColor());
                    }
                });
                Slider greenSlider = colorPickerViewHolder.getGreenSlider();
                greenSlider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        colorPickerData.setGreen((int) value);
                        colorCard.setCardBackgroundColor(colorPickerData.getCardColor());
                    }
                });
                Slider blueSlider = colorPickerViewHolder.getBlueSlider();
                blueSlider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        colorPickerData.setBlue((int) value);
                        colorCard.setCardBackgroundColor(colorPickerData.getCardColor());

                    }
                });
                redSlider.setValue(colorPickerData.getRed());
                greenSlider.setValue(colorPickerData.getGreen());
                blueSlider.setValue(colorPickerData.getBlue());
                colorCard.setCardBackgroundColor(colorPickerData.getCardColor());
//                slider.setValue(sliderData.getValue());

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
