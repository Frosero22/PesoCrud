package com.appcrud.pesosaludablecrud.Utils;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;

public class Location {

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
        return String.valueOf(latitude+longitude);
    }

    public void getLocation() {
        try {
            if (canGetLocation) {

                // from GPS
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

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
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    return;
                }

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

}
