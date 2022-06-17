package com.hci.homerunapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class SecondaryFragment extends Fragment {
    protected String label;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null) {
            mainActivity.hideBottomNav();
            mainActivity.getUpButton().setVisibility(View.VISIBLE);
            mainActivity.getTitleText().setText(label);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
//        ((MainActivity) context).getTitle().setText();
        ((MainActivity) context).getUpButton().setVisibility(View.VISIBLE);
    }
}
