package com.example.akash.rahat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationSettingsResult;

import java.security.Key;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class FirstActivity extends AppCompatActivity implements LocationListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle arg0) {
    }

    @Override
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    private RadioButton radioCallerButton;
    private RadioGroup radioCallerGroup;
    protected boolean locationFound = false;
    protected boolean helpRequested = false;

    PendingResult<LocationSettingsResult> result;

    private double latitude = 100.0000000000000;
    private double longitude = 100.0000000000000;
    protected LocationManager locationManager;


    ArrayList<String> places = new ArrayList<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendSMS(String phoneNo, String msg) {
        Log.d("MainB", "sendSMS Reached!");
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Help is on the way!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    void getLocation() {
        try {
            Log.d("Main", "Try initiated");

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_LOW);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            String provider = locationManager.getBestProvider(criteria, true);

            // Cant get a hold of provider
            if (provider == null) {
                Log.d("Main", "Provider is null");
                return;
            } else {
                Log.d("Main", "Provider: " + provider);
            }
            Log.d("Main", "Works till check!");
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3600000, 5, this);
            if (isNetworkAvailable()) {
                Log.d("Main", "Network Provider Selected");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            } else {
                Log.d("Main", "GPS Provider Selected");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            //requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Main", "Location Change detected");
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        locationFound = true;
        sendTheMessageHelper(Double.toString(latitude), Double.toString(longitude));

    }

    public void sendTheMessageHelper(String lat, String lon) {
        Log.d("Main", "sendMessageHelper Called");
        Log.d("Main", "L: " + locationFound + " H: " + helpRequested);
        if (locationFound && helpRequested) {
            Toast.makeText(getApplicationContext(), "Requesting help..",
                    Toast.LENGTH_LONG).show();
            sendTheMessage();
            if (!(latitude == 100.0000000000000 && longitude == 100.0000000000000)) {
                Log.d("Main2", "Help Not Requested anymore");
                helpRequested = false;
            } else {
                Log.d("Main2", "Invalid LatLong");
            }
        } else if (!locationFound && helpRequested) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("Main", "Unable to sleep");
            }
            sendTheMessageHelper(lat, lon);
        } else if (locationFound && !helpRequested) {
            Log.d("Main", "Help not requested, but new Location found.");
        } else {
            Log.d("Main", "Help not requested, and no new Location as well.");
        }
    }


    private void sendTheMessage() {
        String lat = Double.toString(latitude);
        String lon = Double.toString(longitude);
        CheckBox medicines = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox food = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox water = (CheckBox) findViewById(R.id.checkBox3);

        String androidID = "nada";

        try {
//            String androidID = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
            androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("Main", "AndroidID: " + androidID);
        } catch (SecurityException e) {
            Log.d("Main", "Security exception!");
        }

        int numberOfPeople;

        EditText editText = (EditText) findViewById(R.id.numberOfPeople);
        try {
            numberOfPeople = Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("Main", "Empty string to be converted");
            numberOfPeople = 0;
        }
        String needs = "";
        if (medicines.isChecked()) {
            needs = needs + "M";
        }
        if (food.isChecked()) {
            needs = needs + "F";
        }
        if (water.isChecked()) {
            needs = needs + "W";
        }

        Log.d("MainB", "Works till beg of sendTheMessage()");

        if (latitude != 100.000000 && longitude != 100.000000) {
            sendSMS("9036112331", "Hello Daddy. " + androidID + ": " + lat + " " + lon + " " + needs + " " + numberOfPeople);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getLocation();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button help_me = (Button) findViewById(R.id.help_me);
        help_me.setOnClickListener(this);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.help_me:
                helpRequested = true;
                sendTheMessage();
                break;
            default:
                Log.d("Main", "Which button was clicked!?");
        }
    }
}
