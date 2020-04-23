package com.saifi.shoppurchase.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.saifi.shoppurchase.ManagerActivity;
import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.constants.SessonManager;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.retrofitmodel.managerReceived.ReceivedDatum;
import com.saifi.shoppurchase.retrofitmodel.managerReceived.ReceivedStatusModel;
import com.saifi.shoppurchase.retrofitmodel.managerReceived.SubmitToStockModel;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.ScanFragment;
import com.saifi.shoppurchase.util.Views;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Today_ReceivedFragment extends Fragment {

    public Today_ReceivedFragment() {
        // Required empty public constructor
    }

    RecyclerView rv_receive;
    View view;
    SessonManager sessonManager;
    Views views;
    ReceivedAdapter receivedAdapter;
    int phoneId;
    String barcodeReceived;
    ArrayList<ReceivedDatum> listReceived;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_todayreceived, container, false);
        rv_receive = view.findViewById(R.id.rv_receive);
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
        Call<ReceivedStatusModel> call = api.hitReceivedListApi(Url.key, sessonManager.getToken(), sessonManager.getBuisnessLocationId());
        call.enqueue(new Callback<ReceivedStatusModel>() {
            @Override
            public void onResponse(Call<ReceivedStatusModel> call, Response<ReceivedStatusModel> response) {
                views.hideProgress();
                if (response.isSuccessful()) {
                    ReceivedStatusModel model = response.body();
                    if (model.getCode().equals("200")) {
                        listReceived = model.getData();
                        setRv(listReceived);
                        receivedAdapter.notifyDataSetChanged();
                    } else {
                        views.showToast(getActivity(), model.getMsg());
                    }

                }
            }

            @Override
            public void onFailure(Call<ReceivedStatusModel> call, Throwable t) {
                views.hideProgress();
            }
        });

    }

    private void setRv(ArrayList<ReceivedDatum> listReceived) {
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        rv_receive.setLayoutManager(layoutManager);
        receivedAdapter = new ReceivedAdapter(getContext(), listReceived);
        rv_receive.setAdapter(receivedAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((ManagerActivity) getActivity()).barcode.equals("")) {

        } else {
            Log.d("abcdef", ((ManagerActivity) getActivity()).barcode);
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_today_received);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            final TextView txtBarcodeToStock = dialog.findViewById(R.id.txtBarcodeToStock);
            txtBarcodeToStock.setText(((ManagerActivity) getActivity()).barcode);
            Button SubmitButtonReceived = dialog.findViewById(R.id.SubmitButtonReceived);
            dialog.show();

            SubmitButtonReceived.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txtBarcodeToStock.getText().toString().equals(barcodeReceived)) {
                        dialog.dismiss();
                        hitApiSubmitoManager(phoneId, ((ManagerActivity) getActivity()).barcode);
                    } else {
                        ((ManagerActivity) getActivity()).barcode = "";
                        Toast.makeText(getActivity(), "Barcode not match", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
        }

    }


    private void hitApiSubmitoManager(int phoneId, String code) {
        views.showProgress(getActivity());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<SubmitToStockModel> call = api.hitWareHousetoManagerApi(Url.key, String.valueOf(phoneId), code);
        call.enqueue(new Callback<SubmitToStockModel>() {
            @Override
            public void onResponse(Call<SubmitToStockModel> call, Response<SubmitToStockModel> response) {
                views.hideProgress();
                if (response.isSuccessful()) {
                    SubmitToStockModel model = response.body();

                    if (model.getCode().equals("200")) {
                        ((ManagerActivity) getActivity()).barcode = "";
                        hitApi();
                    } else {
                        views.showToast(getActivity(), model.getMsg());
                        ((ManagerActivity) getActivity()).barcode = "";
                    }

                }
            }

            @Override
            public void onFailure(Call<SubmitToStockModel> call, Throwable t) {
                views.hideProgress();
                views.showToast(getActivity(), t.getMessage());
            }
        });
    }


    public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedAdapter.TotalHolder> {

        Context context;
        ArrayList<ReceivedDatum>  listReceived;


        public ReceivedAdapter(Context context, ArrayList<ReceivedDatum> listReceived) {
            this.context = context;
            this.listReceived = listReceived;
        }

        @NonNull
        @Override
        public ReceivedAdapter.TotalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_todayreceived, parent, false);
            return new ReceivedAdapter.TotalHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull final ReceivedAdapter.TotalHolder holder, int position) {
            final ReceivedDatum totalModel = listReceived.get(position);
            holder.txtBrandReceived.setText(totalModel.getBrandName());
            holder.txtModelReceived.setText(totalModel.getModelName());
            holder.txtGBReceived.setText(totalModel.getGb());
            holder.txtPriceReceived.setText("₹" + (totalModel.getPurchaseAmount()) + "/-");
            holder.txtNameReceived.setText("Purchased By(" + totalModel.getName() + ")");
            holder.txtBarcodeReceived.setText("Barcode no : " + totalModel.getBarcodeScan());


            holder.receivedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phoneId = totalModel.getId();
                    barcodeReceived = totalModel.getBarcodeScan();

                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ScanFragment scanFragment = new ScanFragment();
                    fragmentTransaction.add(R.id.frameLayout, scanFragment);
                    fragmentTransaction.commit();


                }
            });
        }

        @Override
        public int getItemCount() {

            return listReceived.size();
        }

        public class TotalHolder extends RecyclerView.ViewHolder {

            Button receivedButton;
            TextView txtBrandReceived, txtModelReceived, txtGBReceived, txtPriceReceived, txtNameReceived, txtBarcodeReceived;


            public TotalHolder(@NonNull View itemView) {
                super(itemView);
                receivedButton = itemView.findViewById(R.id.receivedButton);
                txtBrandReceived = itemView.findViewById(R.id.txtBrandReceived);
                txtModelReceived = itemView.findViewById(R.id.txtModelReceived);
                txtGBReceived = itemView.findViewById(R.id.txtGBReceived);
                txtPriceReceived = itemView.findViewById(R.id.txtPriceReceived);
                txtNameReceived = itemView.findViewById(R.id.txtNameReceived);
                txtBarcodeReceived = itemView.findViewById(R.id.txtBarcodeReceived);
            }
        }
    }

}
