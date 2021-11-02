// 나만의 과제 : https://ddangeun.tistory.com/59 비교해보기
// HC-06 세팅법 : https://devsoboro.tistory.com/12?category=817998

package com.example.buddycane;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class Phone extends AppCompatActivity {

    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        ImageButton menu_home = (ImageButton) findViewById(R.id.menu_home);
        menu_home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class); // 홈버튼 누르면 홈으로 화면 전환하도록
                startActivity(intent);
            }
        });

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(Phone.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
            //startActivityForResult is deprecated..오류 뜨면
            // 참조 --> https://blog.daum.net/gomahaera/26
            // 참조 --> https://modelmaker.tistory.com/18
            // 참조 --> https://junyoung-developer.tistory.com/151
            // 참조 --> https://crazykim2.tistory.com/497

        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }

    public void setup() {
        Button btnSend = findViewById(R.id.btnSend); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("on", false);
                // CRLF를 true로 설정 시 줄바꿈, false 설정 시 줄바꿈X
            }
        });
        
        Button btnSend2 = findViewById(R.id.btnSend2); //데이터 전송
        btnSend2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.send("off", false);
                // CRLF를 true로 설정 시 줄바꿈, false 설정 시 줄바꿈X
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // super와 위에 @Override는 원래 없던 줄 추가한 것 --> 마지막 인수에 data가 맞나? intent가 맞나?
        // super 사용 시 Activity API의 다른 이벤트 함수와 더 일관성이 있으며 비용이 들지 않으며 (호출하는 코드는 현재 아무 작업도하지 않음) 기본 클래스가 변경됩니다.
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}


//참조: https://devsoboro.tistory.com/13 [CodeJUN]

// 아두이노 센서값 안드로이드에서 받기
//https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=tsniper_72&logNo=221261130141

//안드로이드 앱으로 led 제어
//https://webnautes.tistory.com/966

//시각장애지팡이 참고
// https://redbinalgorithm.tistory.com/289?category=928541