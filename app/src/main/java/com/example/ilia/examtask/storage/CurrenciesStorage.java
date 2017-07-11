package com.example.ilia.examtask.storage;

import android.app.Application;

import com.example.ilia.examtask.model.CurrenciesList;


public class CurrenciesStorage extends Application {

    private CurrenciesList loadedList;

    public synchronized boolean isReady() {
        return loadedList != null;
    }

    public synchronized CurrenciesList getLoadedList() {
        return loadedList;
    }

    public synchronized void setLoadedList(CurrenciesList loadedList) {
        this.loadedList = loadedList;
    }
}
