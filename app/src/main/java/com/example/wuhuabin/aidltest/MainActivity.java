package com.example.wuhuabin.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button mBind,mShow,mBinderActivity;
    private IMyAidlInterface mInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind= (Button) findViewById(R.id.bind);
        mShow= (Button) findViewById(R.id.show);
        mBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyService.class);
                bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
            }
        });
        mShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Message message = mInterface.ShowMessage();
                    Log.d(TAG, "onClick: "+message.getId()+message.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        mBinderActivity= (Button) findViewById(R.id.binder_activity);
        mBinderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BinderActivity.class));
            }
        });
    }

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mInterface=IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
