package com.hci.homerunapp.ui.home;

import androidx.lifecycle.ViewModel;

import com.hci.homerunapp.ui.Data;

import java.util.ArrayList;
import java.util.List;

public class SimpleButtonViewModel extends ViewModel {

    protected List<Data> elements = new ArrayList<>();

//    public SimpleButtonViewModel() {
//
//    }

    public List<Data> getElements() {
        return elements;
    }
}