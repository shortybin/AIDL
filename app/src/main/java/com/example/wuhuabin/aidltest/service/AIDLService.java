package com.example.wuhuabin.aidltest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AIDLService extends Service {
    public AIDLService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
