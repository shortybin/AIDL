package com.example.wuhuabin.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.wuhuabin.aidltest.R;

public class MessengerService extends Service {

    private Messenger mClient;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                try {
                    Message message=Message.obtain(msg);

                    int sum=msg.arg1+msg.arg2;
                    message.arg1=sum;

                    Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("bitmap",bitmap);
                    message.setData(bundle);

                    message.replyTo.send(message);
                }catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Messenger mMessenger=new Messenger(mHandler);

    public MessengerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
