package com.example.ilia.examtask.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ilia.examtask.OnCurrenciesLoaded;
import com.example.ilia.examtask.model.CurrenciesList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoadCurrencies extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "LoadCurrencies";
    private OnCurrenciesLoaded listener;
    private int code;
    private CurrenciesList loadList;
    private final String STRING_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    private Context context;

    public LoadCurrencies(OnCurrenciesLoaded listener, Context context){
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.e(TAG, "doInBackground: ");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            URL obj = new URL(STRING_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setInstanceFollowRedirects(true);
            HttpURLConnection.setFollowRedirects(true);
            code = con.getResponseCode();
            loadList = CurrenciesList.readFromStream(con.getInputStream());
            CurrenciesList.writeToFile(context, loadList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e(TAG, "onPostExecute: "+ code);
        if (code == 200 && loadList != null) {
            listener.OnCurrenciesLoadedSuccess(loadList);
        } else {
            listener.OnCurrenciesLoadedError();
        }
    }
}
