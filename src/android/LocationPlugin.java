package com.drmabuse.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.os.Bundle;

import java.util.List;


public class LocationPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {
      if (ActivityCompat.checkSelfPermission(this.webView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.webView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          callbackContext.error("PERMISSION_DENIED please add the manifest permission ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION");
      }

      if ("lastLocation".equals(action)) {
          this.getLastKnownLocation(callbackContext);
          return true;
      } else if ("getLocation".equals(action)) {
          this.getLocation(callbackContext);
          return true;
      }
        callbackContext.error("Action not fround " + action);
        return false;
    }


    private static void returnLocation(Location location, final CallbackContext callbackContext) {
        final JSONObject obj = new JSONObject();
        try {
            obj.put("lat", location.getLatitude());
            obj.put("long", location.getLongitude());
            obj.put("time", location.getTime());
            obj.put("provider", location.getProvider());
        } catch(JSONException e) {
            callbackContext.error(e.getMessage());
            return;
        }
        callbackContext.success(obj);
    }

    protected void getLastKnownLocation(final CallbackContext callbackContext) {
        final LocationManager locationManager = (LocationManager) this.webView.getContext().getSystemService(Context.LOCATION_SERVICE);
        final String provider = locationManager.getBestProvider(new Criteria(), false);

        if (provider != null) {
            final Location location = locationManager.getLastKnownLocation(provider);
            System.out.println("--------------in locationManager");

            if (location != null) {
                final String message = String.format("Current Location", location.getLongitude(), location.getLatitude());
                System.out.println("--------------in showCurrenenter code heretLocation---" + message);
                returnLocation(location, callbackContext);
            } else {
                System.out.println("in showCurrentLocation error");
                callbackContext.error("Unable to get location.");
            }
        } else {
            callbackContext.error("Unable to get provider.");
        }
    }

    
    protected void getLocation(final CallbackContext callbackContext) {
        final LocationManager locationManager = (LocationManager) this.webView.getContext().getSystemService(Context.LOCATION_SERVICE);
        final String provider = locationManager.getBestProvider(new Criteria(), false);
        
        if (provider != null) {
            // Define a listener that responds to location updates
            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    returnLocation(location, callbackContext);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            };
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestSingleUpdate(provider, locationListener, null);
        } else {
            callbackContext.error("Unable to get provider.");
        }
    }

    private LocationManager getSystemService(String locationService) {
        // TODO Auto-generated method stub
        return null;
    }
}
