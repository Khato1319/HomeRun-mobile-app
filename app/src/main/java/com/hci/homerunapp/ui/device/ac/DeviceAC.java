package com.hci.homerunapp.ui.device.ac;

import android.content.Context;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.DropDownData;
import com.hci.homerunapp.ui.device.SliderData;
import com.hci.homerunapp.ui.device.TurnOnButtonData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceAC extends Device {
    public final TurnOnButtonData turnOnButton;
    public final SliderData temperatureSlider;
    public final DropDownData coolingModeDropDown;
    public final DropDownData verticalSwingDropDown;
    public final DropDownData horizontalSwingDropDown;
    public final DropDownData speedDropDown;

    public DeviceAC(DeviceData deviceData, Context context) {
        super(deviceData, context);
        turnOnButton = new TurnOnButtonData(context, "turnOn", "turnOff", deviceData.getId());

        temperatureSlider = new SliderData(context, context.getResources().getString(R.string.temperature_slider), 18, 38,"setTemperature",deviceData.getId());

        String[] coolingModes = new String[]{"cool", "heat", "fan"};
        coolingModeDropDown = new DropDownData(context, context.getResources().getString(R.string.cooling_mode_title), "setMode",context.getResources().getStringArray(R.array.cooling_modes) ,coolingModes, getDeviceData().getId());

        String[] verticalSwingModes = new String[]{"auto", "22", "45", "67", "90"};

        verticalSwingDropDown = new DropDownData(context, context.getResources().getString(R.string.vertical_swing_mode_title),"setVerticalSwing",context.getResources().getStringArray(R.array.vertical_swing_modes),verticalSwingModes, deviceData.getId());
        String[] horizontalSwingModes = new String[]{"auto", "-90", "-45", "0", "45", "90"};

        horizontalSwingDropDown = new DropDownData(context, context.getResources().getString(R.string.horizontal_swing_mode_title), "setHorizontalSwing",context.getResources().getStringArray(R.array.horizontal_swing_modes),horizontalSwingModes, deviceData.getId());

        String[] speedModes = new String[]{"auto", "25", "50", "75", "100"};
        speedDropDown = new DropDownData(context, context.getResources().getString(R.string.set_fan_speed),"setFanSpeed" ,context.getResources().getStringArray(R.array.speed_modes),speedModes, deviceData.getId());
    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, temperatureSlider, coolingModeDropDown, verticalSwingDropDown, horizontalSwingDropDown, speedDropDown);
    }

    public TurnOnButtonData getTurnOnButton() {
        return turnOnButton;
    }

    public SliderData getTemperatureSlider() {
        return temperatureSlider;
    }

    public DropDownData getCoolingModeDropDown() {
        return coolingModeDropDown;
    }

    public DropDownData getVerticalSwingDropDown() {
        return verticalSwingDropDown;
    }

    public DropDownData getHorizontalSwingDropDown() {
        return horizontalSwingDropDown;
    }

    public DropDownData getSpeedDropDown() {
        return speedDropDown;
    }
}
