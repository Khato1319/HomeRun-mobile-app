package com.hci.homerunapp.ui.device.vacuum;

import android.content.Context;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.DropDownData;
import com.hci.homerunapp.ui.device.ProgressBarData;
import com.hci.homerunapp.ui.device.TurnOnButtonData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceVacuum extends Device {
    private ProgressBarData batteryProgressBar;
    private TurnOnButtonData turnOnButton;
    // boton volver a estacion de carga
    private ChangeLocationDropDownData changeLocationDropDown;
    private DropDownData setModeDropDownData;
    public DeviceVacuum(DeviceData deviceData, Context context) {
        super(deviceData, context);
        batteryProgressBar = new ProgressBarData(context, context.getResources().getString(R.string.vacuum_battery), R.color.primary, deviceData.getId());
        turnOnButton = new TurnOnButtonData(context, "start", "pause", deviceData.getId());;

        changeLocationDropDown = new ChangeLocationDropDownData(context, deviceData.getId());

        String[] modes = new String[]{"vacuum", "mop"};
        setModeDropDownData = new DropDownData(context, context.getResources().getString(R.string.vacuum_mode), "setMode",context.getResources().getStringArray(R.array.vacuum_modes),modes, deviceData.getId());

    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(batteryProgressBar, turnOnButton, changeLocationDropDown, setModeDropDownData);
    }
}
