package com.example.wuhuabin.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.wuhuabin.aidltest.ICallbackAidlInterface;
import com.example.wuhuabin.aidltest.IShowCallback;
import com.example.wuhuabin.aidltest.Message;
import com.example.wuhuabin.aidltest.R;

public class AidlCallbackService extends Service {

    private static final String TAG = "AidlCallbackService";

    private RemoteCallbackList<IShowCallback> mList;

    public AidlCallbackService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mList = new RemoteCallbackList();
    }

    ICallbackAidlInterface.Stub mStub=new ICallbackAidlInterface.Stub() {
        @Override
        public void regist(IShowCallback callback) throws RemoteException {
            mList.register(callback);
        }

        @Override
        public void unRegist(IShowCallback callback) throws RemoteException {
            mList.unregister(callback);
        }

        @Override
        public void getBitmap() throws RemoteException {
            Log.d(TAG, "getBitmap: "+Thread.currentThread());
            try {
                Thread.sleep(7000);
                Message message=new Message();
                message.setName("1234");
                message.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                callback(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    public void callback(Message message){
        int len=mList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            Log.d(TAG, "callback: "+i+"开始");
            try {
                mList.getBroadcastItem(i).showImage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mList.finishBroadcast();
    }

}
