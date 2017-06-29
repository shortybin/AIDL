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
import android.widget.ImageView;

import com.example.wuhuabin.aidltest.ICallbackAidlInterface;
import com.example.wuhuabin.aidltest.IShowCallback;
import com.example.wuhuabin.aidltest.Message;
import com.example.wuhuabin.aidltest.R;
import com.example.wuhuabin.aidltest.service.AidlCallbackService;

public class AIDLCallbackActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AIDLCallbackActivity";

    private Button mBind;
    private Button mSend;
    private ImageView mImageView;
    private boolean isBind;
    private ICallbackAidlInterface mICallbackAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlcallback);
        mBind= (Button) findViewById(R.id.bind_service);
        mBind.setOnClickListener(this);
        mSend= (Button) findViewById(R.id.send_event);
        mSend.setOnClickListener(this);
        mImageView= (ImageView) findViewById(R.id.bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bind_service:
                Intent intent=new Intent(AIDLCallbackActivity.this, AidlCallbackService.class);
                bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.send_event:
                if (isBind){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mICallbackAidlInterface.getBitmap();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
        }
    }

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mICallbackAidlInterface = ICallbackAidlInterface.Stub.asInterface(service);
            isBind=true;
            try {
                mICallbackAidlInterface.regist(mStub);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                mICallbackAidlInterface.unRegist(mStub);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isBind=false;
        }
    };

    IShowCallback.Stub mStub=new IShowCallback.Stub() {
        @Override
        public void showImage(final Message message) throws RemoteException {
            Log.d(TAG, "showImage: "+Thread.currentThread().getId());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "showImage: "+Thread.currentThread());
                    mImageView.setImageBitmap(message.getBitmap());
                    Log.d(TAG, "showImage: "+message.getName());
                }
            });

        }
    };

}
