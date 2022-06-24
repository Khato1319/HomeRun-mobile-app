package com.hci.homerunapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;

public interface ButtonListenerMaker {
    Activity getActivity();

    NavController getNavController();

    default View.OnClickListener getButtonClickListener(Data data, String dataName, int destination) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(dataName, data);

                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.hideBottomNav();

                getNavController().navigate(destination, bundle);
            }
        };
    }

    View.OnClickListener getButtonClickListener(Data data);
}
