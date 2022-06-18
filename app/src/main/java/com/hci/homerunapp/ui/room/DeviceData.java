package com.hci.homerunapp.ui.room;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hci.homerunapp.R;
import com.hci.homerunapp.ui.Data;
import com.hci.homerunapp.ui.device.Device;
import com.hci.homerunapp.ui.device.ac.DeviceAC;
import com.hci.homerunapp.ui.device.blinds.DeviceBlinds;
import com.hci.homerunapp.ui.device.light.DeviceLight;
import com.hci.homerunapp.ui.device.oven.DeviceOven;
import com.hci.homerunapp.ui.device.vacuum.DeviceVacuum;
import com.hci.homerunapp.ui.home.RoomData;

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

    public Device getDeviceInstance(Context context) {
        return type.getDeviceInstance(this, context);
    }

    public DeviceData.Type getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Object other) {
        if (!(other instanceof DeviceData aux))
            return false;

        return aux.id.equals(id);
    }

    public enum Type {
        VACUUM(R.drawable.ic_vacuum_cleaner_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data, Context context) {
                return new DeviceVacuum(data, context);
            }
        },
        LIGHT(R.drawable.ic_light_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data, Context context) {
                return new DeviceLight(data, context);
            }
        },
        BLINDS(R.drawable.ic_blinds_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data, Context context) {
                return new DeviceBlinds(data, context);
            }
        },
        OVEN(R.drawable.ic_oven_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data, Context context) {
                return new DeviceOven(data, context);
            }
        },
        AC(R.drawable.ic_ac_icon) {
            @Override
            public Device getDeviceInstance(DeviceData data, Context context) {
                return new DeviceAC(data, context);
            }
        };


        Type(int icon) {
            this.icon = icon;
        }

        private int icon;

        public abstract Device getDeviceInstance(DeviceData data, Context context);

        public int getIcon() {
            return icon;
        }


    }
}
