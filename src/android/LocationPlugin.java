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
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;


public class LocationPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
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
        return false;
    }

    protected void getLastKnownLocation(CallbackContext callbackContext) throws JSONException {
        LocationManager locationManager = (LocationManager) this.webView.getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        List<String> providers = locationManager.getProviders(true);

        for (String prov : providers) {
            Log.d(getClass().getSimpleName(), "showCurrentLocation: " + prov);
        }

        if (provider != null) {


            Location location = locationManager.getLastKnownLocation(provider);
            System.out.println("--------------in locationManager");

            if (location != null) {
                String message = String.format("Current Location", location.getLongitude(), location.getLatitude());
                System.out.println("--------------in showCurrenenter code heretLocation---" + message);
                JSONObject obj = new JSONObject();
                obj.put("lat", location.getLatitude());
                obj.put("long", location.getLongitude());
                obj.put("time", location.getTime());
                obj.put("provider", location.getProvider());
                callbackContext.success(obj);

            } else {
                System.out.println("in showCurrentLocation error");
                callbackContext.error("Unable to get location.");
            }
        }
    }

    protected void getLastKnownLocation(CallbackContext callbackContext) throws JSONException {
        LocationManager locationManager = (LocationManager) this.webView.getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), false);
        List<String> providers = locationManager.getProviders(true);

        for (String prov : providers) {
            Log.d(getClass().getSimpleName(), "showCurrentLocation: " + prov);
        }

        if (provider != null) {
            Location location = locationManager.getLocation(provider);
            System.out.println("--------------in locationManager");

            if (location != null) {
                String message = String.format("Current Location", location.getLongitude(), location.getLatitude());
                System.out.println("--------------in showCurrenenter code heretLocation---" + message);
                JSONObject obj = new JSONObject();
                obj.put("lat", location.getLatitude());
                obj.put("long", location.getLongitude());
                obj.put("time", location.getTime());
                obj.put("provider", location.getProvider());
                callbackContext.success(obj);

            } else {
                System.out.println("in showCurrentLocation error");
                callbackContext.error("Unable to get location.");
            }
        }
    }

    private LocationManager getSystemService(String locationService) {
        // TODO Auto-generated method stub
        return null;
    }
}
