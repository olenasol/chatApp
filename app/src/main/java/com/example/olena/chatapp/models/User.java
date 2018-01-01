package com.example.olena.chatapp.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class User implements Parcelable,Comparable<User>{
    private String userName;
    private String userSurname;
    private ArrayList<Message> listOfMessages;

    public User(String userName, String userSurname) {
        this.userName = userName;
        this.userSurname = userSurname;
        listOfMessages = new ArrayList<>();
    }
    private User(Parcel in) {
        this.userName = in.readString();
        this.userSurname = in.readString();
        this.listOfMessages = in.readArrayList(null);


    }
    public void addMessageToList(Message message){
        listOfMessages.add(message);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public ArrayList<Message> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(ArrayList<Message> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userSurname);
        dest.writeList(this.listOfMessages);
    }
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int compareTo(@NonNull User o) {
        return (this.userSurname.compareTo(o.userSurname));
    }
}
