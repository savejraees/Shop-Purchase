package com.saifi.shoppurchase.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
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


@RequiresApi(api = Build.VERSION_CODES.M)
public class StockFragment extends Fragment implements RecyclerView.OnScrollChangeListener {

    public StockFragment() {
        // Required empty public constructor
    }

    View view;
    Views views;
    StockAdapter adapter;
    RecyclerView rvAll;
    LinearLayoutManager layoutManager;
    ArrayList<StockDatum> listData = new ArrayList<>();
    ArrayList<StockDatum> listData2 = new ArrayList<>();
    int currentPage = 1;
    int totalPage;
    SessonManager sessonManager;
    Call<StockStstusModel> call;
    EditText edtsearchAll;
    ImageView imgScanAll;
    TextView txtClear,txtTotalItem;
    static  public int totalItem=0;
    static public int scanValue=1;
    boolean scanAdapterFragment =false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stock, container, false);
        views = new Views();
        rvAll = view.findViewById(R.id.rvStock);
        edtsearchAll = view.findViewById(R.id.edtsearchStock);
        imgScanAll = view.findViewById(R.id.imgScanStock);
        txtClear = view.findViewById(R.id.txtClearStock);
        txtTotalItem = view.findViewById(R.id.txtTotalItem);
        layoutManager = new GridLayoutManager(getContext(), 1);
        rvAll.setLayoutManager(layoutManager);
        sessonManager = new SessonManager(getActivity());

        hitApi();

        rvAll.setOnScrollChangeListener(this);
        //initializing our adapter
        adapter = new StockAdapter(getActivity(), listData2);
        //Adding adapter to recyclerview
        rvAll.setAdapter(adapter);

        txtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StockFragment scanFragment = new StockFragment();
                fragmentTransaction.replace(R.id.frameLayout, scanFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        imgScanAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listData2.clear();
                scanAdapterFragment =true;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScanFragment scanFragment = new ScanFragment();
                fragmentTransaction.add(R.id.frameLayout, scanFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        if (isLastItemDisplaying(rvAll)) {
            if ((currentPage <= totalPage) && (listData2.size() > 1)) {
                hitApi();
            }

        }
    }

    private void hitApi() {
        views.showProgress(getActivity());
        listData.clear();
        //   listDataSearch.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Url.BASE_URL)
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);


        if(((ManagerActivity) getActivity()).barcode.equals("")||((ManagerActivity)getActivity()).barcode.isEmpty()){
            scanValue=1;
            call = api.hitAllStockApi(Url.key, String.valueOf(currentPage), sessonManager.getBuisnessLocationId());
        }else
        {
            scanAdapterFragment =false;
            currentPage =1;
            scanValue=0;
            call = api.hitAllStockSearchApi(Url.key, String.valueOf(currentPage), sessonManager.getBuisnessLocationId()
                    ,((ManagerActivity) getActivity()).barcode);
        }

        call.enqueue(new Callback<StockStstusModel>() {
            @Override
            public void onResponse(Call<StockStstusModel> call, Response<StockStstusModel> response) {
                views.hideProgress();

                if (response.isSuccessful()) {
                    StockStstusModel model = response.body();
                    totalPage = model.getTotalPages();
                    totalItem = model.getTotalItem();
                    txtTotalItem.setText("Total Item ="+totalItem);


                    listData = model.getData();
                    listData2.addAll(listData);
                    adapter.notifyDataSetChanged();

                    currentPage = currentPage + 1;


                } else {
                    views.showToast(getActivity(), String.valueOf(response));
                }
            }

            @Override
            public void onFailure(Call<StockStstusModel> call, Throwable t) {
                views.hideProgress();
                views.showToast(getActivity(), t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //  sessonManager.setBarcode();
        edtsearchAll.setText(((ManagerActivity) getActivity()).barcode);

        if(edtsearchAll.getText().toString().equals("")){
        }else {
            listData2.clear();

            if(scanAdapterFragment==true){
                hitApi();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        ((ManagerActivity) getActivity()).barcode="";
    }
}
