package com.hci.homerunapp.ui.device;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.hci.homerunapp.R;

public class TurnOnButtonData extends ControlData{
    private boolean state;

    TurnOnButtonData(boolean state) {
        super(R.layout.switch_on_item, "Estado: %s");
        this.state = state;
    }

    public boolean isOn() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String getActionLabel() {
        return String.format(super.getActionLabel(), state ? "encendido" : "apagado");
    }

    public static class ViewHolder extends CustomAdapter.ViewHolder {
        private final FloatingActionButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.device_button);
        }

        public FloatingActionButton getButton() {
            return button;
        }

    }
}
