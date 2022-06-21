package com.hci.homerunapp.ui.device.vacuum;

import android.content.Context;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.DockButtonData;
import com.hci.homerunapp.ui.device.DropDownData;
import com.hci.homerunapp.ui.device.ProgressBarData;
import com.hci.homerunapp.ui.device.TurnOnButtonData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceVacuum extends Device {
    private ProgressBarData batteryProgressBar;
    private DockButtonData dockButton;
    private TurnOnButtonData turnOnButton;
    // boton volver a estacion de carga
    private ChangeLocationDropDownData changeLocationDropDown;
    private DropDownData setModeDropDownData;

    public DockButtonData getDockButton() {
        return dockButton;
    }

    public DeviceVacuum(DeviceData deviceData, Context context) {
        super(deviceData, context);
        dockButton = new DockButtonData(context, deviceData.getId());
        batteryProgressBar = new ProgressBarData(context, context.getResources().getString(R.string.vacuum_battery), R.color.primary, deviceData.getId());
        turnOnButton = new TurnOnButtonData( context, "start", "pause", deviceData.getId());;

        changeLocationDropDown = new ChangeLocationDropDownData(context, deviceData.getId());

        String[] modes = new String[]{"vacuum", "mop"};
        setModeDropDownData = new DropDownData(context, context.getResources().getString(R.string.set_mode), "setMode",context.getResources().getStringArray(R.array.vacuum_modes),modes, deviceData.getId());

    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(batteryProgressBar, dockButton, turnOnButton, changeLocationDropDown, setModeDropDownData);
    }

    public ProgressBarData getBatteryProgressBar() {
        return batteryProgressBar;
    }

    public TurnOnButtonData getTurnOnButton() {
        return turnOnButton;
    }

    public ChangeLocationDropDownData getChangeLocationDropDown() {
        return changeLocationDropDown;
    }

    public DropDownData getSetModeDropDownData() {
        return setModeDropDownData;
    }
}
