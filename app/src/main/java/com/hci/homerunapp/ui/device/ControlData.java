package com.hci.homerunapp.ui.device;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hci.homerunapp.R;

import java.io.Serializable;

public class ControlData implements Serializable {
    private int layoutId;
    private String actionLabel, deviceId;
    protected Context context;

    public ControlData(Context context, int layoutId, String actionLabel, String deviceId) {
        this.layoutId = layoutId;
        this.actionLabel = actionLabel;
        this.context = context;
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public String getActionLabel() {
        return actionLabel;
    }

//    public void setupViewHolder(CustomAdapter.ViewHolder holder) {
//    }

    public static class ControlDataViewHolder<T extends ControlData> extends ControlDataAdapter.ViewHolder {
        private final TextView controlText;
        protected final Context context;

        public ControlDataViewHolder(@NonNull View itemView, DeviceFragment deviceFragment) {
            super(itemView, deviceFragment);
            controlText = itemView.findViewById(R.id.text_control);
            context = itemView.getContext();
        }

        public void bindTo(T controlData) {
            getControlText().setText(controlData.getActionLabel());
        }

        public TextView getControlText() {
            return controlText;
        }

    }
}
