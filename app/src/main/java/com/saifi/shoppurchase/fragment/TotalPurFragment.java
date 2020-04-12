package com.saifi.shoppurchase.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.adapter.TotalAdapter;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.model.TotalModel;
import com.saifi.shoppurchase.retrofitmodel.StatusModel;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseDatum;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseStatusModel;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.Views;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TotalPurFragment extends Fragment {

    public TotalPurFragment() {
        // Required empty public constructor
    }

    View view;
    TotalAdapter totalAdapter;
    RecyclerView rvTotalPur;
    ArrayList<PurchaseDatum> listData;
    SessonManager sessonManager;
    Views views;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_total_pur, container, false);
        rvTotalPur = view.findViewById(R.id.rvTotalPur);
        sessonManager = new SessonManager(getActivity());
        views = new Views();

        hitApi();
        return view;
    }

    private void hitApi() {
        views.showProgress(getActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<PurchaseStatusModel> call = api.hitPurchaseListApi(Url.key, sessonManager.getToken(), sessonManager.getBuisnessLocationId());

        call.enqueue(new Callback<PurchaseStatusModel>() {
            @Override
            public void onResponse(Call<PurchaseStatusModel> call, Response<PurchaseStatusModel> response) {
                views.hideProgress();

                if (response.isSuccessful()) {
                    PurchaseStatusModel model = response.body();
                    listData = model.getData();
                    Log.d("asffsff", String.valueOf(listData.size()));
                    setRv();
                    totalAdapter.notifyDataSetChanged();
                } else {
                    views.showToast(getActivity(), String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<PurchaseStatusModel> call, Throwable t) {
                views.hideProgress();
                views.showToast(getActivity(), t.getMessage());
            }
        });
    }

    private void setRv() {

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rvTotalPur.setLayoutManager(layoutManager);
        totalAdapter = new TotalAdapter(getContext(), listData);
        rvTotalPur.setAdapter(totalAdapter);
    }
}
