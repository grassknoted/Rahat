package com.example.akash.rahat;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity_Hindi extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    public int message_id = 2; // First 2 for Dummy messages
    private String latitude = "100.0000000000000";
    private String longitude = "100.0000000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getString("Latitude");
        longitude = bundle.getString("Longitude");
        initControls();
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Your help request was escalated.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        Log.d("MainA", "Leaving sendSMS");
    }

    public void sendText() {
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
        Log.d("MainA", "Before sending SMS");
        sendSMS("9036112331", "#CC "+androidID+": "+latitude+" "+longitude);
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("Rahat");// Hard Coded
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(message_id);
                Log.d("Chat", "MsgID: "+message_id);
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);
                message_id = message_id+1;

                messageET.setText("");

                displayMessage(chatMessage);
                try {
                    Thread.sleep(150);
                } catch(InterruptedException e) {
                    Log.d("Chat", "Interrrrrupt!");
                }

                if(message_id == 3)
                    respondToMessage(message_id-1);
                else if(((message_id == 6 && messageText.contains("yes")) || (message_id == 6 && messageText.contains("हाँ"))))
                    respondToMessage1_yes(message_id-1);
                else if(message_id == 6 && messageText.contains("okay") || message_id == 6 && messageText.contains("ok"))
                    respondToMessage1_ok(message_id-1);
                else if(message_id == 6 && messageText.contains("no") || message_id == 6 && messageText.contains("नहीं"))
                    respondToMessage1_no(message_id-1);
                else if(message_id == 8) {
                    respondToSymptoms(message_id-1);
                }
                else {
                    ChatMessage fin_message = new ChatMessage();
                    fin_message.setId(message_id);
                    fin_message.setMessage("धन्यवाद!\n" +
                            "ध्यान रखें।");
                    fin_message.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                    message_id = message_id+1;

                    displayMessage(fin_message);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent go_to_home = new Intent(ChatActivity_Hindi.this, MainActivity.class);
                            startActivity(go_to_home);
                        }
                    }, 2000);
                }
            }
        });
    }

    public void respondToMessage1_no(int msg_id) {
        ChatMessage chatMessage1a_no = new ChatMessage();
        chatMessage1a_no.setId(message_id);
        chatMessage1a_no.setMessage("हमें यह सुनकर खुशी हुई।");
        chatMessage1a_no.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        message_id = message_id+1;

        ChatMessage chatMessage1b_no = new ChatMessage();
        chatMessage1b_no.setId(message_id);
        chatMessage1b_no.setMessage("चिंता मत करो, मदद रास्ते पर है! कृपया शांत रहें और धैर्य रखें।");
        chatMessage1b_no.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage1b_no.setMe(false);
        message_id = message_id+1;

        messageET.setText("");

        displayMessage(chatMessage1a_no);
        displayMessage(chatMessage1b_no);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent go_to_home = new Intent(ChatActivity_Hindi.this, MainActivity.class);
                startActivity(go_to_home);
            }
        }, 5000);
    }

    public void respondToMessage1_ok(int msg_id) {
        ChatMessage chatMessage2_ok = new ChatMessage();
        chatMessage2_ok.setId(message_id);
        chatMessage2_ok.setMessage("धन्यवाद!\n" +
                "ध्यान रखें।");
        chatMessage2_ok.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage2_ok.setMe(false);
        message_id = message_id+1;

        displayMessage(chatMessage2_ok);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent go_to_home = new Intent(ChatActivity_Hindi.this, MainActivity.class);
                startActivity(go_to_home);
            }
        }, 2000);


    }

    public void respondToMessage(int msg_id) {
        Log.d("Chat", "respondToMessage called! MsgID: "+Integer.toString(msg_id));
        ChatMessage chat_message1 = adapter.getItem(msg_id);
        String current_message = chat_message1.getMessage().replaceAll("[^a-zA-Z ]", "").toLowerCase();
        Log.d("Chat", "Passing Initialization!");

        if (current_message.contains("हाँ") || current_message.contains("yes")) {
            Log.d("Chat", "Enters First Yes");
            ChatMessage chatMessage1a_yes = new ChatMessage();
            chatMessage1a_yes.setId(message_id);
            chatMessage1a_yes.setMessage("आपका स्थान अधिकारियों को भेजा गया है। कृपया निम्नलिखित सावधानी बरतें:\n" +
                    "1. उच्च जमीन की तलाश करें\n" +
                    "2. फ्लैश बाढ़ क्षेत्रों जैसे धाराओं, जल निकासी चैनलों से दूर रहें\n" +
                    "3. निकालने के लिए तैयार रहो\n" +
                    "4. गीले होने पर विद्युत उपकरण को छूएं नहीं\n" +
                    "5. चलने वाले पानी के माध्यम से मत चलना\n" +
                    "6. बाढ़ वाली सड़क पर ड्राइव करने की कोशिश मत करो");
            chatMessage1a_yes.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage1a_yes.setMe(false);
            message_id = message_id+1;

            ChatMessage chatMessage1b_yes = new ChatMessage();
            chatMessage1b_yes.setId(message_id);
            chatMessage1b_yes.setMessage("क्या आपको कोई चोट या चिकित्सा स्थितियां हैं, या आपके साथ कोई सामना कर रहा है?");
            chatMessage1b_yes.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage1b_yes.setMe(false);
            message_id = message_id+1;

            messageET.setText("");

            displayMessage(chatMessage1a_yes);
            displayMessage(chatMessage1b_yes);
        }
        else {
            Log.d("Chat", "Enters First No");
            ChatMessage chatMessage1a_no = new ChatMessage();
            chatMessage1a_no.setId(message_id);
            chatMessage1a_no.setMessage("हमें यह सुनकर खुशी हुई।");
            chatMessage1a_no.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            message_id = message_id+1;

            ChatMessage chatMessage1b_no = new ChatMessage();
            chatMessage1b_no.setId(message_id);
            chatMessage1b_no.setMessage("यदि आपके पास प्राकृतिक आपदा से प्रभावित कोई मित्र या परिवार है, तो कृपया उनकी एक तस्वीर अपलोड करके हमारे बचाव अभियान में हमारी सहायता करें।\n" +
                    "यदि आपके पास कोई संसाधन है जो पीड़ितों से लाभ उठा सकता है, तो कृपया 'जानकारी प्रदान करें' चुनकर पीड़ितों के साथ साझा करने के लिए जानकारी प्रदान करें।");
            chatMessage1b_no.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage1b_no.setMe(false);
            message_id = message_id+1;

            messageET.setText("");

            displayMessage(chatMessage1a_no);
            displayMessage(chatMessage1b_no);

        }
    }

    public void respondToMessage1_yes(int msg_id) {
        Log.d("Chat", "respondToMessage1_yes called!");
        ChatMessage chat_message = adapter.getItem(msg_id);
        String current_message = chat_message.getMessage().replaceAll("[^a-zA-Z ]", "").toLowerCase();


        if (current_message.contains("yes") || current_message.contains("हाँ")) {
            ChatMessage chatMessage2_yes_yes = new ChatMessage();
            chatMessage2_yes_yes.setId(message_id);
            chatMessage2_yes_yes.setMessage("कृपया लक्षणों की सूची दें।");
            chatMessage2_yes_yes.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatMessage2_yes_yes.setMe(false);
            message_id = message_id + 1;

            displayMessage(chatMessage2_yes_yes);
        }
    }

    public void respondToSymptoms(int msg_id) {
        Log.d("Chat", "Respond to symptoms");
        ChatMessage chat_message1 = adapter.getItem(msg_id);
        String current_message = chat_message1.getMessage().replaceAll("[^a-zA-Z ]", "").toLowerCase();
        String[] jaundice_symptoms = {"fatigue", "fever", "vomiting", "yellow", "nausea"};
        String[] leptospirosis_symptoms = {"headache", "rash", "bleeding", "muscle", "diarrhea"};
        String[] dengue_symptoms = {"headache", "joint", "muscle", "nausea", "bone"};

        int jaundice_probability = 0;
        int leptospirosis_probability = 0;
        int dengue_probability = 0;

        String disease = "";
        String treatment = "";
        String medication = "";

        for (String symptom : jaundice_symptoms) {
            // Jaundice
            if (current_message.contains(symptom))
                jaundice_probability++;

        }
        for (String symptom : leptospirosis_symptoms) {
            // Leptospirosis
            if (current_message.contains(symptom))
                leptospirosis_probability++;

        }
        for (String symptom : dengue_symptoms) {
            // Dengue
            if (current_message.contains(symptom))
                dengue_probability++;

        }

        ChatMessage chatMessage1 = new ChatMessage();
        chatMessage1.setId(message_id);

        ChatMessage chatMessage2 = new ChatMessage();
        chatMessage2.setId(message_id);

        if (jaundice_probability > leptospirosis_probability) {
            if (jaundice_probability > dengue_probability) {
                // jaundice
                disease = "Jaundice\n";
                treatment = "Treatment:\n1. Staying hydrated is one of the best ways to help the liver recover from jaundice. \n" +
                        "2. Please ensure that you do not get stressed. Try to remain calm despite the calamity. \n" +
                        "3. Exhausting tasks are not advisable as it would strain the liver. \n" +
                        "4. If infants are showing symptoms of jaundice, try to feed them as regularly as possible until further help arrives.";
                medication = "Medication: Crocine or Dolo 650 (or any paracetamol), methylcobalamine.";
                sendText();

            } else {
                //dengue
                disease = "Dengue\n";
                treatment = "Treatment:\n1. Drink plenty of fluids to avoid dehydration from vomiting and a high fever.\n" +
                        "2. Do not exert yourself by performing strenuous tasks.\n" +
                        "3. Rest as much as possible.\n" +
                        "4. Avoid medicines with aspirin which could worsen bleeding.";
                medication = "Medication: Benzyl penicillin (or any penicillin tablet), Doxycycline, Ibuprofen.";
                sendText();
            }
        } else {
            if (leptospirosis_probability > dengue_probability) {
                // lepto
                disease = "Leptospirosis\n";
                treatment = "Treatment:\n1. Do not leave skin wounds open. Clean and cover them as soon as possible.\n" +
                        "2. Avoid drinking water from rivers, lakes or streams. \n" +
                        "3. Stay away from dogs and other animals in the vicinity.\n" +
                        "4. Avoid contact with or consuming anything that has been in contact with flood water. Try to refrain from swimming or moving in these water bodies";
                medication = "Medication: Benzyl penicillin (or any penicillin tablet), Doxycycline, Ibuprofen.";
                sendText();
            } else {
                //dengue
                disease = "Dengue\n";
                treatment = "Treatment:\n1. Drink plenty of fluids to avoid dehydration from vomiting and a high fever.\n" +
                        "2. Do not exert yourself by performing strenuous tasks.\n" +
                        "3. Rest as much as possible.\n" +
                        "4. Avoid medicines with aspirin which could worsen bleeding.";
                medication = "Medication: Diclofenac (or analgesic), Crocine or Dolo 650 (or any paracetamol).";
                sendText();
            }
        }

        chatMessage1.setMessage("Predicted disease: "+disease+treatment);
        chatMessage1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage1.setMe(false);
        message_id = message_id + 1;

        chatMessage2.setMessage(medication);
        chatMessage2.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatMessage2.setMe(false);
        message_id = message_id + 1;

        displayMessage(chatMessage1);
        displayMessage(chatMessage2);
    }



//            String flood_precautions = "During a flood:\n1. Seek higher ground\n2. Stay away from flash flood areas such as streams, drainage channels\n3. Be ready to evacuate\n4. Do not touch electrical equipment if wet\n5. Do not walk through moving water\n6. Do not try to drive over a flooded road";
//            chatMessage.setMessage(flood_precautions);
//            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//            chatMessage.setMe(false);
//            displayMessage(chatMessage);
//            message_id = message_id + 1;
//
//            Log.d("MainC", "First mesage done");
//
//            ChatMessage schatMessage = new ChatMessage();
//            Log.d("MainC", "Before setting message_id");
//            schatMessage.setId(message_id);
//            Log.d("Chat", Integer.toString(message_id));
//            Log.d("MainC", "After setting message_id");
//            schatMessage.setMessage("Do you have any medical issues? If yes, what are they?");
//            schatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//            schatMessage.setMe(false);
//            displayMessage(schatMessage);
//            message_id = message_id + 1;
//            Log.d("MainC", "Before the last line.");
//
//            Log.d("Chat", Integer.toString(message_id));
//            ChatMessage new_chat_message = adapter.getItem(message_id);
//            String current_message2 = new_chat_message.getMessage().toLowerCase();
//
//            Log.d("Surprisingly, this works!","Final line of IF");
//            if(current_message2.contains("joint pain") && current_message2.contains("high fever") && current_message2.contains("coughing blood")) {
//                ChatMessage leptoMessage = new ChatMessage();
//                leptoMessage.setId(message_id);
//                leptoMessage.setMessage("The most likely diagnosis is: Leptospirosis.\nPlease consume Glucose and salt solution.\nTry to avoid contact with the flood water.\nMedicine: penicillin G, ampicillin, amoxicillin.");
//                leptoMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//                leptoMessage.setMe(false);
//                displayMessage(leptoMessage);
//
//
//                ChatMessage leptoMessage2 = new ChatMessage();
//                leptoMessage2.setId(message_id);
//                leptoMessage2.setMessage("Leptospirosis is a communicable disease, please refrain from the use of stagnant water, the authorities have been informed about your condition.\nHelp is on the way.");
//                leptoMessage2.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//                leptoMessage2.setMe(false);
//                displayMessage(leptoMessage2);
//                message_id = message_id + 1;
//            }
//
//        } else {
//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setId(message_id);
//            chatMessage.setMessage("I am glad to hear that! Please provide any information if possible, we will get it across to people in need.\nThank you!");
//            chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//            chatMessage.setMe(false);
//            displayMessage(chatMessage);
//            message_id = message_id + 1;
//        }
//
//
//        //String[] current_message_array = current_message.split("\\s+");


    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(0);
        msg.setMe(false);
        msg.setMessage("नमस्ते, हैलो, यह रहात है।\n");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(1);
        msg1.setMe(false);
        msg1.setMessage("क्या आप प्राकृतिक आपदा का शिकार हैं, और मदद चाहिए?");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatActivity_Hindi.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            Log.d("MainC", "Is this called?");
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}