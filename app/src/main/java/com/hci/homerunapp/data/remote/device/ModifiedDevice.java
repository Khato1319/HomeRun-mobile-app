package com.hci.homerunapp.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifiedDevice {
    @SerializedName("meta")
    @Expose
    private RemoteDeviceMeta meta;

    @SerializedName("name")
    @Expose
    private String name;


    public RemoteDeviceMeta getMeta() {
        return meta;
    }

    public void setMeta(RemoteDeviceMeta meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
