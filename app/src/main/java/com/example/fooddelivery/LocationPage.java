package com.example.fooddelivery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;
import java.util.Locale;


public class LocationPage extends Activity implements LocationListener{
    private LocationManager locationManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(LocationPage.this, Manifest.permission.ACCESS_FINE_LOCATION
        )!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationPage.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        getCurrentLocation();
        Intent intent = new Intent(LocationPage.this, MultiPage.class);
        startActivity(intent);
            }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, LocationPage.this);

        } catch (Exception e) {
            Toast.makeText(LocationPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(LocationPage.this, ""+location.getLatitude()+" " +location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(LocationPage.this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
            String address = addressList.get(0).getAddressLine(0);
//            etLocation.setText(address);

        } catch (Exception e) {
            Toast.makeText(LocationPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

