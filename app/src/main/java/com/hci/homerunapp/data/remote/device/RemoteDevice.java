package com.hci.homerunapp.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hci.homerunapp.data.remote.room.RemoteRoomMeta;

public class RemoteDevice {
    @SerializedName("state")
    @Expose
    private RemoteDeviceState state;

    @SerializedName("room")
    @Expose
    private RemoteDeviceRoom room;

    @SerializedName("meta")
    @Expose
    private RemoteDeviceMeta meta;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private RemoteDeviceType type;


    public RemoteDeviceState getState() {
        return state;
    }

    public void setState(RemoteDeviceState state) {
        this.state = state;
    }

    public RemoteDeviceRoom getRoom() {
        return room;
    }

    public void setRoom(RemoteDeviceRoom room) {
        this.room = room;
    }

    public RemoteDeviceMeta getMeta() {
        return meta;
    }

    public void setMeta(RemoteDeviceMeta meta) {
        this.meta = meta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RemoteDeviceType getType() {
        return type;
    }

    public void setType(RemoteDeviceType type) {
        this.type = type;
    }
}
