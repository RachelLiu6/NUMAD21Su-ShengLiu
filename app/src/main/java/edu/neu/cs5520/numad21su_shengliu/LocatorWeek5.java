package edu.neu.cs5520.numad21su_shengliu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocatorWeek5 extends AppCompatActivity implements LocationListener {
    TextView text;
    Button button;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator_week5);

        text = (TextView) findViewById(R.id.location);
        button = (Button) findViewById(R.id.locationButton);

        if(savedInstanceState != null) {
            String location = savedInstanceState.getString("location");
            text.setText(location);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermit();
                getLocation();
            }
        });
    }

    private void checkPermit() {

        if (ContextCompat.checkSelfPermission(LocatorWeek5.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocatorWeek5.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("location", text.getText().toString());
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, LocatorWeek5.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Called when the location has changed
    @Override
    public void onLocationChanged(@NonNull Location location) {
        text.setText("Latitude is: " + location.getLatitude() +"\nLongitude is: " + location.getLongitude());
    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}