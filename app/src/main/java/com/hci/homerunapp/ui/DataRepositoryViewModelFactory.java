package com.hci.homerunapp.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Constructor;

public class DataRepositoryViewModelFactory<R, D extends Data> implements ViewModelProvider.Factory  {
    private final Class<R> repositoryClass;
    private final Class<D> dataClass;
    private final R repository;
    private final D data;

    public DataRepositoryViewModelFactory(Class<R> repositoryClass, R repository, Class<D> dataClass, D data) {
        this.repositoryClass = repositoryClass;
        this.repository = repository;
        this.data = data;
        this.dataClass = dataClass;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!DataRepositoryViewModel.class.isAssignableFrom(modelClass))
            throw new IllegalArgumentException("Unknown class " + modelClass);
        try {
            Constructor<T> constructor = modelClass.getConstructor(repositoryClass, dataClass);
            return constructor.newInstance(repository, data);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an instance of class " + modelClass, e);
        }
    }
}
