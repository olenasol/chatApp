package com.example.olena.chatapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.utils.Utils;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton blueRadioBtn;
    private RadioButton greenRadioBtn;
    private RadioButton yellowRadioBtn;
    private RadioButton redRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        blueRadioBtn = findViewById(R.id.blueRadio);
        greenRadioBtn = findViewById(R.id.greenRadio);
        yellowRadioBtn = findViewById(R.id.yellowRadio);
        redRadioBtn = findViewById(R.id.redRadio);

        ImageButton goBackBtn = findViewById(R.id.goBackBtnSt);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        greenRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (greenRadioBtn.isChecked()) {
                    Utils.changeToTheme(Utils.THEME_GREEN);
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                }
            }
        });
        yellowRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yellowRadioBtn.isChecked()) {
                    Utils.changeToTheme(Utils.THEME_YELLOW);
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                }
            }
        });
        blueRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (blueRadioBtn.isChecked()) {
                    Utils.changeToTheme(Utils.THEME_DEFAULT);
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                }
            }
        });
        redRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (redRadioBtn.isChecked()) {
                    Utils.changeToTheme(Utils.THEME_RED);
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
