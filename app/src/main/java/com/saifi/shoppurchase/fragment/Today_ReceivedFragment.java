package com.saifi.shoppurchase.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saifi.shoppurchase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Today_ReceivedFragment extends Fragment {

    public Today_ReceivedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todayreceived, container, false);
    }
}
