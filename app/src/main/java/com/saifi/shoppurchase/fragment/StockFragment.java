package com.saifi.shoppurchase.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.saifi.shoppurchase.ManagerActivity;
import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.ReturnActivity;
import com.saifi.shoppurchase.adapter.StockAdapter;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockDatum;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockStstusModel;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.ScanFragment;
import com.saifi.shoppurchase.util.Views;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StockFragment extends Fragment implements SearchView.OnQueryTextListener{

    public StockFragment() {
        // Required empty public constructor
    }

    View view;

    SearchView ImeiSearch;
    ImageView imgScan;
    Views views;
    SessonManager sessonManager;
    ArrayList<StockDatum> listStock;
    RecyclerView imeirecyclerview;
    StockAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stock, container, false);
        views = new Views();
        sessonManager = new SessonManager(getActivity());

        imeirecyclerview = view.findViewById(R.id.imeirecyclerview);
        ImeiSearch = view.findViewById(R.id.ImeiSearch);
        imgScan = view.findViewById(R.id.imgScan);

        hitImeiApi();
        ImeiSearch.setOnQueryTextListener(this);
        ImeiSearch.setQueryHint(Html.fromHtml("<font color = #ffffff>" +
                getResources().getString(R.string.imeihint) + "</font>"));

        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScanFragment scanFragment = new ScanFragment();
                fragmentTransaction.add(R.id.frameLayout, scanFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void hitImeiApi() {
        views.showProgress(getActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<StockStstusModel> call = api.hitStockListApi(Url.key,sessonManager.getToken(),sessonManager.getBuisnessLocationId());
        call.enqueue(new Callback<StockStstusModel>() {
            @Override
            public void onResponse(Call<StockStstusModel> call, Response<StockStstusModel> response) {
                views.hideProgress();
                if(response.isSuccessful()){
                    StockStstusModel stockStstusModel = response.body();
                    listStock = stockStstusModel.getData();
                    Log.d("silmo", String.valueOf(listStock));
                    setRv();
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<StockStstusModel> call, Throwable t) {
                views.hideProgress();
            }
        });
    }

    private void setRv() {
        imeirecyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        imeirecyclerview.setLayoutManager(layoutManager);
        adapter = new StockAdapter(getActivity(),listStock);
        imeirecyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(listStock.contains(s)){
            adapter.getFilter().filter(s);
        }

        else {
            Toast.makeText(getActivity(), "No Imei no. is Exist", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length()>=1) {
            adapter.getFilter().filter(s);
        }
        else{

    }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
         ImeiSearch.setQuery(((ManagerActivity) getActivity()).barcode, false);

    }
}
