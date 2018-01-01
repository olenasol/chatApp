package com.example.olena.chatapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.adapters.MessageListAdapter;
import com.example.olena.chatapp.adapters.UserListAdapter;
import com.example.olena.chatapp.dataproviders.MessageProvider;
import com.example.olena.chatapp.dataproviders.UserProvider;
import com.example.olena.chatapp.models.Message;
import com.example.olena.chatapp.models.User;
import com.example.olena.chatapp.utils.Constants;

import java.util.ArrayList;


public class MessageLogFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView messageEdit;
    private User user;
    private  ArrayList<Message> listOfMessages;
    private UserListFragment userListFragment;


    public static MessageLogFragment newInstance(User u,UserListFragment userListFragment) {
        MessageLogFragment myFragment = new MessageLogFragment();
        myFragment.user = u;
        myFragment.userListFragment = userListFragment;
        return myFragment;
    }

    public User getUser() {
        return user;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_log, container, false);
        recyclerView = view.findViewById(R.id.messageList);
        messageEdit = view.findViewById(R.id.messageEdit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(Constants.USER_KEY) != null) {
                user = savedInstanceState.getParcelable(Constants.USER_KEY);
            }
        }
        listOfMessages = user.getListOfMessages();
        if (listOfMessages == null) {
            listOfMessages = new ArrayList<>();
        }
        recyclerView.setAdapter(new MessageListAdapter(listOfMessages));
        recyclerView.getLayoutManager().scrollToPosition(listOfMessages.size() - 1);
        messageEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundColor(Color.RED);
                }
            }
        });

        messageEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!((EditText) v).getText().equals("")) {
                        Message message = new Message(((EditText) v).getText().toString(), true);
                        //((MessageListAdapter) recyclerView.getAdapter()).addToListOfMessages(message);
                        listOfMessages.add(message);
                        user.setListOfMessages(listOfMessages);
                        userListFragment.changeListOfUsers(user);
                        recyclerView.getLayoutManager().scrollToPosition(((MessageListAdapter) recyclerView
                                .getAdapter()).getListOfMessages().size() - 1);
                        ((EditText) v).setText("");
                    }
                    return true;
                }
                return false;
            }
        });
        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(Constants.USER_KEY, user);

    }

}
