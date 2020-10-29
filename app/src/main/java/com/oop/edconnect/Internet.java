package com.oop.edconnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Internet {

    public Internet() {
    }

    public static boolean check_Connection(Context context){

        boolean res;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();

        res = activeInfo != null && activeInfo.isConnectedOrConnecting();

        return  res;
    }

}