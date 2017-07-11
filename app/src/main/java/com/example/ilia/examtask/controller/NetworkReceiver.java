package com.example.ilia.examtask.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class NetworkReceiver extends BroadcastReceiver {

    private static OnNetworkChanges listener;

    public interface OnNetworkChanges {
        void OnNetworkChanges();
    }

    public static void setListener(OnNetworkChanges listener) {
        NetworkReceiver.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() || mobile.isAvailable()) {
            Log.e(TAG, "onReceive: network find");
            if(listener!=null) {
                listener.OnNetworkChanges();
            }
        }


    }
}
