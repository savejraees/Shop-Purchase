package com.saifi.shoppurchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.model.TotalModel;

import java.util.ArrayList;

public class RequestedAdapter extends RecyclerView.Adapter<RequestedAdapter.TotalHolder> {

    Context context;
    ArrayList<TotalModel> list;

    public RequestedAdapter(Context context, ArrayList<TotalModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RequestedAdapter.TotalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_total_pur,parent,false);
        return new RequestedAdapter.TotalHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RequestedAdapter.TotalHolder holder, int position) {
        TotalModel totalModel = list.get(position);
        holder.txtBrand.setText(totalModel.getBrand());
        holder.txtModel.setText(totalModel.getModel());
        holder.txtGB.setText(totalModel.getGb());
        holder.txtPrice.setText(totalModel.getPrice());
        holder.txtName.setText(totalModel.getName());
        Glide.with(context).load(totalModel.getImg()).into(holder.imgPhone);

        holder.wareHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TotalHolder extends RecyclerView.ViewHolder {

        Button wareHouseButton;
        TextView txtBrand,txtModel,txtGB,txtPrice,txtName;
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
