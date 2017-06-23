package com.example.wuhuabin.aidltest.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wuhuabin.aidltest.R;
import com.example.wuhuabin.aidltest.service.MessengerService;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    private Button mBindService,mSendEvent;
    private ImageView mImageView;
    private boolean isBind;
    private Messenger mService;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Log.d(TAG, "handleMessage: SUM"+msg.arg1);
                Bundle bundle=msg.getData();
                Bitmap bitmap=bundle.getParcelable("bitmap");
                mImageView.setImageBitmap(bitmap);
            }
        }
    };
    private Messenger mMessenger=new Messenger(mHandler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        mBindService= (Button) findViewById(R.id.bind_service);
        mBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MessengerActivity.this, MessengerService.class);
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        isBind=true;
                        mService=new Messenger(service);
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
                        Message message=Message.obtain(null,1,5,5);
                        message.replyTo=mMessenger;
                        mService.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mImageView= (ImageView) findViewById(R.id.bitmap);

    }
}
