package com.example.olena.chatapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.additional_classes.Utils;

public class AboutActivity extends AppCompatActivity {

    private TextView aboutDescription;
    private ImageButton goBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutDescription = findViewById(R.id.descriptionTxt);
        aboutDescription.setText(getDescription());
        goBackBtn = findViewById(R.id.goBackBtnAb);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private String getDescription(){
        return " But with so many apps out there it can be difficult to know exactly which one is " +
                "best for you and your friends. To help you make that choice, we've waded through " +
                "the densely populated mass of chat apps to pick out the best.";
    }
}
