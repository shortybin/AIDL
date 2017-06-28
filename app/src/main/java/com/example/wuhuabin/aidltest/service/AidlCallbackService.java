package com.example.wuhuabin.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.bumptech.glide.Glide;
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
            getBitmap();
        }

        @Override
        public void unRegist(IShowCallback callback) throws RemoteException {
            mList.unregister(callback);
        }

        @Override
        public void getBitmap() throws RemoteException {
            Message message=new Message();
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            Glide.with(getApplicationContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size" +
                    "=b9999_10000&sec=1498648274110&di=10eec2162029316" +
                    "d403e16aab17fe02d&imgtype=0&src=http%3A%2F%2Fimg.mp.it" +
                    "c.cn%2Fupload%2F20160829%2Fc6504f3c0f514517be1fd09e5281fcd0_th.jpg");
            message.setBitmap(bitmap);
            callback(message);
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
