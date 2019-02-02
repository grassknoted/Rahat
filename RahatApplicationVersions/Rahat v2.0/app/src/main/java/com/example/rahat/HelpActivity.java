package com.example.rahat;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HelpActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_dialogue);

        Button helpButton = (Button) findViewById(R.id.help_me);
        helpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Your request has been acknowledged.\nRahat is on the way.",
                Toast.LENGTH_LONG).show();
    }
}
