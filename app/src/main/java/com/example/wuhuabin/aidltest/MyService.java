package com.example.wuhuabin.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {

    }

    IMyAidlInterface.Stub bind= new IMyAidlInterface.Stub()
    {
        @Override
        public Message ShowMessage() throws RemoteException {
            Message message=new Message();
            message.setId(1);
            message.setName("li");
            return message;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return bind;
    }
}
