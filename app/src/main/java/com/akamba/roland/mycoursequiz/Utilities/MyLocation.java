package com.akamba.roland.mycoursequiz.Utilities;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;

import com.akamba.roland.mycoursequiz.R;
import com.akamba.roland.mycoursequiz.data.MyBDD;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Roland on 08/12/2015.
 */
public class MyLocation {
    private static LocationManager locationManager;
    private static Location myLocation=null;
    private boolean isGPSEnabled=false;
    private boolean isNetWorkEnabled=false;
    private static Context ctx;
    private static Activity activity;

    public MyLocation(Activity activity){
        this.activity=activity;
        this.ctx=activity.getApplicationContext();
        initLocationService(ctx);
    }
    /*
public static Location getInstance(Context ctx){
    if(myLocation==null)
        new MyLocation(ctx);
    return myLocation;
}*/

    public boolean isGPSEnabled(){
        return isGPSEnabled;
    }
    private void initLocationService(Context ctx){
        //check for permission granted
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        try {
            //create location listener
            LocationListener locationListener = new MyLocationListener();
            //get location manager
            locationManager=(LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);

            //check gps status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetWorkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            //update location if the gps is on
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            //get lastest known location
            if (locationManager != null)
                myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //check there is network
            if(isNetWorkEnabled){
                //check if my location is still null, then try with the network provider
                if(myLocation==null){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
                    if (locationManager != null)
                        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<Address> getLocationAddressName(){
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
    public Location getMyLocation(){
        return myLocation;
    }
    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert(){
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


    public void alertUserGPSNotAvailable(final Context contx){
        final AlertDialog.Builder builder = new AlertDialog.Builder(contx);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
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


    private final class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location locFromGps) {
            // called when the listener is notified with a location update from the GPS
        }

        @Override
        public void onProviderDisabled(String provider) {
            // called when the GPS provider is turned off (user turning off the GPS on the phone)
        }

        @Override
        public void onProviderEnabled(String provider) {
            // called when the GPS provider is turned on (user turning on the GPS on the phone)
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if(provider.equals(LocationManager.GPS_PROVIDER)) {
                initLocationService(ctx);

                TextView connectionTv=(TextView)activity.findViewById(R.id.connectionInfo);
                connectionTv.setText("Location :" + getLocationAddressName().get(0).getAddressLine(1));
            }
        }
    }
}


