package com.samples.webserver.webserversamples.log_in_system.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

/**
 * Created by ibrahim on 23/12/17.
 */
/**
 * Class uses Check of the Internet Connection
 */
@SuppressWarnings("ALL")
public class CheckInternetConnection {


    public boolean checkInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        boolean isConnected = Objects.requireNonNull(connectivityManager).getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        return isConnected;
    }
}
