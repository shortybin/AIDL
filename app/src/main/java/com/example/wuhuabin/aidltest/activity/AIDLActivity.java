package com.example.wuhuabin.aidltest.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wuhuabin.aidltest.IAidlInterface;
import com.example.wuhuabin.aidltest.Message;
import com.example.wuhuabin.aidltest.R;
import com.example.wuhuabin.aidltest.service.AIDLService;

public class AIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDLActivity";

    private Button mBindService,mSendEvent;
    private ImageView mImageView;
    private boolean isBind;
    private IAidlInterface mIMyAidlInterface;

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
                        mIMyAidlInterface = IAidlInterface.Stub.asInterface(service);
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
                        Message message= mIMyAidlInterface.showMessage();
                        Bitmap bitmap=mIMyAidlInterface.showBitmap();
                        mImageView.setImageBitmap(bitmap);
                        Log.d(TAG, "onServiceConnected: "+message.getName()+message.getId());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mImageView= (ImageView) findViewById(R.id.bitmap);

    }
}
