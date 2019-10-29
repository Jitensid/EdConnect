package com.oop.edconnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Internet {

    private Context context;

    public Internet(Context context) {
        this.context = context;
    }

    public boolean check_Connection(){

        boolean res;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();

        res = activeInfo != null && activeInfo.isConnectedOrConnecting();

        return  res;
    }

}