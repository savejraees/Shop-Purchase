package com.saifi.shoppurchase.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.adapter.RequestedAdapter;
import com.saifi.shoppurchase.adapter.TotalAdapter;
import com.saifi.shoppurchase.model.TotalModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestedFragment extends Fragment {

    public RequestedFragment() {
        // Required empty public constructor
    }


    View view;
    RequestedAdapter requestedAdapter;
    ArrayList<TotalModel> list = new ArrayList<>();
    RecyclerView rvRequested;
    int[] img = {R.drawable.phone_icon, R.drawable.phone_icon, R.drawable.phone_icon};
    String[] brand = {"Apple", "Samsung", "Lenovo"};
    String[] gb = {"128GB", "64GB", "32GB"};
    String[] model = {"11PRO", "A5", "L3"};
    String[] price = {"90000/-", "30000/-", "50000/-"};
    String[] name = {"Purchased By(Anmol)", "Purchased By(Suraj)", "Purchased By(Raju)"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_requested, container, false);
        rvRequested = view.findViewById(R.id.rvRequested);

        setRv();

        return view;
    }

    private void setRv() {

        for (int i = 0; i < img.length; i++) {
            TotalModel totalModel = new TotalModel();
            totalModel.setImg(img[i]);
            totalModel.setBrand(brand[i]);
            totalModel.setGb(gb[i]);
            totalModel.setModel(model[i]);
            totalModel.setPrice(price[i]);
            totalModel.setName(name[i]);

            list.add(totalModel);
        }
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        rvRequested.setLayoutManager(layoutManager);
        requestedAdapter = new RequestedAdapter(getContext(), list);
        rvRequested.setAdapter(requestedAdapter);
    }
}
