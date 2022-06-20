package com.hci.homerunapp.ui.device;

import android.content.Context;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;

public abstract class Device implements Data {
    private final DeviceData deviceData;
    protected Context context;
    private NotificationState notificationState = NotificationState.OFF;


    public abstract List<ControlData> getControls();

    public Device(DeviceData deviceData, Context context) {
        this.deviceData = deviceData;
        this.context = context;

    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public void toggleNotificationState() {
        if (notificationState.equals(NotificationState.OFF))
            notificationState = NotificationState.ON;
        else
            notificationState = NotificationState.OFF;
    }

    public NotificationState getNotificationState() {
        return notificationState;
    }

    @Override
    public String getName() {
        return deviceData.getName();
    }

    @Override
    public String getId() {
        return deviceData.getId();
    }

    public enum NotificationState {
        ON(R.drawable.ic_notifications_black_24dp),
        OFF(R.drawable.ic_baseline_notifications_off_24);
        private int iconId;
        NotificationState(int iconId) {
            this.iconId = iconId;
        }

        public int getIconId() {
            return iconId;
        }
    }

}
