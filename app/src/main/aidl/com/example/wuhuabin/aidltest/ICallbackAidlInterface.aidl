// ICallbackAidlInterface.aidl
package com.example.wuhuabin.aidltest;
import com.example.wuhuabin.aidltest.IShowCallback;

// Declare any non-default types here with import statements

interface ICallbackAidlInterface {
    void getBitmap();
    void regist(IShowCallback callback);
    void unRegist(IShowCallback callback);
}
