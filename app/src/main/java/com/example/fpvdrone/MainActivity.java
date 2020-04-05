package com.example.fpvdrone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private EditText mEditTextIP;
    private EditText mEditTextPort;
    private Button mButtonConnect;
    private Button mButtonStart;
    private TextView mConnectionStatus;

    private String ConnectedLabel = "Connected";
    private String NotConnectedLabel = "No Connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mEditTextIP = (EditText) findViewById(R.id.editText_ip);
        this.mEditTextPort = (EditText) findViewById(R.id.editText_port);
        this.mButtonConnect = (Button) findViewById(R.id.button_connect);
        this.mButtonStart = (Button) findViewById(R.id.button_start);
        this.mConnectionStatus = (TextView) findViewById(R.id.text_connection_status);

        //start button enable-disable FOR NOW I DO NOT USE IT
        /*if(mConnectionStatus.getText().equals("not connected")) {
            mButtonStart.setEnabled(false);
            mButtonStart.setBackgroundColor(Color.GRAY);
        }*/

        //next page intent
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });




    }

    public void nextPage(){
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }




    public void processFinish(boolean output) {
        if (output) {
            this.mConnectionStatus.setText(this.ConnectedLabel);
            this.mConnectionStatus.setTextColor(Color.GREEN);
        }
        else {
            this.mConnectionStatus.setText(this.NotConnectedLabel);
            this.mConnectionStatus.setTextColor(Color.RED);

        }
    }
}
