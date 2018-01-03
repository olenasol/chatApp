package com.example.olena.chatapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.olena.chatapp.R;

class IncomingMessageHolder extends RecyclerView.ViewHolder {
    private TextView messageTxt;
    private TextView timeTxt;

    TextView getMessageTxt() {
        return messageTxt;
    }

    TextView getTimeTxt() {
        return timeTxt;
    }

    IncomingMessageHolder(View itemView) {
        super(itemView);
        messageTxt = itemView.findViewById(R.id.messageTextView);
        timeTxt = itemView.findViewById(R.id.timeTextView);
    }
}
