package com.hci.homerunapp.data.remote.device.action;

public class IntActionBody extends ActionBody<Integer> {
    public IntActionBody(Integer param) {
        super(param);
    }
    public IntActionBody(float param) {
        super((int)param);
    }
}
