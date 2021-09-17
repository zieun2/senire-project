package com.example.buddycane;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ImageButton map_bt = (ImageButton) findViewById(R.id.map_bt);
        map_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);


            }
        });

        ImageButton sms_bt = (ImageButton) findViewById(R.id.sms_bt);
        sms_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Message.class);
                startActivity(intent);


            }
        });

        ImageButton led_bt = (ImageButton) findViewById(R.id.led_bt);
        led_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Led.class);
                startActivity(intent);


            }
        });

        ImageButton phone_bt = (ImageButton) findViewById(R.id.phone_bt);
        phone_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Phone.class);
                startActivity(intent);


            }
        });
    }
}