package com.hci.homerunapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hci.homerunapp.ui.MainActivity;

public abstract class PrimaryFragment extends Fragment{
    protected String label;

    protected void executeActions(MainActivity mainActivity) {
        mainActivity.showBottomNav();
        mainActivity.getUpButton().setVisibility(View.INVISIBLE);
        mainActivity.getTitleText().setText(label);
        mainActivity.getLogo().setVisibility(View.VISIBLE);
        ImageButton notificationsButton = mainActivity.getNotificationsButton();
        notificationsButton.setVisibility(View.INVISIBLE);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null) {
            executeActions(mainActivity);
        }

    }
}
