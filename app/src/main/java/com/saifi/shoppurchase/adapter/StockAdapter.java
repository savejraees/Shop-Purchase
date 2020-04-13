package com.saifi.shoppurchase.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saifi.shoppurchase.R;
import com.saifi.shoppurchase.retrofitmodel.managerStock.StockDatum;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.LatestViewHolder> implements Filterable {


    Context context;

    private List<StockDatum> nameList;
    private List<StockDatum> filteredNameList;


    public StockAdapter(Context context, ArrayList<StockDatum> nameList) {
        super();
        this.context = context;
        this.nameList = nameList;
        this.filteredNameList = nameList;
    }



    @Override
    public LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_stock,parent,false);

        return new LatestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LatestViewHolder holder, int position) {

        holder.txtImeiStock.setText(filteredNameList.get(position).getImeiNo());
        holder.txtBrandStock.setText(filteredNameList.get(position).getBrandName());
        holder.txtModelStock.setText(filteredNameList.get(position).getModelName());
        holder.txtGBStock.setText(filteredNameList.get(position).getGb());
        holder.txtPriceStock.setText(String.valueOf(filteredNameList.get(position).getPurchaseAmount()));

        Log.d("silmo", String.valueOf(filteredNameList.get(position).getBrandName()));
    }


    @Override
    public int getItemCount() {

        return filteredNameList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredNameList = nameList;
                } else {
                    List<StockDatum> filteredList = new ArrayList<>();
                    for (StockDatum name : nameList) {
                        if (name.getImeiNo().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(name);
                        }
                        filteredNameList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredNameList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNameList = (List<StockDatum>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class LatestViewHolder extends RecyclerView.ViewHolder{

        TextView txtBrandStock,txtModelStock,txtGBStock,txtPriceStock,txtImeiStock;
        CardView cardStock;
        public LatestViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtBrandStock = itemView.findViewById(R.id.txtBrandStock);
            txtModelStock = itemView.findViewById(R.id.txtModelStock);
            txtGBStock = itemView.findViewById(R.id.txtGBStock);
            txtPriceStock = itemView.findViewById(R.id.txtPriceStock);
            txtImeiStock = itemView.findViewById(R.id.txtImeiStock);
            cardStock = itemView.findViewById(R.id.cardStock);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        StockDatum clickedDataItem = filteredNameList.get(pos);
                        int id = clickedDataItem.getId();

                        Log.d("asfklsa",""+id);
                        cardStock.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    }
                }
            });
        }
    }

}
