package com.example.akash.rahat;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class Information extends AppCompatActivity implements View.OnClickListener {

    private String current_lat;
    private String current_lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_activity);

        Bundle bundle = getIntent().getExtras();
        current_lat = bundle.getString("Latitude");
        current_lng = bundle.getString("Longitude");

        Button inform = (Button) findViewById(R.id.inform_button);
        inform.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String can_provide = "";
        String message_text = "#CI ";
        String androidID = "nada";
        try {
            Log.d("MainA", "Entered try");
//            String androidID = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
            androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("MainA", "AndroidID: "+androidID);
        }
        catch (SecurityException e) {
            Log.d("MainA", "Security exception!");
        }

        message_text = message_text + androidID;
        switch (v.getId()) {
            case R.id.inform_button:
                CheckBox food_box = (CheckBox) findViewById(R.id.food_box);
                CheckBox water_box = (CheckBox) findViewById(R.id.water_box);
                CheckBox firstaid_box = (CheckBox) findViewById(R.id.firstaid_box);
                CheckBox shelter_box = (CheckBox) findViewById(R.id.shelter_box);

                if (food_box.isChecked()) {
                    can_provide = can_provide + "F";
                }
                if (water_box.isChecked()) {
                    can_provide = can_provide + "W";
                }
                if (firstaid_box.isChecked()) {
                    can_provide = can_provide + "A";
                }
                if (shelter_box.isChecked()) {
                    can_provide = can_provide + "S";
                }

                message_text = message_text + can_provide + " ";

                EditText no_of_people = (EditText) findViewById(R.id.no_people);
                String no_people = no_of_people.getText().toString();
                if(no_people ==  "") {
                    message_text = message_text + "0 ";
                }
                else {
                    message_text = message_text + no_people + " ";
                }

                RadioButton current_address = (RadioButton) findViewById(R.id.current_location_selected);
                if(current_address.isChecked()) {
                    message_text = message_text + current_lat + " " + current_lng;
                }
                else {
                    EditText entered_address = (EditText) findViewById(R.id.help_address);
                    message_text = message_text + entered_address.getText();
                }

                sendSMS("9036112331", "#CI "+message_text);
                break;
            default:
                Log.d("Main", "Which button was clicked!?");
                break;
        }
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Thank you for your help!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        EditText enter_address = (EditText) findViewById(R.id.help_address);
        switch(view.getId()) {
            case R.id.current_location_selected:
                if (!checked)
                    enter_address.setEnabled(true);
                else
                    enter_address.setEnabled(false);
                break;
        }
    }
}
