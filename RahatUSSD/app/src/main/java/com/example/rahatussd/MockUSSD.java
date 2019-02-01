package com.example.rahatussd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MockUSSD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_ussd);
        final Handler handler = new Handler();
        ProgressDialog Pdialog = ProgressDialog.show(MockUSSD.this, "", "Loading. Please wait...", true);

        showInputDialog(R.layout.input_dialog1);
    }

    protected void showInputDialog(int input_xml) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MockUSSD.this);
        View promptView = layoutInflater.inflate(input_xml, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockUSSD.this);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setTitle("Rahat: Alert");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("USSD", editText.getText().toString());
                        String inp = editText.getText().toString();
                            if (inp.contains("0")) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showInputDialog2(R.layout.input_dialog2);;
                                    }
                                }, 1000);

                            } else {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showInputDialog3(R.layout.input_dialog3);
                                    }
                                }, 1000);

                            }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showInputDialog2(int input_xml) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MockUSSD.this);
        View promptView = layoutInflater.inflate(input_xml, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockUSSD.this);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setTitle("Rahat: Provide Help");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("USSD", editText.getText().toString());
                        String inp = editText.getText().toString();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showInputDialog4(R.layout.input_dialog4);
                            }
                        }, 1000);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showInputDialog3(int input_xml) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MockUSSD.this);
        View promptView = layoutInflater.inflate(input_xml, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockUSSD.this);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setTitle("Rahat: Rescue");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("USSD", editText.getText().toString());
                        String inp = editText.getText().toString();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showInputDialog5(R.layout.input_dialog5);
                            }
                        }, 1000);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showInputDialog4(int input_xml) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MockUSSD.this);
        View promptView = layoutInflater.inflate(input_xml, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockUSSD.this);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setTitle("Rahat: Provide Help");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("USSD", editText.getText().toString());
                        String inp = editText.getText().toString();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showInputDialog6(R.layout.input_dialog6);
                            }
                        }, 1000);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showInputDialog5(int input_xml) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MockUSSD.this);
        View promptView = layoutInflater.inflate(input_xml, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockUSSD.this);
        alertDialogBuilder.setView(promptView);

        if(input_xml == R.layout.input_dialog2)
            alertDialogBuilder.setTitle("Rahat: Rescue");
        else if(input_xml == R.layout.input_dialog3)
            alertDialogBuilder.setTitle("Rahat: Rescue");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
//                .setPositiveButton("Respond", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Log.d("USSD", editText.getText().toString());
//                    }
//                })
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    protected void showInputDialog6(int input_xml) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MockUSSD.this);
        View promptView = layoutInflater.inflate(input_xml, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockUSSD.this);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setTitle("Rahat: Provide Help");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)

                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}

//        AlertDialog.Builder builder;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder = new AlertDialog.Builder(MockUSSD.this, android.R.style.Theme_Material_Dialog_Alert);
//        } else {
//            builder = new AlertDialog.Builder(MockUSSD.this);
//        }
//        View viewInflated = LayoutInflater.from(MockUSSD.this).inflate(R.layout.android_ussd, (ViewGroup) findViewById(android.R.id.content), false);
//        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
//        builder.setView(viewInflated);
//        // Set up the buttons
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                System.out.print(input.getText().toString());
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//        builder.setTitle("Delete entry")
//
//                .setMessage("Are you sure you want to delete this entry?")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with delete
//                    }
//
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();