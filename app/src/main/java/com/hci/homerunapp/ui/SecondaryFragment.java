package com.hci.homerunapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hci.homerunapp.ui.MainActivity;

public abstract class SecondaryFragment extends Fragment {
    protected String label;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null) {
            executeActions(mainActivity);
        }
    }

    protected void executeActions(MainActivity mainActivity) {
        Log.d("EXECUTING", "EXECUTING");
        mainActivity.hideBottomNav();
        mainActivity.getUpButton().setVisibility(View.VISIBLE);
        mainActivity.getTitleText().setText(label);
        mainActivity.getLogo().setVisibility(View.INVISIBLE);
        ImageButton notificationsButton = mainActivity.getNotificationsButton();
        notificationsButton.setVisibility(View.INVISIBLE);
    }
}
