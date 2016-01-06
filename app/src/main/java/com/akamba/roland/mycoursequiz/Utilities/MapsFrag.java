package com.akamba.roland.mycoursequiz.Utilities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.akamba.roland.mycoursequiz.R;
import com.akamba.roland.mycoursequiz.beans.Joueur;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFrag extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Joueur joueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_frag);
        Intent mapIntent=getIntent();
        joueur=(Joueur) mapIntent.getExtras().getSerializable("loginUser");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //setUpMap();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng myPos=new LatLng(joueur.getLatitude(),joueur.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
        mMap.addMarker(new MarkerOptions().title(joueur.getCity()).snippet(joueur.getAddress()).position(myPos));
    }
    private void setUpMap() {
        LatLng myPos=new LatLng(49.383430,1.0773341);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos,15));
        mMap.addMarker(new MarkerOptions().title("ESIGELEC").snippet("Esole ingenieur").position(myPos));
    }
}
