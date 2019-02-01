package com.example.rahatussd;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mButton = (Button) findViewById(R.id.mockButton);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("MainB", "OnClick Entered");
        switch (v.getId()) {

            case R.id.mockButton:
                Log.d("MainB", "Help button pressed.");
                Button mButton = (Button) findViewById(R.id.mockButton);
                mButton.setVisibility(View.INVISIBLE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent start_main_activity = new Intent(MainActivity.this, MockUSSD.class);
                        startActivity(start_main_activity);
                    }
                }, 2500);
                break;

        }
    }
}
