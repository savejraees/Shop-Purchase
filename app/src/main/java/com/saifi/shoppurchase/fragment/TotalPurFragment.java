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
import com.saifi.shoppurchase.adapter.TotalAdapter;
import com.saifi.shoppurchase.model.TotalModel;

import java.util.ArrayList;

public class TotalPurFragment extends Fragment {

    public TotalPurFragment() {
        // Required empty public constructor
    }

    View view;
    TotalAdapter totalAdapter;
    ArrayList<TotalModel> list = new ArrayList<>();
    RecyclerView rvTotalPur;
    int[] img = {R.drawable.phone_icon, R.drawable.phone_icon, R.drawable.phone_icon};
    String[] brand = {"Apple", "Samsung", "Lenovo"};
    String[] gb = {"64GB", "32GB", "16GB"};
    String[] model = {"11PRO", "A5", "L3"};
    String[] price = {"60000/-", "40000/-", "30000/-"};
    String[] name = {"Purchased By(Dheeraj)", "Purchased By(Mehta)", "Purchased By(Ruhul)"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_total_pur, container, false);
        rvTotalPur = view.findViewById(R.id.rvTotalPur);

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
        rvTotalPur.setLayoutManager(layoutManager);
        totalAdapter = new TotalAdapter(getContext(), list);
        rvTotalPur.setAdapter(totalAdapter);
    }
}
