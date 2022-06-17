package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceVacuum extends Device{
    private ProgressBarData batteryProgressBar;
    private TurnOnButtonData turnOnButton;
    // boton volver a estacion de carga
    private DropDownData changeLocationDropDown;
    private DropDownData setModeDropDownData;
    public DeviceVacuum(DeviceData deviceData) {
        super(deviceData);
        batteryProgressBar = new ProgressBarData(0, "Batería", R.color.primary);
        turnOnButton = new TurnOnButtonData(false);

        String[] locations = new String[]{"Cocina", "Banio"};
        changeLocationDropDown = new DropDownData("Cambiar ubicación", locations, "Seleccione una habitación");

        String[] modes = new String[]{"Aspirar", "Trapear"};
        setModeDropDownData = new DropDownData("Establecer modo", modes, "Seleccione un modo");

    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(batteryProgressBar, turnOnButton, changeLocationDropDown, setModeDropDownData);
    }
}
