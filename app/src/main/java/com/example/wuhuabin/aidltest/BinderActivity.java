package com.example.wuhuabin.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BinderActivity extends AppCompatActivity {

    private Button mBind,mCount;
    private boolean isBind;
    private BinderService mBinderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        mBind= (Button) findViewById(R.id.bind);
        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BinderActivity.this,BinderService.class);
                bindService(intent,serverConn,BIND_AUTO_CREATE);
            }
        });
        mCount= (Button) findViewById(R.id.count);
        mCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind){
                    int count = mBinderService.count(5, 5);
                    Toast.makeText(BinderActivity.this,"count="+count,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ServiceConnection serverConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind=true;
            BinderService.MyBinder myBinder=(BinderService.MyBinder) service;
            mBinderService=myBinder.getServer();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind=false;
        }
    };

}
