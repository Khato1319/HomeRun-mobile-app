package com.hci.homerunapp.ui.device;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hci.homerunapp.R;

public class ToggleButtonData extends ControlData{
    private boolean state;
    private String[] btnLabels;
    private String[] actionLabels;

    ToggleButtonData(boolean state, String[] btnLabels, String[] actionLabels) {
        super(R.layout.toggle_button_item, "Estado: %s");
        this.state = state;
        this.btnLabels = btnLabels;
        this.actionLabels = actionLabels;
    }

    public String getButtonText() {
        return btnLabels[state ? 0 : 1];
    }

    private String getLabelText() {
        return actionLabels[state ? 0 : 1];
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public String getActionLabel() {
        return String.format(super.getActionLabel(), getLabelText());
    }

    public static class ViewHolder extends CustomAdapter.ViewHolder {
        private final MaterialButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.device_button);
        }

        public MaterialButton getButton() {
            return button;
        }

    }
}
