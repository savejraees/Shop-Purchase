package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.fragment.RequestedFragment;
import com.saifi.shoppurchase.fragment.StockFragment;
import com.saifi.shoppurchase.fragment.Today_ReceivedFragment;
import com.saifi.shoppurchase.fragment.TotalPurFragment;
import com.saifi.shoppurchase.util.NoScanResultException;
import com.saifi.shoppurchase.util.ScanResultReceiver;

public class ManagerActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    ImageView imgBack;
    TextView txtNameManager,txtContactManager,txtLocationManager;

    SessonManager sessonManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        getSupportActionBar().hide();
        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.frameLayout);
        imgBack = findViewById(R.id.imgBack);
        txtNameManager = findViewById(R.id.txtNameManager);
        txtContactManager = findViewById(R.id.txtContactManager);
        txtLocationManager = findViewById(R.id.txtLocationManager);

        sessonManager = new SessonManager(ManagerActivity.this);
        txtNameManager.setText(sessonManager.getUserName());
        txtLocationManager.setText(sessonManager.getLocation());
        txtContactManager.setText("("+sessonManager.getMobile()+")");

        fragment = new TotalPurFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new TotalPurFragment();
                        break;
                    case 1:
                        fragment = new StockFragment();
                        break;
                    case 2:
                        fragment = new Today_ReceivedFragment();
                        break;
                    case 3:
                        fragment = new RequestedFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finishAffinity();
            }
        });
    }


}
