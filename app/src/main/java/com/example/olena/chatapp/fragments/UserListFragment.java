package com.example.olena.chatapp.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.activities.ChatActivity;
import com.example.olena.chatapp.adapters.UserListAdapter;
import com.example.olena.chatapp.interfaces.UserItemClickListener;
import com.example.olena.chatapp.models.User;
import com.example.olena.chatapp.utils.Constants;

import java.util.ArrayList;


public class UserListFragment extends Fragment implements UserItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<User> listOfUsers;

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView = view.findViewById(R.id.userList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList(Constants.LIST_OF_USERS) != null) {
                listOfUsers = savedInstanceState.getParcelableArrayList(Constants.LIST_OF_USERS);
            }

        } else {
            listOfUsers = ((ChatActivity) getActivity()).getListOfUsers();

        }
        UserListAdapter userListAdapter = new UserListAdapter(listOfUsers, UserListFragment.this);
        recyclerView.setAdapter(userListAdapter);
        return view;
    }


    @Override
    public void onUserClicked(int position) {
        MessageLogFragment messageLogFragment = MessageLogFragment.newInstance(listOfUsers.get(position), this);
        ((ChatActivity) getActivity()).setMessageLogFragment(messageLogFragment);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getFragmentManager().beginTransaction().addToBackStack(null).hide(this).commit();

            ((ChatActivity) getActivity()).setMessageListVisible();
            ((ChatActivity) getActivity()).startListOfMessagesFragment(messageLogFragment);
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container2, messageLogFragment)
                    .commit();

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.LIST_OF_USERS, listOfUsers);

    }

    public void changeListOfUsers(User user) {
        if (listOfUsers != null) {
            int index = listOfUsers.indexOf(user);
            listOfUsers.remove(index);
            listOfUsers.add(0, user);

            //recyclerView.setAdapter(new UserListAdapter(listOfUsers,UserListFragment.this));
            recyclerView.getAdapter().notifyItemMoved(index, 0);
            //
            // recyclerView.getAdapter().notifyItemChanged(0);
            for (int i = 0; i <= index; i++) {
                recyclerView.getAdapter().notifyItemChanged(i);
            }

            recyclerView.getLayoutManager().scrollToPosition(0);
        }
    }
}
