package com.hci.homerunapp.data.remote.device.action;

import java.util.ArrayList;

public class ActionBody<T> extends ArrayList<T> {
    ActionBody(T param) {
        super();
        add(param);
    }

    public ActionBody() {
        super();
    }
}
