package com.hci.homerunapp.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceType {

    public String getId() {
        return switch(name) {
            case "ac" -> "li6cbv5sdlatti0j";
            default -> throw new IllegalStateException("Invalid device type name");
        };
    }

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
