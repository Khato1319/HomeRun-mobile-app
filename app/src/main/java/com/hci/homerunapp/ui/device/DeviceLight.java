package com.hci.homerunapp.ui.device;


import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceLight extends Device{
    private TurnOnButtonData turnOnButton;
    private ColorPickerData colorPicker;
    private SliderData brightnessSlider;

    public DeviceLight(DeviceData deviceData) {
        super(deviceData);

        turnOnButton = new TurnOnButtonData(false);
        colorPicker = new ColorPickerData("Seleccionar color", 0,0,0);
        brightnessSlider = new SliderData("Cambiar brillo: %s",  0, 100);


    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, colorPicker, brightnessSlider);
    }
}
