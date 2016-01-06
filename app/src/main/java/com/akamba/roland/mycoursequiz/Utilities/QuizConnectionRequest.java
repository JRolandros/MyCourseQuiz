package com.akamba.roland.mycoursequiz.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Roland on 08/12/2015.
 */
public class QuizConnectionRequest {
    //region GPS variables
    LocationManager locationManager;
    public boolean isGPSEnabling=false;
    //endregion
    //region network connectivity
    //region network variables
    private static QuizConnectionRequest instance=null;
    private static Context ctx;
    ConnectivityManager connectivityManager;
    //wifi is available or not
    boolean wifiInfo;
    //mobile network is available or not
    boolean mobileInfo;
    //the device is connected or not
    boolean connected=false;
    //endregion

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

    //endregion

    /*/region manage GPS
    public boolean isGPSAvailable(){
        locationManager= (LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return false;
        }
        else
        return true;
    }

    public void alertUserGPSNotAvailable(final Context contx){
        final AlertDialog.Builder builder = new AlertDialog.Builder(contx);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        isGPSEnabling=true;
                        contx.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    //endregion*/
}
