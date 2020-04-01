package com.saifi.shoppurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

public class ShopActivity extends AppCompatActivity {

    RadioGroup radioShop,radioWarranty;
    CheckBox chkExchange;
    EditText editTextExchange;
    TextInputLayout textInputExchange;
    LinearLayout layoutAccesother,layoutMobTab;
    Spinner Warranty_spinner;
    Button finalSubmitButton;
    ImageView imgBacktoMAin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().hide();

        init();
        click();

    }

    private void click() {
        radioShop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);
                boolean checked = checkedRadioButton.isChecked();
                if(checked){
                    if(checkedRadioButton.getText().toString().equals("Mobile")){
                        layoutMobTab.setVisibility(View.VISIBLE);
                        layoutAccesother.setVisibility(View.GONE);
                    }
                    if(checkedRadioButton.getText().toString().equals("Tablet")){
                        layoutMobTab.setVisibility(View.VISIBLE);
                        layoutAccesother.setVisibility(View.GONE);
                    }
                    if(checkedRadioButton.getText().toString().equals("Accessories")){
                        layoutMobTab.setVisibility(View.GONE);
                        layoutAccesother.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        radioWarranty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);
                boolean checked = checkedRadioButton.isChecked();
                if(checked){
                    if(checkedRadioButton.getText().toString().equals("In")){
                        Warranty_spinner.setVisibility(View.VISIBLE);

                    }
                    if(checkedRadioButton.getText().toString().equals("Out")){
                        Warranty_spinner.setVisibility(View.GONE);

                    }

                }
            }
        });

        chkExchange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                b = chkExchange.isChecked();
                if(b){
                    textInputExchange.setVisibility(View.VISIBLE);
                }else {
                    textInputExchange.setVisibility(View.GONE);
                }

            }
        });
        imgBacktoMAin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finishAffinity();
            }
        });

        finalSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ShopImageActivity.class));
            }
        });



    }

    private void init() {
        radioShop = findViewById(R.id.radioShop);
        radioWarranty = findViewById(R.id.radioWarranty);
        layoutAccesother = findViewById(R.id.layoutAccesother);
        layoutMobTab = findViewById(R.id.layoutMobTab);
        Warranty_spinner = findViewById(R.id.Warranty_spinner);
        finalSubmitButton = findViewById(R.id.finalSubmitButton);
        chkExchange = findViewById(R.id.chkExchange);
        textInputExchange = findViewById(R.id.textInputExchange);
        editTextExchange = findViewById(R.id.editTextExchange);
        imgBacktoMAin = findViewById(R.id.imgBacktoMAin);

    }
}
