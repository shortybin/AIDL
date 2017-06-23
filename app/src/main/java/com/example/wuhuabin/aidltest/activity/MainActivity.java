package com.example.wuhuabin.aidltest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.wuhuabin.aidltest.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button mBinderActivity,mMessengerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinderActivity= (Button) findViewById(R.id.binder_activity);
        mBinderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BinderActivity.class));
            }
        });

        mMessengerActivity= (Button) findViewById(R.id.messenger_activity);
        mMessengerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MessengerActivity.class));
            }
        });
    }
}
