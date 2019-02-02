package com.example.appwithchat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        View bv = findViewById(R.id.mainActivity);
//        bv.setBackgroundColor(getResources().getColor(R.color.colorLightGrey));
//
//        EditText editText = (EditText) findViewById(R.id.editText);
//        final int greyColor =  getResources().getColor(R.color.colorGrey);
//        editText.setBackgroundColor(greyColor);
//
//        final Button button = (Button) findViewById(R.id.joinButton );
//        final int orangeColor =  getResources().getColor(R.color.colorOrange);
//        button.setBackgroundColor(greyColor);
//        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        wifi.setWifiEnabled(true);
        Intent intent = new Intent(NotMainActivity.this, BroadcastReceiverActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();

        String phoneNumber = android.os.Build.MODEL;;
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();

        for (Account ac : accounts) {
            String acname = ac.name;
            String actype = ac.type;
            // Take your time to look at all available accounts
            System.out.println("Accounts : " + acname + ", " + actype);
            if(actype.equals("com.whatsapp")){
                phoneNumber = ac.name;
            }
        }

        intent.putExtra("nameText", phoneNumber);
        startActivity(intent);
//        editText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if(s.toString().trim().length()==0){
//                    button.setEnabled(false);
//                    button.setBackgroundColor(greyColor);
//                } else {
//                    button.setEnabled(true);
//                    button.setBackgroundColor(orangeColor);
//                }
//
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//    }
//
//    public void joinPressed(View view) {
//        Intent intent = new Intent(NotMainActivity.this, BroadcastReceiverActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra("nameText", message);
//        startActivity(intent);
//
//    }
    }
}
