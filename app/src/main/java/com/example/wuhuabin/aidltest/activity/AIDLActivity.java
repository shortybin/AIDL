package com.example.wuhuabin.aidltest.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuhuabin.aidltest.IAidlInterface;
import com.example.wuhuabin.aidltest.Message;
import com.example.wuhuabin.aidltest.R;
import com.example.wuhuabin.aidltest.service.AIDLService;

import java.util.List;

public class AIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDLActivity";

    private Button mBindService,mSendEvent;
    private boolean isBind;
    private IAidlInterface mIAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        mBindService= (Button) findViewById(R.id.bind_service);
        mBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AIDLActivity.this, AIDLService.class);
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        isBind=true;
                        mIAidlInterface = IAidlInterface.Stub.asInterface(service);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        isBind=false;
                    }
                },BIND_AUTO_CREATE);
            }
        });

        mSendEvent= (Button) findViewById(R.id.send_event);
        mSendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind){
                    try {
                        Message message=new Message();
                        message.setId(2000);
                        message.setName("lisi");
                        mIAidlInterface.sendMessage(message);
                        //mImageView.setImageBitmap(bitmap);

                        List<Message> message1 = mIAidlInterface.getMessage();
                        for (int i = 0; i < message1.size(); i++) {
                            Log.d(TAG, "onClick: "+message1.get(i).getId()+message1.get(i).getName());
                        }
                        //Log.d(TAG, "onServiceConnected: "+message.getName()+message.getId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
