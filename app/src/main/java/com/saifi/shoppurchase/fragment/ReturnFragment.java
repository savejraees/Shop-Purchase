package com.saifi.shoppurchase.fragment;

import android.app.AppComponentFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.ReturnActivity;
import com.saifi.shoppurchase.util.NoScanResultException;
import com.saifi.shoppurchase.util.ScanFragment;
import com.saifi.shoppurchase.util.ScanResultReceiver;


public class ReturnFragment extends Fragment {

    public ReturnFragment() {
        // Required empty public constructor
    }

    View view;

    ImageView scanReturn;
    EditText edt_imeiReturn, editTextPayment, editTextExchangeReturn;
    RadioGroup radioPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_return, container, false);
        init();
        click();
        return view;
    }

    private void click() {
        scanReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScanFragment scanFragment = new ScanFragment();
                fragmentTransaction.add(R.id.frameLayoutRequest, scanFragment);
                fragmentTransaction.commit();


            }
        });
        radioPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedButton = radioPayment.findViewById(i);
                boolean checked = checkedButton.isChecked();
                if(checked){
                    if(checkedButton.getText().toString().equals("Payment")) {
                        editTextExchangeReturn.setVisibility(View.GONE);
                        editTextPayment.setVisibility(View.VISIBLE);
                    }
                    if(checkedButton.getText().toString().equals("Exchange")) {
                        editTextExchangeReturn.setVisibility(View.VISIBLE);
                        editTextPayment.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    private void init() {
        scanReturn = view.findViewById(R.id.scanReturn);
        edt_imeiReturn = view.findViewById(R.id.edt_imeiReturn);
        editTextExchangeReturn = view.findViewById(R.id.editTextExchangeReturn);
        editTextPayment = view.findViewById(R.id.editTextPayment);
        radioPayment = view.findViewById(R.id.radioPayment);

    }

    @Override
    public void onResume() {
        super.onResume();
        edt_imeiReturn.setText(((ReturnActivity) getActivity()).barcode);
    }
}
