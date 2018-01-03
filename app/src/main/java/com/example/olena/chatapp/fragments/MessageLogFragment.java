package com.example.olena.chatapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.adapters.MessageListAdapter;
import com.example.olena.chatapp.models.Message;
import com.example.olena.chatapp.models.User;
import com.example.olena.chatapp.utils.Constants;

import java.util.ArrayList;


public class MessageLogFragment extends Fragment {

    private RecyclerView recyclerView;
    private User user;
    private ArrayList<Message> listOfMessages;
    private UserListFragment userListFragment;


    public static MessageLogFragment newInstance(User u, UserListFragment userListFragment) {
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
        TextView messageEdit = view.findViewById(R.id.messageEdit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(Constants.USER_KEY) != null) {
                user = savedInstanceState.getParcelable(Constants.USER_KEY);
            }
            Fragment fragment = getFragmentManager().getFragment(savedInstanceState,
                    Constants.LIST_FRAGMENT);
            if (fragment instanceof UserListFragment) {
                userListFragment = (UserListFragment) fragment;

            }
        }
        setNameTitle(view);
        listOfMessages = user.getListOfMessages();
        if (listOfMessages == null) {
            listOfMessages = new ArrayList<>();
        }
        recyclerView.setAdapter(new MessageListAdapter(listOfMessages));
        recyclerView.getLayoutManager().scrollToPosition(listOfMessages.size() - 1);

        messageEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (!((EditText) v).getText().toString().equals("")) {
                        Message message = new Message(((EditText) v).getText().toString(), true);
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

    private void setNameTitle(View view) {
        if (user != null) {
            TextView userTxt = view.findViewById(R.id.userNameTxt);
            String userName = user.getUserName() + user.getUserSurname();
            userTxt.setText(userName);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(Constants.USER_KEY, user);
        getFragmentManager().putFragment(outState, Constants.LIST_FRAGMENT,
                userListFragment);
    }

}
