package com.example.ilia.examtask.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ilia.examtask.OnCurrenciesLoaded;
import com.example.ilia.examtask.model.CurrenciesList;

import java.io.IOException;
import java.lang.ref.WeakReference;



public class LoadCurrenciesFromCache extends AsyncTask<Void, Void, Void> {
    private CurrenciesList loadList;

    private WeakReference<OnCurrenciesLoaded> listener;
    private WeakReference<Context> context;

    public LoadCurrenciesFromCache(OnCurrenciesLoaded listener, Context context) {
        this.listener = new WeakReference<>(listener);
        this.context = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Context tryContext = context.get();

        if (tryContext != null) {
            try {
                loadList = CurrenciesList.readFromFile(tryContext);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        OnCurrenciesLoaded tryListener = listener.get();
        if (tryListener != null) {
            if ( loadList != null) {
                tryListener.OnCurrenciesLoadedSuccess(loadList);
            } else {
                tryListener.OnCurrenciesLoadedErrorWithCache();
            }
        }

    }
}
