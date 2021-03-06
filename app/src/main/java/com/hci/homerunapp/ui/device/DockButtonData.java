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

public class DockButtonData extends ControlData{
    private boolean docked;

    public DockButtonData(Context context, DeviceData deviceData) {
        super(context, R.layout.dock_item, context.getString(R.string.dock_state_docked), deviceData);
    }

    @Override
    public String getActionLabel() {
        return docked ? context.getString(R.string.dock_state_docked) : context.getString(R.string.dock_state_undocked);
    }

    public void setState(boolean docked) {
        this.docked = docked;
    }


    public static class ViewHolder extends ControlDataViewHolder<DockButtonData> {
        private final MaterialButton button;

        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
            button = itemView.findViewById(R.id.device_button);
        }

        public MaterialButton getButton() {
            return button;
        }

        @Override
        public void bindTo(DockButtonData controlData) {
            super.bindTo(controlData);
            MaterialButton toggleButton = getButton();
            if (controlData.docked) {
                toggleButton.setEnabled(false);
                toggleButton.setBackgroundColor(Color.GRAY);
            }
            else {
                toggleButton.setEnabled(true);
                toggleButton.setBackgroundColor(context.getColor(R.color.primary));
            }
            getControlText().setText(controlData.getActionLabel());
            toggleButton.setText(context.getString(R.string.dock_action));
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceData(), "dock", new ActionBody(), ViewHolder.this, true, 0);
                }
            });
        }

    }
}
