package com.example.aps_true.ui.query.show.tab.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_true.R;

import java.util.ArrayList;

public class SaleAdapter extends RecyclerView.Adapter<com.example.aps_true.ui.query.show.tab.recyclerview.SaleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SaleItem> dataList;

    public SaleAdapter(Context context, ArrayList<SaleItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public com.example.aps_true.ui.query.show.tab.recyclerview.SaleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_sales, parent, false);
        return new com.example.aps_true.ui.query.show.tab.recyclerview.SaleAdapter.ViewHolder(view);
    }

    public void updateData(ArrayList<SaleItem> newData) {
        this.dataList = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialTextView, product_numberTextView,customer_number, specificationsTextView,
                quantityTextView, unitTextView, notshippedTextView, predeliverydateTextView,noteTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            serialTextView = itemView.findViewById(R.id.sales_serial_tv);
            product_numberTextView = itemView.findViewById(R.id.sales_product_number_tv);
            customer_number = itemView.findViewById(R.id.sales_customer_number_tv);
            specificationsTextView = itemView.findViewById(R.id.sales_specifications_tv);
            quantityTextView = itemView.findViewById(R.id.sales_quantity_tv);
            unitTextView = itemView.findViewById(R.id.sales_unit_tv);
            notshippedTextView = itemView.findViewById(R.id.sales_notshipped_tv);
            predeliverydateTextView = itemView.findViewById(R.id.sales_predate_tv);
            noteTextView = itemView.findViewById(R.id.sales_note_tv);
        }
    }

    @Override
    public void onBindViewHolder(com.example.aps_true.ui.query.show.tab.recyclerview.SaleAdapter.ViewHolder holder, int position) {
        SaleItem item = dataList.get(position);
        holder.serialTextView.setText(String.valueOf(item.getSerial()));
        holder.product_numberTextView.setText(String.valueOf(item.getProduct_number()));
        holder.customer_number.setText(String.valueOf(item.getCustomer_number()));
        holder.specificationsTextView.setText(String.valueOf(item.getSpecifications()));
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
        holder.unitTextView.setText(String.valueOf(item.getUnit()));
        holder.notshippedTextView.setText(String.valueOf(item.getNotshipped()));
        holder.predeliverydateTextView.setText(String.valueOf(item.getPredeliverydate()));
        holder.noteTextView.setText(String.valueOf(item.getNote()));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
