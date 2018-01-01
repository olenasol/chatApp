package com.example.olena.chatapp.dataproviders;

import com.example.olena.chatapp.models.Message;
import com.example.olena.chatapp.models.User;

import java.util.ArrayList;

public class MessageProvider {
    public ArrayList<Message> getListOfMessages(){
        ArrayList<Message> listOfMessages = new ArrayList<>();
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Cat",false));
        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));

        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));

        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));

        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));

        listOfMessages.add(new Message("Bye",false));
        listOfMessages.add(new Message("Hello",true));
        listOfMessages.add(new Message("Bye",false));

        return listOfMessages;
    }
}
