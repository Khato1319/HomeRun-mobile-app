package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceBlinds extends Device{
    private final ProgressBarData stateProgressBar;
    private final ToggleButtonData toggleStateButton;
    private final SliderData closePercentageSlider;

    public DeviceBlinds(DeviceData deviceData) {
        super(deviceData);

        stateProgressBar = new ProgressBarData(40, "Estado: %s", R.color.primary);
        toggleStateButton = new ToggleButtonData(false, new String[]{"Cerrar","Abrir"}, new String[]{"Abierto", "Cerrado"});
        closePercentageSlider = new SliderData("Límite de cerrado: %s", 0, 100);
    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(stateProgressBar, toggleStateButton, closePercentageSlider);
    }
}
