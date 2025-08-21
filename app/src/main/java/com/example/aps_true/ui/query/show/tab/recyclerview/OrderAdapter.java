package com.example.aps_true.ui.query.show.tab.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.aps_true.R;
import com.example.aps_true.ui.query.main.QueryTabActivity;
import com.example.aps_true.ui.query.main.recyclerview.QueryItem;

public class OrderAdapter extends RecyclerView.Adapter<com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderItem> dataList;

    public OrderAdapter(Context context, ArrayList<OrderItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_order, parent, false);
        return new com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter.ViewHolder(view);
    }


    public void updateData(List<OrderItem> newData) {
        this.dataList.clear();
        this.dataList.addAll(newData);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialTextView, materialTextView, specificationsTextView, unitdosageTextView,
                requiredamountTextView, unitTextView, storageTextView, descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            serialTextView = itemView.findViewById(R.id.order_serial_tv);
            materialTextView = itemView.findViewById(R.id.order_material_tv);
            specificationsTextView = itemView.findViewById(R.id.order_specifications_tv);
            unitdosageTextView = itemView.findViewById(R.id.order_unitdosage_tv);
            requiredamountTextView = itemView.findViewById(R.id.order_requiredamount_tv);
            unitTextView = itemView.findViewById(R.id.order_unit_tv);
            storageTextView = itemView.findViewById(R.id.order_storage_tv);
            descriptionTextView = itemView.findViewById(R.id.order_description_tv);
        }
    }

    @Override
    public void onBindViewHolder(com.example.aps_true.ui.query.show.tab.recyclerview.OrderAdapter.ViewHolder holder, int position) {
        OrderItem item = dataList.get(position);
        holder.serialTextView.setText(String.valueOf(item.getSerial()));
        holder.materialTextView.setText(String.valueOf(item.getMaterial()));
        holder.specificationsTextView.setText(String.valueOf(item.getSpecifications()));
        holder.unitdosageTextView.setText(String.valueOf(item.getUnitdosage()));
        holder.requiredamountTextView.setText(String.valueOf(item.getRequiredamount()));
        holder.unitTextView.setText(String.valueOf(item.getUnit()));
        holder.storageTextView.setText(String.valueOf(item.getStorage()));
        holder.descriptionTextView.setText(String.valueOf(item.getDescription()));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}