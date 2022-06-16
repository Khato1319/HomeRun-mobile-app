package com.hci.homerunapp.ui.device;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.room.DeviceData;

import java.util.ArrayList;
import java.util.List;

public class DeviceViewModel extends ViewModel {

    private DeviceData deviceData;
    private List<ControlData> controls;

    public DeviceViewModel() {
        super();
        controls = new ArrayList<>();
        controls.add(new SliderData("Temperatura", 0, 0, 40));
        controls.add(new ColorPickerData("Color", 0, 0, 0));
        controls.add(new DropDownData("Opcioness", new String[]{"Hola", "Como estas"}, "Opciones"));
        controls.add(new TurnOnButtonData(false));
        controls.add(new ToggleButtonData(false, new String[]{"Cerrar","Abrir"}, new String[]{"Abierto", "Cerrado"}));
    }

    public void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public List<ControlData> getControls() {
        return controls;
    }

    // TODO: Implement the ViewModel
}