package com.example.ilia.examtask.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            boolean isConnected = activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                Log.e(TAG, "onReceive: network find");
                if(listener!=null) {
                    listener.OnNetworkChanges();
                }
            }
        }
    }
}
