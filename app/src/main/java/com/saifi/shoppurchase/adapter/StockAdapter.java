package com.saifi.shoppurchase.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.saifi.shoppurchase.ManagerActivity;
import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.fragment.StockFragment;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockDatum;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockMatch;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.ScanFragment;
import com.saifi.shoppurchase.util.Views;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.saifi.shoppurchase.fragment.StockFragment.scanValue;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.LatestViewHolder> {


    Context context;
    private ArrayList<StockDatum> list;
    Views views = new Views();

    public StockAdapter(Context context, ArrayList<StockDatum> list) {
        super();
        this.context = context;
        this.list = list;

    }



    @Override
    public LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_stock,parent,false);

        return new LatestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final LatestViewHolder holder, int position) {
        final StockDatum totalModel = list.get(position);
        holder.txtBrandAllQC.setText(totalModel.getBrandName());
        holder.txtModelAll.setText(totalModel.getModelName());
        holder.txtGBAll.setText(totalModel.getGb());
//        holder.txtPriceAll.setText("₹"+(totalModel.getPurchaseAmount())+"/-");
        holder.txtNameAll.setText("Purchased By(" + totalModel.getName() + ")");
        holder.txtBarcodeAll.setText("Barcode no : " + totalModel.getBarcodeScan() );
        holder.txtCategoryAll.setText("Purchase Category : " + totalModel.getPurchaseCatName() );

        if(totalModel.getStockCheckStatus().equals("1")){
            holder.scan.setBackgroundColor(Color.parseColor("#5F061D"));
        }

        holder.scan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                final int phoneId = totalModel.getId();
                final int position = holder.getAdapterPosition();
                final String barcode = totalModel.getBarcodeScan();

                Log.d("lksafsfa", String.valueOf(phoneId)+" "+totalModel.getBrandName()+" "+totalModel.getModelName());


                if(scanValue==1){
                    FragmentManager fragmentManager = ((ManagerActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ScanFragment scanFragment = new ScanFragment();
                    fragmentTransaction.add(R.id.frameLayout, scanFragment);
                    fragmentTransaction.commit();
                }


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.warehouse_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                Button codeSubmitButton = dialog.findViewById(R.id.codeSubmitButton);
                Button cancelButton = dialog.findViewById(R.id.codecancelButton);
//                    final TextView txtBarcodeSubmit = dialog.findViewById(R.id.txtBarcodeSubmit);

                dialog.show();
                codeSubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                            txtBarcodeSubmit.setText(edtsearchAll.getText().toString());

                        if(((ManagerActivity)context).barcode.equals(barcode)){
                            dialog.dismiss();
                            hitMatchStock(phoneId, position);
                        }else {
                            Toast.makeText(context, "Barcode does not Match", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentManager fragmentManager = ((ManagerActivity)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        StockFragment scanFragment = new StockFragment();
                        fragmentTransaction.replace(R.id.frameLayout, scanFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        dialog.dismiss();
                    }
                });

            }
        });

    }
    private void hitMatchStock(int phoneId, final int pos) {
        views.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<StockMatch> call = api.hitMatchStock(Url.key, String.valueOf(phoneId));
        call.enqueue(new Callback<StockMatch>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<StockMatch> call, Response<StockMatch> response) {
                views.hideProgress();
                if (response.isSuccessful()) {
                    StockMatch model = response.body();

                    if(model.getCode().equals("200")){
                        views.showToast(context, model.getMsg());

                        FragmentManager fragmentManager = ((ManagerActivity)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        StockFragment scanFragment = new StockFragment();
                        fragmentTransaction.replace(R.id.frameLayout, scanFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
//                         removeItem(pos);
                    }else {
                        views.showToast(context, model.getMsg());
                    }

                }
            }

            @Override
            public void onFailure(Call<StockMatch> call, Throwable t) {
//                views.hideProgress();
                views.showToast(context, t.getMessage());
            }
        });
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class LatestViewHolder extends RecyclerView.ViewHolder{

        Button scan;
        TextView txtBrandAllQC, txtModelAll, txtGBAll, txtNameAll,txtBarcodeAll,txtCategoryAll;
        public LatestViewHolder(@NonNull final View itemView) {
            super(itemView);
            scan = itemView.findViewById(R.id.submitButtonAllQC);
            txtBrandAllQC = itemView.findViewById(R.id.txtBrandAllQC);
            txtModelAll = itemView.findViewById(R.id.txtModelAllQC);
            txtGBAll = itemView.findViewById(R.id.txtGBAllQC);
//            txtPriceAll = itemView.findViewById(R.id.txtPriceAll);
            txtNameAll = itemView.findViewById(R.id.txtNameAllQC);
            txtBarcodeAll = itemView.findViewById(R.id.txtBarcodeAllQC);
            txtCategoryAll = itemView.findViewById(R.id.txtCategoryAllQC);



        }
    }

}
