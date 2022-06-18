package com.hci.homerunapp.ui.device.blinds;

import android.content.Context;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.device.ControlData;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.ProgressBarData;
import com.hci.homerunapp.ui.device.SliderData;
import com.hci.homerunapp.ui.device.ToggleButtonData;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.Arrays;
import java.util.List;

public class DeviceBlinds extends Device {
    private final ProgressBarData stateProgressBar;
    private final ToggleButtonData toggleStateButton;
    private final SliderData closePercentageSlider;

    public DeviceBlinds(DeviceData deviceData, Context context) {
        super(deviceData, context);

        stateProgressBar = new ProgressBarData(context, context.getResources().getString(R.string.blinds_state), R.color.primary, deviceData.getId());
        toggleStateButton = new ToggleButtonData(context, context.getResources().getStringArray(R.array.blinds_actions), context.getResources().getStringArray(R.array.blinds_states), deviceData.getId());
        closePercentageSlider = new SliderData(context, context.getResources().getString(R.string.close_limit), 0, 100, "setLevel",deviceData.getId());
    }

    @Override
    public List<ControlData> getControls() {
        return Arrays.asList(stateProgressBar, toggleStateButton, closePercentageSlider);
    }
}
