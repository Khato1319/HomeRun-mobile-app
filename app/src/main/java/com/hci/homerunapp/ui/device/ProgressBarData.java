package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.hci.homerunapp.R;

public class ProgressBarData extends ControlData{
    private int progress;
    private final int color;

    public ProgressBarData(Context context, String actionLabel, int color, String deviceId) {
        super(context, R.layout.progress_bar_item, actionLabel, deviceId);
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

//    @Override
//    public void setupViewHolder(ControlDataAdapter.ViewHolder holder) {
//        super.setupViewHolder(holder);
//        ProgressBarData.ViewHolder progressBarViewHolder = (ProgressBarData.ViewHolder) holder;
//        LinearProgressIndicator progressBar = progressBarViewHolder.getProgressBar();
//        progressBar.setIndicatorColor(color);
//        progressBar.setProgress(getProgress());
//    }

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
//            ProgressBarData.ViewHolder progressBarViewHolder = (ProgressBarData.ViewHolder) holder;
            subLabel.setText(context.getString(R.string.charge_msg));
            subLabel.setVisibility(View.INVISIBLE);
            progressBar.setIndicatorColor(controlData.color);

            if (controlData.getActionLabel().contains("Bat")) {
                if (controlData.getProgress() <= 5){
                    progressBar.setIndicatorColor(Color.RED);
                    subLabel.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setIndicatorColor(controlData.color);

                }
            }

        }

        public LinearProgressIndicator getProgressBar() {
            return progressBar;
        }



    }
}
