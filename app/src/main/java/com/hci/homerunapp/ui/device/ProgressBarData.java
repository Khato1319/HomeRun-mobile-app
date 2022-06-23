package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.room.DeviceData;

public class ProgressBarData extends ControlData{
    private int progress;
    private final int color;

    public ProgressBarData(Context context, String actionLabel, int color, DeviceData deviceData) {
        super(context, R.layout.progress_bar_item, actionLabel, deviceData);
        this.color = color;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public String getActionLabel() {
        return String.format(super.getActionLabel(),progress);
    }

    public static class ViewHolder extends ControlDataViewHolder<ProgressBarData> {
        private final LinearProgressIndicator progressBar;
        private final TextView subLabel;

        public ViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
            progressBar = itemView.findViewById(R.id.progressBar);
            subLabel = itemView.findViewById(R.id.text_sublabel);
        }

        @Override
        public void bindTo(ProgressBarData controlData) {
            progressBar.setProgress(controlData.getProgress());
            super.bindTo(controlData);
            subLabel.setText(context.getString(R.string.charge_msg));
            subLabel.setVisibility(View.INVISIBLE);
            progressBar.setIndicatorColor(context.getResources().getColor(controlData.color));

            if (controlData.getActionLabel().contains("Bat")) {
                if (controlData.getProgress() <= 5){
                    progressBar.setIndicatorColor(Color.RED);
                    subLabel.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setIndicatorColor(context.getResources().getColor(controlData.color));
                }
            }

        }

        public LinearProgressIndicator getProgressBar() {
            return progressBar;
        }



    }
}
