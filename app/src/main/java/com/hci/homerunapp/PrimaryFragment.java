package com.hci.homerunapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class PrimaryFragment extends Fragment{
    protected String label;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null) {
            mainActivity.showBottomNav();
            mainActivity.getUpButton().setVisibility(View.INVISIBLE);
            mainActivity.getTitleText().setText(label);
        }

    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        ((MainActivity) context).getUpButton().setVisibility(View.INVISIBLE);
//        super.onAttach(context);
////        ((MainActivity) context).getTitle().setText();
//
//    }

}
