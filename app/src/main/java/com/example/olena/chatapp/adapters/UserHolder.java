package com.example.olena.chatapp.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.olena.chatapp.R;

class UserHolder extends RecyclerView.ViewHolder {

    private TextView userNameTxt;
    private CardView cardView;

    TextView getUserNameTxt() {
        return userNameTxt;
    }


    CardView getCardView() {
        return cardView;
    }


    UserHolder(View itemView) {
        super(itemView);
        userNameTxt = itemView.findViewById(R.id.userName);
        cardView = itemView.findViewById(R.id.card_user);

    }
}
