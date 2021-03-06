package com.example.olena.chatapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.interfaces.UserItemClickListener;
import com.example.olena.chatapp.models.User;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserHolder> {

    private ArrayList<User> listOfUsers;
    private UserItemClickListener onClickListener;

    public UserListAdapter(ArrayList<User> listOfUsers, UserItemClickListener onClickListener) {
        this.listOfUsers = listOfUsers;
        this.onClickListener = onClickListener;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final UserHolder holder, int position) {
        String fullName = listOfUsers.get(position).getUserName() + " "
                + listOfUsers.get(position).getUserSurname();
        holder.getUserNameTxt().setText(fullName);
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onUserClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }
}
