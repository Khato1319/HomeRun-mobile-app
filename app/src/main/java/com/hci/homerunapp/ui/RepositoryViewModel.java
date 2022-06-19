package com.hci.homerunapp.ui;

import androidx.lifecycle.ViewModel;

//Para ser usado en las vistas Rooms y Routines que llaman a la API
public class RepositoryViewModel<T> extends ViewModel {
    protected T repository;

    public RepositoryViewModel(T repository) {
        this.repository = repository;
    }
}
