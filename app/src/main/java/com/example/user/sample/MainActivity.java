package com.example.user.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onClick(View v) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.home_display);


        Button bt = (Button) findViewById(R.id.button6);
        bt.setOnClickListener(new View.OnClickListener() {
        });

        ImageView iv_droid = (ImageView) findViewById(R.id.iv_droid);

        switch (v.getId()){
            case R.id.button1:
                iv_droid.setImageResource(R.drawable.test_image);
                break;

            case R.id.button2:
                iv_droid.setImageResource(R.drawable.android_logo_red);
                break;

            case R.id.button3:
                iv_droid.setImageResource(R.drawable.android_logo_blue);
                break;
        }
    }

    Button bt_1 = (Button) findViewById(R.id.button1);
    bt_1.setOnClickListener(this);

    Button bt_2 = (Button) findViewById(R.id.button2);
    bt_2.setOnClickListener(this);

    Button bt_3 = (Button) findViewById(R.id.button3);
    bt_3.setOnClickListener(this);


}

