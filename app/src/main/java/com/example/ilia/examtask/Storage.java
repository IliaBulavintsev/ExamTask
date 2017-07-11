package com.example.ilia.examtask;

import android.app.Application;

import com.example.ilia.examtask.storage.CurrenciesStorage;

public class Storage extends Application {

    private CurrenciesStorage storage = new CurrenciesStorage();

    public CurrenciesStorage getStorage() {
        return this.storage;
    }

}