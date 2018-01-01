package com.example.olena.chatapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.olena.chatapp.R;

public class OtherMessageHolder extends RecyclerView.ViewHolder{
    TextView messageTxt;
    TextView timeTxt;

    OtherMessageHolder(View itemView) {
        super(itemView);
        messageTxt = itemView.findViewById(R.id.otherMessageText);
        timeTxt = itemView.findViewById(R.id.timeOtherMessage);

    }
}