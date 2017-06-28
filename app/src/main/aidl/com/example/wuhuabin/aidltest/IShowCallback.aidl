// IShowCallback.aidl
package com.example.wuhuabin.aidltest;
import com.example.wuhuabin.aidltest.Message;
// Declare any non-default types here with import statements

interface IShowCallback {

    void showImage(inout Message message);

}
