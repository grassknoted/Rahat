package com.example.rahat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationSettingsResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SensorEventListener {

        protected boolean locationFound = false;
        protected boolean helpRequested = false;
    private float currentDegree = 0f;
    private ImageView image;

    // device sensor manager
    private SensorManager mSensorManager;

        PendingResult<LocationSettingsResult> result;

        private double latitude = 100.0000000000000;
        private double longitude = 100.0000000000000;
        protected LocationManager locationManager;


        ArrayList<String> places = new ArrayList<String>();

        public void sendSMS(String msg) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(getResources().getString(R.string.server_number), null, msg, null, null);
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
            if (places.size() == 0 || places.size() == 1) {
                places.add(Double.toString(latitude) + ' ' + Double.toString(longitude));
            }
        }

        private void respondToAlert() {
            String lat = Double.toString(latitude);
            String lon = Double.toString(longitude);
            String androidID = "nada";
            Log.d("MainA", "Respond to Alert reached");
            try {
                Log.d("MainA", "Entered try");
                androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                Log.d("MainA", "AndroidID: "+androidID);
            }
            catch (SecurityException e) {
                Log.d("MainA", "Security exception!");
            }
            Log.d("MainA", "Before sending SMS");
            sendSMS("My current Location. "+androidID+": "+lat+" "+lon);
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

        setContentView(R.layout.activity_main);
        getLocation();

        Button helpButton = (Button) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(this);

        Button chatbotButton = (Button) findViewById(R.id.chatbotButton);
        chatbotButton.setOnClickListener(this);

        Button faceButton = (Button) findViewById(R.id.faceButton);
        faceButton.setOnClickListener(this);

        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);

        Button infoButton = (Button) findViewById(R.id.infoButton);
        infoButton.setOnClickListener(this);

        Button wifiButton = (Button) findViewById(R.id.wifiButton);
        wifiButton.setOnClickListener(this);

        Log.d("MainB", "OnCreate Started");
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                //From the received text string you may do string operations to get the required OTP
                //It depends on your SMS format
                Log.d("Main", messageText);

                if (messageText.length() >= 8) {
                    if (messageText.startsWith("#R")) {
                        Toast.makeText(MainActivity.this,
                                "Message: " + messageText, Toast.LENGTH_LONG).show();
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
//                        TextView map_updates = (TextView) findViewById(R.id.updateNotification);
//                        map_updates.setVisibility(View.VISIBLE);
                    }
                    else {
                        Log.d("Main", "Invalid Message");
                    }
                }
            }
        });
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        image = (ImageView) findViewById(R.id.compassImage);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);


        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }


    @Override
    public void onClick(View v) {
        Log.d("MainB", "OnClick Entered");
        switch (v.getId()) {

            case R.id.helpButton:
                Log.d("MainB", "Help button pressed.");
                Intent start_main_activity = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(start_main_activity);
                break;

            case R.id.infoButton:
                Log.d("MainB", "Info button pressed.");
                Intent start_inform_activity = new Intent(MainActivity.this, Information .class );
                start_inform_activity.putExtra("Latitude", Double.toString(latitude));
                start_inform_activity.putExtra("Longitude", Double.toString(longitude));
                startActivity(start_inform_activity);
                break;

            case R.id.mapButton:
                Log.d("MainB", "Map button pressed.");
                Intent start_map_activity = new Intent(MainActivity.this, MapsActivity.class );
                start_map_activity.putStringArrayListExtra("Places", places);
                startActivity(start_map_activity);
//                TextView map_updates = (TextView) findViewById(R.id.updateNotification);
//                map_updates.setVisibility(View.INVISIBLE);
                break;

            case R.id.chatbotButton:
                Log.d("MainB", "Chat button pressed.");
                Intent start_chat_activity = new Intent(MainActivity.this, ChatActivity.class );
                start_chat_activity.putExtra("Latitude", Double.toString(latitude));
                start_chat_activity.putExtra("Longitude", Double.toString(longitude));
                startActivity(start_chat_activity);
                break;

            case R.id.faceButton:
                Log.d("MainB", "Miss button pressed.");
                Intent start_miss_activity = new Intent(MainActivity.this, MissingActivity.class );
                Log.d("MainM", "Before starting activity");
                startActivity(start_miss_activity);
                break;

            case R.id.wifiButton:
                Log.d("MainB", "Miss button pressed.");
                Intent start_wifi_activity = new Intent(MainActivity.this, MissingActivity.class );
                Log.d("MainM", "Before starting activity");
                startActivity(start_wifi_activity);
                break;
        }
    }



    // Unnecessary functions that MUST be implemented follow:
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
