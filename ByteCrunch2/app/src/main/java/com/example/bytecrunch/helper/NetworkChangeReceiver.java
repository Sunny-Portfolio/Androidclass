package com.example.bytecrunch.helper;

import static android.net.NetworkCapabilities.TRANSPORT_CELLULAR;
import static android.net.NetworkCapabilities.TRANSPORT_ETHERNET;
import static android.net.NetworkCapabilities.TRANSPORT_WIFI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Capability;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkChangeListener networkChangeListener;


    /**
     * Constructor
     * @param listener
     */
    public NetworkChangeReceiver(NetworkChangeListener listener) {
        this.networkChangeListener = listener;
    }

    private static final String TAG = "NetworkChangeReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: in broadcast receiver onReceive 1");

        try {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                Log.d(TAG, "onReceive: inside if");
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                Network activeNetwork = cm.getActiveNetwork();
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);

                boolean hasWifi = capabilities.hasTransport(TRANSPORT_WIFI);
                boolean hasCell = capabilities.hasTransport(TRANSPORT_CELLULAR);
                boolean hasEth = capabilities.hasTransport(TRANSPORT_ETHERNET);

                boolean hasInternet = hasWifi || hasCell || hasEth;


//                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//                boolean hasActiveNetwork = activeNetwork != null;
//                Log.d(TAG, "onReceive: in broadcast receiver onReceive 2 : network : " + hasActiveNetwork  );
//
//                boolean isConnecting;
//                boolean hasInternet = hasActiveNetwork && activeNetwork.isConnectedOrConnecting();

//                Log.d(TAG, "onReceive: in broadcast receiver onReceive 3 : network : " + hasActiveNetwork + " has internet  "+ hasInternet );

                if (hasInternet && networkChangeListener != null) {
                    Log.d(TAG, "onReceive: going to onNetworkAvailable");
                    networkChangeListener.onNetworkAvailable();
                }
            }


        } catch (Exception e) {
            Log.e(TAG, "onReceive: " + e );
        }
                


    }


    public interface NetworkChangeListener {
        void onNetworkAvailable();
    }
}
