package com.hci.homerunapp.ui;

//Para ser usado en la pestaña de dispositivos que recibe por Data algo
public class DataRepositoryViewModel<T, D extends Data> extends RepositoryViewModel<T>{
    private D data;

    public DataRepositoryViewModel(T repository, D data){
        super(repository);
        this.data=data;
    }
}
