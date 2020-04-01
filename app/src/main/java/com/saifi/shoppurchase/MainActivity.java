package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    Button shopButton,managerButton,returnButton;
    RelativeLayout mainLaout;
    long back_pressed=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        managerButton = findViewById(R.id.managerButton);
        shopButton = findViewById(R.id.shopButton);
        returnButton = findViewById(R.id.returnButton);
        mainLaout = findViewById(R.id.mainLaout);

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ShopActivity.class));
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ManagerActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
        {
            Snackbar snackbar=Snackbar.make(mainLaout, "Double Tap to Exit!", Snackbar.LENGTH_SHORT);
            View view=snackbar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
