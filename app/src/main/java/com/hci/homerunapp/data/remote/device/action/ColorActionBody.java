package com.hci.homerunapp.data.remote.device.action;

public class ColorActionBody extends StringActionBody{
    public ColorActionBody(int r, int g, int b) {
        super();
        String hex = String.format("%02x%02x%02x", r, g, b);
        add(hex);
    }
}
