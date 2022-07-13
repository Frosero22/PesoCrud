package com.appcrud.pesosaludablecrud.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

import static android.content.ContentValues.TAG;

public class Location implements LocationListener{

    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    private LocationManager locationManager;
    private android.location.Location loc;
    private double latitude;
    private double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;//1 Metro
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;//1 minuto

    public String getLatitud(){
        return String.valueOf(latitude);
    }

    public String getLongitud(){
        return String.valueOf(longitude);
    }

    public String getLoc(){
        return String.valueOf(latitude+" ; "+longitude);
    }

    public void getLocation(Context context) {
        try {

            locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
            boolean permissionGranted = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if(permissionGranted) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }

            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (canGetLocation) {

                // from GPS
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,  this);

                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

                if(loc != null){

                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    return;

                }else{

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    if(loc != null){
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                        return;
                    }

                }


                if(loc == null){
                    loc= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(loc != null){
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                        return;
                    }

                }

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<android.location.Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
