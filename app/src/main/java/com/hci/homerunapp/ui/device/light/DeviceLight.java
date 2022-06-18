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

        turnOnButton = new TurnOnButtonData(context, "on", "off", deviceData.getId());

        colorPicker = new ColorPickerData(context, deviceData.getId());
        brightnessSlider = new SliderData(context, context.getResources().getString(R.string.brightness),  0, 100, "setBrightness", deviceData.getId());


    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, colorPicker, brightnessSlider);
    }
}
