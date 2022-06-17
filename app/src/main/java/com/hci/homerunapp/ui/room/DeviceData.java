package com.hci.homerunapp.ui.room;

import android.bluetooth.BluetoothClass;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.DeviceAC;
import com.hci.homerunapp.ui.device.DeviceBlinds;
import com.hci.homerunapp.ui.device.DeviceLight;
import com.hci.homerunapp.ui.device.DeviceVacuum;
import com.hci.homerunapp.ui.home.RoomData;

import java.io.Serializable;

public class DeviceData implements Data {
    private String name;
    private RoomData roomData;
    private DeviceData.Type type;
    private String id;

    public DeviceData(String name, String id, RoomData roomData, DeviceData.Type type) {
        this.name = name;
        this.id = id;
        this.roomData = roomData;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public RoomData getRoomData() {
        return roomData;
    }

    public Device getDeviceInstance() {
        return type.getDeviceInstance(this);
    }

    public DeviceData.Type getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public enum Type {
        VACUUM(R.drawable.ic_vacuum_cleaner_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data) {
                return new DeviceVacuum(data);
            }
        },
        LIGHT(R.drawable.ic_light_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data) {
                return new DeviceLight(data);
            }
        },
        BLINDS(R.drawable.ic_blinds_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data) {
                return new DeviceBlinds(data);
            }
        },
        OVEN(R.drawable.ic_oven_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data) {
                return new DeviceVacuum(data);
            }
        },
        AC(R.drawable.ic_ac_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data) {
                return new DeviceAC(data);
            }
        };


        Type(int icon) {
            this.icon = icon;
        }

        private int icon;

        public abstract Device getDeviceInstance(DeviceData data);

        public int getIcon() {
            return icon;
        }


    }
}
