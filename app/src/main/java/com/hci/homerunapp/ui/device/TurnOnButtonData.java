package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hci.homerunapp.MyApplication;
import com.hci.homerunapp.R;
import com.hci.homerunapp.data.remote.device.action.ActionBody;
import com.hci.homerunapp.ui.MainActivity;
import com.hci.homerunapp.ui.room.DeviceData;

public class TurnOnButtonData extends ControlData{
    private boolean state;
    private String onApiAction, offApiAction;
    private ProgressBarData progressBarData;

    public TurnOnButtonData(Context context, String onApiAction, String offApiAction, DeviceData deviceData, ProgressBarData progressBar) {
        super(context, R.layout.switch_on_item, context.getResources().getString(R.string.switch_on_state), deviceData);
        this.onApiAction = onApiAction;
        this.offApiAction = offApiAction;
        this.progressBarData = progressBar;
    }

    public String getOnApiAction() {
        return onApiAction;
    }


    public String getOffApiAction() {
        return offApiAction;
    }

    public boolean isOn() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String getActionLabel() {
        return String.format(super.getActionLabel(), state ? context.getResources().getString(R.string.turn_on) : context.getResources().getString(R.string.turn_off));
    }

    public static class ViewHolder extends ControlDataViewHolder<TurnOnButtonData> {
        private final FloatingActionButton button;

        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
            button = itemView.findViewById(R.id.device_button);
        }

        public FloatingActionButton getButton() {
            return button;
        }

        @Override
        public void bindTo(TurnOnButtonData controlData) {
            super.bindTo(controlData);
            FloatingActionButton turnOnbutton = getButton();
            if (!controlData.isOn()) {
                controlData.setState(false);
                if (turnOnbutton.getBackground() != null)
                    turnOnbutton.getBackground().setTint(Color.LTGRAY);

                turnOnbutton.setSupportImageTintList(ColorStateList.valueOf(Color.BLACK));
            }
            else {
                controlData.setState(true);
                if (turnOnbutton.getBackground() != null)
                    turnOnbutton.getBackground().setTint(ContextCompat.getColor(context, R.color.primary));
                turnOnbutton.setSupportImageTintList(ColorStateList.valueOf(Color.WHITE));
            }
            getControlText().setText(controlData.getActionLabel());

            turnOnbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (controlData.isOn()) {
                        ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceData(), controlData.getOffApiAction(), new ActionBody(), ViewHolder.this, true, controlData.progressBarData != null ? controlData.progressBarData.getProgress() : 0);
                    }
                    else {
                        ((MyApplication)((MainActivity)context).getApplication()).getDeviceRepository().putAction(controlData.getDeviceData(), controlData.getOnApiAction(), new ActionBody(), ViewHolder.this, true, controlData.progressBarData != null ? controlData.progressBarData.getProgress() : 0);
                    }
                }
            });

        }
    }
}
