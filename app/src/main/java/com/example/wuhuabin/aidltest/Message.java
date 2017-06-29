package com.example.wuhuabin.aidltest;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuhuabin on 2017/6/15.
 */

public class Message implements Parcelable {
    private int id;
    private String name;
    private Bitmap mBitmap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.mBitmap, flags);
    }

    public void readFromParcel(Parcel dest){
        name=dest.readString();
        id=dest.readInt();
        mBitmap=dest.readParcelable(Bitmap.class.getClassLoader());
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
