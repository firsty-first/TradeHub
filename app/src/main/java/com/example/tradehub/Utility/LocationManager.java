package com.example.tradehub.Utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationManager implements LifecycleObserver {//just for the sake of cleaner code and separating
    //location things which gets messy when implemented in an activity/fragment
    //lifecycleobserver provides hadnling oncreate onpause etc in a normal java class

//
    private static final String TAG = "LocationManager";
    private static final long UPDATE_INTERVAL = 10 * 1000; // 10 seconds
    private static final long FASTEST_INTERVAL = 5 * 1000; // 5 seconds

    private Context mContext;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationListener mLocationListener;

    public LocationManager(Context context) {
        mContext = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    public interface LocationListener {
        void onLocationReceived(Location location);
        void onLocationError(String error);
    }

    public void setLocationListener(LocationListener listener) {
        mLocationListener = listener;
    }

    public void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    if (mLocationListener != null) {
                        mLocationListener.onLocationReceived(location);
                    }
                } else {
                    if (mLocationListener != null) {
                        mLocationListener.onLocationError("Failed to get location");
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (mLocationListener != null) {
                mLocationListener.onLocationError("Location permission not granted");
            }
            return;
        }

        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdates() {
        if (mFusedLocationClient != null && mLocationCallback != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        stopLocationUpdates();
    }
}
