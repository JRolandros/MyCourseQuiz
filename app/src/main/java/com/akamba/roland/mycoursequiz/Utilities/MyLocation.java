package com.akamba.roland.mycoursequiz.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.akamba.roland.mycoursequiz.data.MyBDD;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Roland on 08/12/2015.
 */
public class MyLocation implements LocationListener {
    private static LocationManager locationManager;
    private static Location myLocation=null;
    private boolean isGPSEnabled=false;
    private boolean isNetWorkEnabled=false;
    private static Context ctx;

    public MyLocation(Context ctx){
        this.ctx=ctx;
        initLocationService(ctx);
    }
public static Location getMyLocation(Context ctx){
    if(myLocation==null)
        new MyLocation(ctx);
    return myLocation;
}
    private void initLocationService(Context ctx){
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        try {
            //get location manager
            locationManager=(LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);

            //check gps status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetWorkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            //update location if the gps is on
            if(isGPSEnabled)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            //get lastest known location
            if (locationManager != null)
                myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //check there is network
            if(isNetWorkEnabled){
                //check if my location is still null, then try with the network provider
                if(myLocation==null){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
                    if (locationManager != null)
                        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static List<Address> getLocationAddressName(Context ctx){
        if(myLocation==null)
            myLocation=getMyLocation(ctx);
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        List<Address> addresses=null;
        try {
            addresses = geocoder.getFromLocation(
                    myLocation.getLatitude(),
                    myLocation.getLongitude(),
                    // In this sample, get just a single address.
                    1);
            return addresses;
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e("ex","No GPS nor Network is working", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e("Exception", "invalid latitude or longitude" + ". " +
                    "Latitude = " + myLocation.getLatitude() +
                    ", Longitude = " +
                    myLocation.getLongitude(), illegalArgumentException);
        }
        return  null;
    }
    /**
     * Function to show settings alert dialog
     * */
    public static void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

        // Setting Dialog Title
        alertDialog.setTitle("GPS permission");

        // Setting Dialog Message
        alertDialog.setMessage("Neither your GPS nor your network is enabled.\nDo you want to go to settings menu and try again?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ctx.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
