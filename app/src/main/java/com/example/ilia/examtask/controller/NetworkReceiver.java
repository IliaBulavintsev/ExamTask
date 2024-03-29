package com.example.ilia.examtask.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ilia.examtask.OnCurrenciesLoaded;

import java.lang.ref.WeakReference;


public class NetworkReceiver extends BroadcastReceiver {

    private static WeakReference<OnNetworkChanges> listener;

    public interface OnNetworkChanges {
        void OnNetworkChanges();
    }

    public static void setListener(OnNetworkChanges listener) {
        NetworkReceiver.listener = new WeakReference<>(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            boolean isConnected = activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                OnNetworkChanges tryListener = listener.get();
                if(tryListener!=null) {
                    tryListener.OnNetworkChanges();
                }
            }
        }
    }
}
