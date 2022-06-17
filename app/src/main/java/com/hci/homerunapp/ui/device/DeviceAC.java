package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceAC extends Device{
    private final TurnOnButtonData turnOnButton;
    private final SliderData temperatureSlider;
    private final DropDownData coolingModeDropDown;
    private final DropDownData verticalSwingDropDown;
    private final DropDownData horizontalSwingDropDown;
    private final DropDownData speedDropDown;

    public DeviceAC(DeviceData deviceData) {
        super(deviceData);
        turnOnButton = new TurnOnButtonData(false);

        temperatureSlider = new SliderData("Temperatura: %d°C", 18, 38);

        String[] coolingModes = new String[]{"Frío", "Calor", "Ventilador"};
        coolingModeDropDown = new DropDownData("Cambiar modo de aire", coolingModes, "Seleccione un modo");

        String[] verticalSwingModes = new String[]{"Automático", "-90 grados", "-45 grados", "0 grados", "45 grados", "90 grados"};
        verticalSwingDropDown = new DropDownData("Cambiar swing vertical", verticalSwingModes, "Seleccione un modo");

        String[] horizontalSwingModes = new String[]{"Automático", "22 grados", "45 grados", "67 grados", "90 grados"};
        horizontalSwingDropDown = new DropDownData("Cambiar swing horizontal", horizontalSwingModes, "Seleccione un modo");

        String[] speedModes = new String[]{"Automático", "25%", "50%", "75%", "100%"};
        speedDropDown = new DropDownData("Cambiar velocidad de aire", speedModes, "Seleccione un modo");
    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, temperatureSlider, coolingModeDropDown, verticalSwingDropDown, horizontalSwingDropDown, speedDropDown);
    }
}
