package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceOven extends Device{
    private TurnOnButtonData turnOnButton;
    private SliderData temperatureSlider;
    private DropDownData changeHeatSourceDropDown;
    private DropDownData setGrillModeDropDown;
    private DropDownData setConvectionModeDropDown;
    public DeviceOven(DeviceData deviceData) {
        super(deviceData);

        turnOnButton = new TurnOnButtonData(false);

        temperatureSlider = new SliderData("Temperatura: %s°C",90, 230);

        String[] sources = new String[]{"Superior", "Inferior", "Convencional"};
        changeHeatSourceDropDown = new DropDownData("Cambiar fuente de calor", sources, "Seleccione una fuente");

        String[] grillModes = new String[]{"Normal", "Ecológico", "Apagado"};
        setGrillModeDropDown = new DropDownData("Establecer modo grill", grillModes, "Seleccione un modo");

        String[] convectionModes = new String[]{"Normal", "Ecológico", "Apagado"};
        setConvectionModeDropDown = new DropDownData("Establecer modo de convección", convectionModes, "Seleccione un modo");

    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(turnOnButton, temperatureSlider, changeHeatSourceDropDown, setGrillModeDropDown, setConvectionModeDropDown);
    }
}
