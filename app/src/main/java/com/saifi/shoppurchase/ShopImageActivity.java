package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saifi.shoppurchase.util.NoScanResultException;
import com.saifi.shoppurchase.util.ScanFragment;
import com.saifi.shoppurchase.util.ScanResultReceiver;

public class ShopImageActivity extends AppCompatActivity implements ScanResultReceiver {

    String phoneId="";
    Button btnBarCodeScan;
    TextView txtBarcodeId;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_image);

        phoneId = getIntent().getStringExtra("phoneId");
        Log.d("kjhjkjjk",phoneId);
        btnBarCodeScan = findViewById(R.id.btnBarCodeScan);
        txtBarcodeId = findViewById(R.id.txtBArcodeId);
        linearLayout = findViewById(R.id.linearLayout);

        btnBarCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScanFragment scanFragment = new ScanFragment();
                fragmentTransaction.add(R.id.linearLayout, scanFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void scanResultData(String codeFormat, String codeContent) {
        txtBarcodeId.setText(codeContent);
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {

    }
}
