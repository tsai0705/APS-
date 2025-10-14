package com.example.aps_true.viewpager.schdule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_true.R;
import com.example.aps_true.viewpager.todayschedule.TodayMainActivity;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<com.example.aps_true.viewpager.schdule.ScheduleAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ScheduleItem> dataList;

    public ScheduleAdapter(Context context, ArrayList<ScheduleItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public com.example.aps_true.viewpager.schdule.ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_schedule, parent, false);
        return new com.example.aps_true.viewpager.schdule.ScheduleAdapter.ViewHolder(view);
    }


    public void updateData(List<ScheduleItem> newData) {
        this.dataList.clear();
        this.dataList.addAll(newData);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialTextView, ordernumberTextView, sourceTextView,
                numberTextView, number2TextView, number3TextView, number4TextView,
                timeTextView, processTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            serialTextView = itemView.findViewById(R.id.schedule_serial_tv);
            ordernumberTextView = itemView.findViewById(R.id.schedule_ordernumber_tv);
            sourceTextView = itemView.findViewById(R.id.schedule_source_tv);
            numberTextView = itemView.findViewById(R.id.schedule_number_tv);
            number2TextView = itemView.findViewById(R.id.schedule_number2_tv);
            number3TextView = itemView.findViewById(R.id.schedule_number3_tv);
            number4TextView = itemView.findViewById(R.id.schedule_number4_tv);
            timeTextView = itemView.findViewById(R.id.schedule_time_tv);
            processTextView = itemView.findViewById(R.id.schedule_process_tv);
        }
    }

    @Override
    public void onBindViewHolder(com.example.aps_true.viewpager.schdule.ScheduleAdapter.ViewHolder holder, int position) {
        ScheduleItem item = dataList.get(position);
        holder.serialTextView.setText(String.valueOf(item.getSerial()));
        holder.ordernumberTextView.setText(String.valueOf(item.getOrderNumber()));
        holder.sourceTextView.setText(String.valueOf(item.getSource()));
        holder.numberTextView.setText(String.valueOf(item.getNumber()));
        holder.number2TextView.setText(String.valueOf(item.getNumber2()));
        holder.number3TextView.setText(String.valueOf(item.getNumber3()));
        holder.number4TextView.setText(String.valueOf(item.getNumber4()));
        holder.timeTextView.setText(String.valueOf(item.getTime()));
        holder.processTextView.setText(String.valueOf(item.getProcess()));

        //跳轉單獨顯示頁面
        holder.serialTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TodayMainActivity.class);
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}