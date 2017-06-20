package com.example.wuhuabin.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BinderService extends Service {

    private IBinder mIBinder=new MyBinder();

    public BinderService() {

    }

    public class MyBinder extends Binder{
        BinderService getServer(){
            return BinderService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public int count(int a,int b){
        return a+b;
    }

}
