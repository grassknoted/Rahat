package com.example.akash.rahat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.google.android.gms.common.api.PendingResult;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private RadioButton radioCallerButton;
    private RadioGroup radioCallerGroup;
    protected boolean locationFound = false;
    protected boolean helpRequested = false;

    PendingResult<LocationSettingsResult> result;

    private double latitude = 100.0000000000000;
    private double longitude = 100.0000000000000;
    protected LocationManager locationManager;


    ArrayList<String> places = new ArrayList<String>();

    public void sendSMS(String phoneNo, String msg) {
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
        Log.d("MainA", "Leaving sendSMS");
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
            if(isNetworkAvailable()) {
                Log.d("Main", "Network Provider Selected");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
            else {
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
        if (places.size() == 0 || places.size() == 1) {
            places.add(Double.toString(latitude) + ' ' + Double.toString(longitude));
        }
    }

    public void sendTheMessageHelper(String lat, String lon) {
        Log.d("Main", "sendMessageHelper Called");
        Log.d("Main", "L: "+locationFound+" H: "+helpRequested);
        if(locationFound && helpRequested) {
            Toast.makeText(getApplicationContext(), "Requesting help..",
                    Toast.LENGTH_LONG).show();
            sendTheMessage();
            if(!(latitude == 100.0000000000000 && longitude == 100.0000000000000)) {
                Log.d("Main2", "Help Not Requested anymore");
                helpRequested = false;
            }
            else {
                Log.d("Main2","Invalid LatLong");
            }
        }
        else if (!locationFound && helpRequested) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d("Main", "Unable to sleep");
            }
            sendTheMessageHelper(lat, lon);
        }
        else if(locationFound && !helpRequested) {
            Log.d("Main", "Help not requested, but new Location found.");
        }
        else {
            Log.d("Main", "Help not requested, and no new Location as well.");
        }
    }

    private void respondToAlert() {
        String lat = Double.toString(latitude);
        String lon = Double.toString(longitude);
        String androidID = "nada";
        Log.d("MainA", "Respond to Alert reached");
        try {
            Log.d("MainA", "Entered try");
//            String androidID = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
            androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("MainA", "AndroidID: "+androidID);
        }
        catch (SecurityException e) {
            Log.d("MainA", "Security exception!");
        }
        Log.d("MainA", "Before sending SMS");
        sendSMS("9036112331", "My current Location. "+androidID+": "+lat+" "+lon);
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
            Log.d("Main", "AndroidID: "+androidID);
        }
        catch (SecurityException e) {
            Log.d("Main", "Security exception!");
        }

        int numberOfPeople;

        EditText editText = (EditText)findViewById(R.id.numberOfPeople);
        try {
            numberOfPeople = Integer.parseInt(editText.getText().toString());
        }
        catch (NumberFormatException e) {
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

        if(latitude>0.10 && longitude>0.10)
            sendSMS("9036112331", "Hello. "+androidID+": "+lat+" "+lon+" "+needs+" "+numberOfPeople);
        else {
            Log.d("Main", "Invalid RadioButton Selection.");
        }
    }


    public void turnOffWifi() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            Log.d("Main","WiFi is connected, do nothing.");
        }
        else {
            Log.d("Main", "WiFi Function Called");
            WifiManager wifiManager = (WifiManager)this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
        }
        Log.d("Main","Starting GPS");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);
        getLocation();

        Button help_button = (Button) findViewById(R.id.help_button);
        help_button.setOnClickListener(this);

        Button chat_button = (Button) findViewById(R.id.chat_button);
        chat_button.setOnClickListener(this);

        Button miss_button = (Button) findViewById(R.id.miss_button);
        miss_button.setOnClickListener(this);

        Button map_button = (Button) findViewById(R.id.map_button);
        map_button.setOnClickListener(this);

        Button info_button = (Button) findViewById(R.id.info_button);
        info_button.setOnClickListener(this);

        Log.d("MainB", "OnCreate Started");
//        main_object = new MainActivity();
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                Log.d("Main", messageText);

                if (messageText.length() >= 8) {
                    if (messageText.startsWith("#R")) {
                        Toast.makeText(MainActivity.this, "Message: " + messageText, Toast.LENGTH_LONG).show();
                    }

                    if (messageText.charAt(2) == 'I') {
                        Log.d("Main", "Informative Message");
                    } else if (messageText.charAt(2) == 'A') {
                        Log.d("MainA", "Alert Message");
                        turnOffWifi();
                        Log.d("MainA", "Turned off");
                        helpRequested = true;
                        Log.d("MainA", "Starting main function");
                        respondToAlert();

                    } else if (messageText.charAt(2) == 'F') {
                        Log.d("Main", "Found Message");
                    }
                    else if (messageText.charAt(2) == 'M') {
                        Log.d("Main", "Map Message");
                        places.add(messageText.substring(4));
                        TextView map_updates = (TextView) findViewById(R.id.textOne2);
                        map_updates.setVisibility(View.VISIBLE);
                    }
                    else {
                        Log.d("Main", "Invalid Message");
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.d("MainB", "OnClick Entered");
        switch (v.getId()) {
            case R.id.help_button:
                Log.d("MainB", "Help button pressed.");
                Intent start_main_activity = new Intent(MainActivity.this, FirstActivity.class );
                startActivity(start_main_activity);
                break;
            case R.id.info_button:
                Log.d("MainB", "Info button pressed.");

                Intent start_inform_activity = new Intent(MainActivity.this, Information.class );
                start_inform_activity.putExtra("Latitude", Double.toString(latitude));
                start_inform_activity.putExtra("Longitude", Double.toString(longitude));

                startActivity(start_inform_activity);

                break;
            case R.id.map_button:
                Log.d("MainB", "Map button pressed.");
                Intent start_map_activity = new Intent(MainActivity.this, MapsActivity.class );
                start_map_activity.putStringArrayListExtra("Places", places);

                startActivity(start_map_activity);

                TextView map_updates = (TextView) findViewById(R.id.textOne2);
                map_updates.setVisibility(View.INVISIBLE);
                break;

            case R.id.chat_button:
                Log.d("MainB", "Chat button pressed.");
                Intent start_chat_activity = new Intent(MainActivity.this, ChatActivity.class );
                start_chat_activity.putExtra("Latitude", Double.toString(latitude));
                start_chat_activity.putExtra("Longitude", Double.toString(longitude));
                startActivity(start_chat_activity);
                break;
            case R.id.miss_button:
                Log.d("MainB", "Miss button pressed.");
                Intent start_miss_activity = new Intent(MainActivity.this, MissingActivity.class );
                Log.d("MainM", "Before starting activity");
                startActivity(start_miss_activity);
                break;
        }
    }


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
}