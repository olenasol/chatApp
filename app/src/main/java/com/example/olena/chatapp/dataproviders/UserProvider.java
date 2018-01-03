package com.example.olena.chatapp.dataproviders;

import com.example.olena.chatapp.models.User;

import java.util.ArrayList;

public class UserProvider {
    public ArrayList<User> getListOfUsers() {
        ArrayList<User> listOfUsers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listOfUsers.add(new User("USER", "#" + (i + 1)));
        }
        return listOfUsers;
    }
}
