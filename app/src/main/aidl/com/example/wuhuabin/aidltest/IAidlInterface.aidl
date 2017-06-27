// IAidlInterface.aidl
package com.example.wuhuabin.aidltest;
import com.example.wuhuabin.aidltest.Message;

// Declare any non-default types here with import statements

interface IAidlInterface {

    void sendMessage(in Message message);

    List<Message> getMessage();

}
