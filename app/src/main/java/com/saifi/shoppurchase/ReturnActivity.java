package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.saifi.shoppurchase.fragment.RequestedFragment;
import com.saifi.shoppurchase.fragment.ReturnFragment;
import com.saifi.shoppurchase.fragment.StockFragment;
import com.saifi.shoppurchase.fragment.TodayReturnFragment;
import com.saifi.shoppurchase.fragment.Today_ReceivedFragment;
import com.saifi.shoppurchase.fragment.TotalPurFragment;
import com.saifi.shoppurchase.util.NoScanResultException;
import com.saifi.shoppurchase.util.ScanResultReceiver;

public class ReturnActivity extends AppCompatActivity implements ScanResultReceiver {

    TabLayout tabLayoutReturn;
    FrameLayout frameLayoutRequest;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public String barcode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        getSupportActionBar().hide();

        tabLayoutReturn = findViewById(R.id.tabLayoutReturn);
        frameLayoutRequest = findViewById(R.id.frameLayoutRequest);

        fragment = new ReturnFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutRequest, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        tabLayoutReturn.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ReturnFragment();
                        break;
                    case 1:
                        fragment = new TodayReturnFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayoutRequest, fragment);
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
    }
    @Override
    public void scanResultData(String codeFormat, String codeContent) {
        barcode = codeContent;
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {

    }
}
