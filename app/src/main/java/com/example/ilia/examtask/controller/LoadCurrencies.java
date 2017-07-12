package com.example.ilia.examtask.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ilia.examtask.OnCurrenciesLoaded;
import com.example.ilia.examtask.model.CurrenciesList;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoadCurrencies extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "LoadCurrencies";
    private int code;
    private CurrenciesList loadList;
    private final String STRING_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    private WeakReference<OnCurrenciesLoaded> listener;
    private WeakReference<Context> context;

    public LoadCurrencies(OnCurrenciesLoaded listener, Context context){
        this.listener = new WeakReference<>(listener);
        this.context = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Context tryContext = context.get();

        try {
            URL obj = new URL(STRING_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setInstanceFollowRedirects(true);
            HttpURLConnection.setFollowRedirects(true);
            code = con.getResponseCode();
            loadList = CurrenciesList.readFromStream(con.getInputStream());
            if (tryContext != null) {
                CurrenciesList.writeToFile(tryContext, loadList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        OnCurrenciesLoaded tryListener = listener.get();
        if (tryListener != null){
            if (code == 200 && loadList != null) {
                tryListener.OnCurrenciesLoadedSuccess(loadList);
            } else {
                tryListener.OnCurrenciesLoadedError();
            }
        }

    }
}
