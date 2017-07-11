package com.example.ilia.examtask.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ilia.examtask.OnCurrenciesLoaded;
import com.example.ilia.examtask.model.CurrenciesList;
import com.example.ilia.examtask.model.Currency;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;


public class LoadCurrenciesFromCache extends AsyncTask<Void, Void, Void> {

    private OnCurrenciesLoaded listener;
    Context context;
    private CurrenciesList loadList;

    public LoadCurrenciesFromCache(OnCurrenciesLoaded listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            loadList = CurrenciesList.readFromFile(context);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e(TAG, "onPostExecute: " + loadList );
        if ( loadList != null) {
            listener.OnCurrenciesLoadedSuccess(loadList);
        } else {
            listener.OnCurrenciesLoadedErrorWithCache();
        }
    }
}
