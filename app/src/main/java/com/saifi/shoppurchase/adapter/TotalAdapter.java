package com.saifi.shoppurchase.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;
import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.ShopActivity;
import com.saifi.shoppurchase.constants.Url;
import com.saifi.shoppurchase.model.TotalModel;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseDatum;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseWareHouseModel;
import com.saifi.shoppurchase.service.ApiInterface;
import com.saifi.shoppurchase.util.Views;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.TotalHolder> {

    Context context;
    ArrayList<PurchaseDatum> list;
    Views views = new Views();

    public TotalAdapter(Context context, ArrayList<PurchaseDatum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TotalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_total_pur, parent, false);
        return new TotalHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final TotalHolder holder, int position) {
        final PurchaseDatum totalModel = list.get(position);
        holder.txtBrand.setText(totalModel.getBrandName());
        holder.txtModel.setText(totalModel.getModelName());
        holder.txtGB.setText(totalModel.getGb());
        holder.txtPrice.setText("₹"+(totalModel.getPurchaseAmount())+"/-");
        holder.txtName.setText("Purchased By(" + totalModel.getName() + ")");
        holder.txtBarcode.setText("Barcode no : " + totalModel.getBarcodeScan() );


//        holder.wareHouseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final int phoneId = totalModel.getId();
//                final int position = holder.getAdapterPosition();
//
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.warehouse_dialog);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.setCancelable(false);
//                final EditText editTextCode = dialog.findViewById(R.id.editTextCode);
//                Button codeSubmitButton = dialog.findViewById(R.id.codeSubmitButton);
//                dialog.show();
//
//                codeSubmitButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (editTextCode.getText().toString().isEmpty()) {
//                            editTextCode.setError("Please enter code");
//                            editTextCode.requestFocus();
//                        } else {
//                            dialog.dismiss();
//                            hitApiWarehouse(phoneId, editTextCode.getText().toString(), position);
//                        }
//                    }
//                });
//
//            }
//        });
    }

//    private void hitApiWarehouse(int phoneId, String code, final int pos) {
//        views.showProgress(context);
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//
//        ApiInterface api = retrofit.create(ApiInterface.class);
//        Call<PurchaseWareHouseModel> call = api.hitWareHouseApi(Url.key, String.valueOf(phoneId), code);
//        call.enqueue(new Callback<PurchaseWareHouseModel>() {
//            @Override
//            public void onResponse(Call<PurchaseWareHouseModel> call, Response<PurchaseWareHouseModel> response) {
//                views.hideProgress();
//                if (response.isSuccessful()) {
//                    PurchaseWareHouseModel model = response.body();
//
//                    if(model.getCode().equals("200")){
//                        views.showToast(context, model.getMsg());
//                        removeItem(pos);
//                    }else {
//                        views.showToast(context, model.getMsg());
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PurchaseWareHouseModel> call, Throwable t) {
//                views.hideProgress();
//                views.showToast(context, t.getMessage());
//            }
//        });
//    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class TotalHolder extends RecyclerView.ViewHolder {

        Button wareHouseButton;
        TextView txtBrand, txtModel, txtGB, txtPrice, txtName,txtBarcode;
        ImageView imgPhone;

        public TotalHolder(@NonNull View itemView) {
            super(itemView);
            wareHouseButton = itemView.findViewById(R.id.wareHouseButton);
            wareHouseButton.setVisibility(View.GONE);
            txtBrand = itemView.findViewById(R.id.txtBrand);
            txtModel = itemView.findViewById(R.id.txtModel);
            txtGB = itemView.findViewById(R.id.txtGB);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtName = itemView.findViewById(R.id.txtName);
            imgPhone = itemView.findViewById(R.id.imgPhone);
            txtBarcode = itemView.findViewById(R.id.txtBarcode);
        }
    }
}
