package com.hci.homerunapp.ui.device;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.room.DeviceData;

import java.util.List;

public abstract class Device {
    private DeviceData deviceData;
    private NotificationState notificationState = NotificationState.OFF;

    public abstract List<ControlData> getControls();

    public Device(DeviceData deviceData) {
        this.deviceData = deviceData;
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
