package com.hci.homerunapp.ui.device;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.hci.homerunapp.R;

public class ProgressBarData extends ControlData{
    private int progress = 40;
    private final int color;

    ProgressBarData(int progress, String actionLabel, int color) {
        super(R.layout.progress_bar_item, actionLabel);
        this.progress = progress;
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

    @Override
    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
        super.setupViewHolder(holder);
        ProgressBarData.ViewHolder progressBarViewHolder = (ProgressBarData.ViewHolder) holder;
        LinearProgressIndicator progressBar = progressBarViewHolder.getProgressBar();
        progressBar.setIndicatorColor(color);
        progressBar.setProgress(getProgress());
    }

    public static class ViewHolder extends CustomAdapter.ViewHolder {
        private final LinearProgressIndicator progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public LinearProgressIndicator getProgressBar() {
            return progressBar;
        }



    }
}
