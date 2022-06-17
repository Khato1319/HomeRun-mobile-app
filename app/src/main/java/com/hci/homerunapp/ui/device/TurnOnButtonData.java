package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    @Override
    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
        super.setupViewHolder(holder);
        TurnOnButtonData.ViewHolder turnOnButtonViewHolder = (TurnOnButtonData.ViewHolder) holder;
        FloatingActionButton turnOnbutton = turnOnButtonViewHolder.getButton();
        Context context = turnOnbutton.getContext();
        turnOnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOn()) {
                    setState(false);
                    turnOnbutton.getBackground().setTint(Color.DKGRAY);

                    turnOnbutton.setSupportImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary)));

                }
                else {
                    setState(true);
                    turnOnbutton.getBackground().setTint(ContextCompat.getColor(context, R.color.primary));
                    turnOnbutton.setSupportImageTintList(ColorStateList.valueOf(Color.WHITE));
                }
                holder.getControlText().setText(getActionLabel());

            }
        });
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
