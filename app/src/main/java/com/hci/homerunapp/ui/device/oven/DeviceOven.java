package com.hci.homerunapp.ui.device.oven;

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

public class DeviceOven extends Device {
    private TurnOnButtonData turnOnButton;
    private SliderData temperatureSlider;
    private DropDownData changeHeatSourceDropDown;
    private DropDownData setGrillModeDropDown;
    private DropDownData setConvectionModeDropDown;
    public DeviceOven(DeviceData deviceData, Context context) {
        super(deviceData, context);

        turnOnButton = new TurnOnButtonData(context, "on", "off", deviceData.getId());

        temperatureSlider = new SliderData(context, context.getResources().getString(R.string.temperature_slider),90, 230, "setTemperature", deviceData.getId());
        String[] heatSources = new String[]{"top", "bottom", "conventional"};
        changeHeatSourceDropDown = new DropDownData(context, context.getResources().getString(R.string.heat_source_title), "setHeatSource",context.getResources().getStringArray(R.array.heat_sources), heatSources, deviceData.getId());

        String[] modes = new String[]{"normal", "eco", "off"};
        setGrillModeDropDown = new DropDownData(context, context.getResources().getString(R.string.grill_mode_title),"setGrillMode",context.getResources().getStringArray(R.array.grill_modes), modes, deviceData.getId());

//        String[] convectionModes = new String[]{"Normal", "Ecológico", "Apagado"};
        setConvectionModeDropDown = new DropDownData(context, context.getResources().getString(R.string.convection_mode_title), "setConvectionMode", modes, context.getResources().getStringArray(R.array.convection_modes), deviceData.getId());

    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, temperatureSlider, changeHeatSourceDropDown, setGrillModeDropDown, setConvectionModeDropDown);
    }
}
