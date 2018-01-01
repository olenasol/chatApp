package com.example.olena.chatapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Parcelable {
    private String message;
    private String time;
    private boolean fromMe;

    public Message(String message, boolean fromMe) {
        this.message = message;
        SimpleDateFormat sdf=new SimpleDateFormat("HH : mm : ss");
        this.time = sdf.format(new Date());
        this.fromMe = fromMe;
    }
    private Message(Parcel in) {
        this.message = in.readString();
        this.time = in.readString();
        this.fromMe = in.readByte() != 0;


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.time);
        dest.writeByte((byte) (this.fromMe ? 1 : 0));
    }
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
