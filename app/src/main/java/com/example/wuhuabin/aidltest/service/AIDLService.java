package com.example.wuhuabin.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.wuhuabin.aidltest.IAidlInterface;
import com.example.wuhuabin.aidltest.Message;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AIDLService extends Service {

    private List<Message> mMessageList;

    public AIDLService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMessageList=new ArrayList<>();
        Message message=new Message();
        message.setId(1000);
        message.setName("zhangsan");
        mMessageList.add(message);
    }

    IAidlInterface.Stub mStub=new IAidlInterface.Stub() {
        @Override
        public void sendMessage(Message message) throws RemoteException {
            message.setId(3000);
            mMessageList.add(message);
            for (int i = 0; i < mMessageList.size(); i++) {
                Log.d(TAG, "sendMessage: "+mMessageList.get(i).getId()+mMessageList.get(i).getName());
            }
        }

        @Override
        public List<Message> getMessage() throws RemoteException {
            for (int i = 0; i < mMessageList.size(); i++) {
                Log.d(TAG, "sendMessage: "+mMessageList.get(i).getId()+mMessageList.get(i).getName());
            }
            return mMessageList;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }
}
