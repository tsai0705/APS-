package com.example.aps_true.ui.query.main.recyclerview;

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

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QueryItem> dataList;

    public QueryAdapter(Context context, ArrayList<QueryItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_query, parent, false);
        return new ViewHolder(view);
    }


    public void updateData(List<QueryItem> newData) {
        this.dataList.clear();
        this.dataList.addAll(newData);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialTextView, ordernumberTextView, sourceTextView,
                numberTextView, number2TextView, number3TextView, number4TextView,
                timeTextView, processTextView, checkTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            serialTextView = itemView.findViewById(R.id.recyclerview_serial_tv);
            ordernumberTextView = itemView.findViewById(R.id.recyclerview_ordernumber_tv);
            sourceTextView = itemView.findViewById(R.id.recyclerview_source_tv);
            numberTextView = itemView.findViewById(R.id.recyclerview_number_tv);
            number2TextView = itemView.findViewById(R.id.recyclerview_number2_tv);
            number3TextView = itemView.findViewById(R.id.recyclerview_number3_tv);
            number4TextView = itemView.findViewById(R.id.recyclerview_number4_tv);
            timeTextView = itemView.findViewById(R.id.recyclerview_time_tv);
            processTextView = itemView.findViewById(R.id.recyclerview_process_tv);
            checkTextView = itemView.findViewById(R.id.recyclerview_check_tv);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QueryItem item = dataList.get(position);
        holder.serialTextView.setText(String.valueOf(item.getSerial()));
        holder.ordernumberTextView.setText(String.valueOf(item.getOrderNumber()));
        holder.sourceTextView.setText(String.valueOf(item.getSource()));
        holder.numberTextView.setText(String.valueOf(item.getNumber()));
        holder.number2TextView.setText(String.valueOf(item.getNumber2()));
        holder.number3TextView.setText(String.valueOf(item.getNumber3()));
        holder.number4TextView.setText(String.valueOf(item.getNumber4()));
        holder.timeTextView.setText(String.valueOf(item.getTime()));
        holder.processTextView.setText(String.valueOf(item.getProcess()));
        holder.checkTextView.setText(String.valueOf(item.getCheck()));

        //跳轉單獨顯示頁面
        holder.serialTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QueryTabActivity.class);
            context.startActivity(intent);
        });

    }





    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}