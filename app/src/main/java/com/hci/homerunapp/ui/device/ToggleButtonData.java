package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.remote.device.action.ActionBody;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.room.DeviceData;

public class ToggleButtonData extends ControlData{
    private int state;
    private String[] btnLabels;
    private String[] actionLabels;

    public ToggleButtonData(Context context, String[] btnLabels, String[] actionLabels, DeviceData deviceData) {
        super(context, R.layout.toggle_button_item, context.getResources().getString(R.string.blinds_state), deviceData);
        this.btnLabels = btnLabels;
        this.actionLabels = actionLabels;
    }

    public String getButtonText() {
        return btnLabels[state == 0 || state == 3 ? 1 : 0];
    }

    private String getLabelText() {
        return actionLabels[state];
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    @Override
    public String getActionLabel() {
        return String.format(super.getActionLabel(), getLabelText());
    }

    public static class ViewHolder extends ControlDataViewHolder<ToggleButtonData> {
        private final MaterialButton button;

        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
            button = itemView.findViewById(R.id.device_button);
        }

        public MaterialButton getButton() {
            return button;
        }

        @Override
        public void bindTo(ToggleButtonData controlData) {
            super.bindTo(controlData);
//            ToggleButtonData.ViewHolder toggleButtonViewHolder = (ToggleButtonData.ViewHolder) holder;
            MaterialButton toggleButton = getButton();
            if (controlData.getState() > 1) {
                toggleButton.setEnabled(false);
                toggleButton.setBackgroundColor(Color.GRAY);
            }
            else {
                toggleButton.setEnabled(true);
                toggleButton.setBackgroundColor(context.getColor(R.color.primary));

            }
            getControlText().setText(controlData.getActionLabel());
            toggleButton.setText(controlData.getButtonText());
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    controlData.setState(!controlData.getState());
                    ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceData(), controlData.getState() == 0 ? "close" : "open", new ActionBody(), ViewHolder.this, true, 0);
//                    toggleButton.setText(controlData.getButtonText());
//                    getControlText().setText(controlData.getActionLabel());

                }
            });
        }

    }
}
