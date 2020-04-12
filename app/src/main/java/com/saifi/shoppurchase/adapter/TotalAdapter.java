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
import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.ShopActivity;
import com.saifi.shoppurchase.model.TotalModel;
import com.saifi.shoppurchase.retrofitmodel.manager.PurchaseDatum;

import java.util.ArrayList;

public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.TotalHolder> {

    Context context;
    ArrayList<PurchaseDatum> list;

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
    public void onBindViewHolder(@NonNull TotalHolder holder, int position) {
        PurchaseDatum totalModel = list.get(position);
        holder.txtBrand.setText(totalModel.getBrandName());
        holder.txtModel.setText(totalModel.getModelName());
        holder.txtGB.setText(totalModel.getGb());
        holder.txtPrice.setText(String.valueOf(totalModel.getPurchaseAmount()));
        holder.txtName.setText("Purchased By(" + totalModel.getCustomerName() + ")");


        holder.wareHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.warehouse_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                final EditText editTextCode = dialog.findViewById(R.id.editTextCode);
                Button codeSubmitButton = dialog.findViewById(R.id.codeSubmitButton);
                dialog.show();

                codeSubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(editTextCode.getText().toString().isEmpty()){
                            editTextCode.setError("Please enter code");
                            editTextCode.requestFocus();
                        }else{
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class TotalHolder extends RecyclerView.ViewHolder {

        Button wareHouseButton;
        TextView txtBrand, txtModel, txtGB, txtPrice, txtName;
        ImageView imgPhone;

        public TotalHolder(@NonNull View itemView) {
            super(itemView);
            wareHouseButton = itemView.findViewById(R.id.wareHouseButton);
            txtBrand = itemView.findViewById(R.id.txtBrand);
            txtModel = itemView.findViewById(R.id.txtModel);
            txtGB = itemView.findViewById(R.id.txtGB);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtName = itemView.findViewById(R.id.txtName);
            imgPhone = itemView.findViewById(R.id.imgPhone);
        }
    }
}
