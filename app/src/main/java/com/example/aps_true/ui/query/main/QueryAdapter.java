package com.example.aps_true.ui.query.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.aps_true.R;

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
                .inflate(R.layout.item_query, parent, false); // 請用你自訂的layout檔名
        return new ViewHolder(view);
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
//        QueryItem item = dataList.get(position);
//        holder.serialTextView.setText(item.getSerial());
//        holder.ordernumberTextView.setText(item.getOrderNumber());
//        holder.sourceTextView.setText(item.getSource());
//        holder.numberTextView.setText(item.getNumber());
//        holder.number2TextView.setText(item.getNumber2());
//        holder.number3TextView.setText(item.getNumber3());
//        holder.number4TextView.setText(item.getNumber4());
//        holder.timeTextView.setText(item.getTime());
//        holder.processTextView.setText(item.getProcess());
//        holder.checkTextView.setText(item.getCheck());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}