package com.hci.homerunapp.ui;

//Para ser usado en la pestaña de dispositivos que recibe por Data algo
public class DataRepositoryViewModel<T> extends RepositoryViewModel<T>{
    private Data data;

    public DataRepositoryViewModel(T repository, Data data){
        super(repository);
        this.data=data;
    }
}
