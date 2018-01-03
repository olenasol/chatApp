package com.example.olena.chatapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.models.Message;
import com.example.olena.chatapp.utils.Constants;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Message> listOfMessages;

    public MessageListAdapter(ArrayList<Message> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

    public void addMessageToList(Message message) {
        listOfMessages.add(message);
        notifyDataSetChanged();
    }

    public ArrayList<Message> getListOfMessages() {
        return listOfMessages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType != Constants.VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message, parent, false);
            vh = new IncomingMessageHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.other_message, parent, false);

            vh = new OtherMessageHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IncomingMessageHolder) {
            ((IncomingMessageHolder) holder).getMessageTxt().setText(listOfMessages.get(position).getMessage());
            ((IncomingMessageHolder) holder).getTimeTxt().setText(listOfMessages.get(position).getTime());

        } else {
            ((OtherMessageHolder) holder).getMessageTxt().setText(listOfMessages.get(position).getMessage());
            ((OtherMessageHolder) holder).getTimeTxt().setText(listOfMessages.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return listOfMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_PROG = 0;
        return listOfMessages.get(position).isFromMe() ? Constants.VIEW_ITEM : VIEW_PROG;
    }

}
