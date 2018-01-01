package com.example.olena.chatapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.olena.chatapp.R;

public class IncommingMessageHolder extends RecyclerView.ViewHolder{
    TextView messageTxt;
    TextView timeTxt;

    IncommingMessageHolder(View itemView) {
        super(itemView);
        messageTxt = itemView.findViewById(R.id.messageTextView);
        timeTxt = itemView.findViewById(R.id.timeTextView);

    }
}
