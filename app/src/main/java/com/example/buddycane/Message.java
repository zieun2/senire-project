package com.example.buddycane;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.telephony.SmsManager;
import android.widget.Toast;

public class Message extends AppCompatActivity {

    private ImageButton send_button;
    private EditText phone_number;
    private EditText message_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        send_button = (ImageButton) findViewById(R.id.send_button);
        phone_number = (EditText) findViewById(R.id.phone_number);
        message_content = (EditText) findViewById(R.id.message_content);




        ImageButton menu_home = (ImageButton) findViewById(R.id.menu_home);
        menu_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //입력한 값을 가져와 변수에 담는다
                String phoneNo = phone_number.getText().toString();
                String sms = message_content.getText().toString();

                Intent intent = new Intent(getApplicationContext(), Message.class);
                startActivity(intent);

                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    /*Toast.makeText(getApplicationContext(), "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();*/

                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();//오류 원인이 찍힌다.
                }
            }
        });


    }
}