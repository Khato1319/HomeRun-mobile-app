package com.hci.homerunapp.ui.device.light;


import android.content.Context;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.SliderData;
import com.hci.homerunapp.ui.device.TurnOnButtonData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceLight extends Device {
    private TurnOnButtonData turnOnButton;
    private ColorPickerData colorPicker;
    private SliderData brightnessSlider;

    public DeviceLight(DeviceData deviceData, Context context) {
        super(deviceData, context);

        turnOnButton = new TurnOnButtonData(context, "turnOn", "turnOff", deviceData, null);
        colorPicker = new ColorPickerData(context, deviceData);
        brightnessSlider = new SliderData(context, context.getResources().getString(R.string.brightness),  0, 100, "setBrightness", deviceData);

    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, colorPicker, brightnessSlider);
    }

    public TurnOnButtonData getTurnOnButton() {
        return turnOnButton;
    }

    public ColorPickerData getColorPicker() {
        return colorPicker;
    }

    public SliderData getBrightnessSlider() {
        return brightnessSlider;
    }
}
