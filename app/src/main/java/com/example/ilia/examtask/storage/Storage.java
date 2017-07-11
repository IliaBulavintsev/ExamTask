package com.example.ilia.examtask.storage;

import android.app.Application;

public class Storage extends Application {

    private CurrenciesStorage storage = new CurrenciesStorage();

    public CurrenciesStorage getStorage() {
        return storage;
    }

}