package com.akamba.roland.mycoursequiz.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Roland on 08/12/2015.
 */
public class QuizConnectionRequest {
    private static QuizConnectionRequest instance=null;
    private static Context ctx;
    ConnectivityManager connectivityManager;
    //wifi is available or not
    boolean wifiInfo;
    //mobile network is available or not
    boolean mobileInfo;
    //the device is connected or not
    boolean connected=false;

    public static QuizConnectionRequest getInstance(Context context){
        ctx=context.getApplicationContext();
        if(instance==null)
            instance=new QuizConnectionRequest();
        return instance;
    }

    public boolean isOnline(){
        try {
            connectivityManager=(ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
            //check if thenetwork is of type of wifi
            wifiInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
            //check if it's mobile network that is available. if so demand permission to use it.
            mobileInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            //try this to check the network capability
            //connectivityManager.getNetworkCapabilities(ConnectivityManager.TYPE_MOBILE);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return wifiInfo||mobileInfo;
    }

    public String getCOnnectionType(){
        isOnline();
        if(wifiInfo)
            return "wifi";
        else
        if(mobileInfo)
        return "mobile";
        else
            return "No connection";
    }
}
