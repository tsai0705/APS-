package com.example.aps_true.ui.query.main.recyclerview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.example.aps_true.R;

import java.util.ArrayList;

// recycleview 查詢結果
public class QueryMainActivity extends AppCompatActivity {
    private ImageButton backImageButton;
    private RecyclerView resultRecyclerView;
    private QueryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querymain);

        backImageButton = findViewById(R.id.querymain_back_ibtn);
        resultRecyclerView = findViewById(R.id.querymain_result_rv);

        Intent intent = getIntent();

        // RecyclerView 設定
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new QueryAdapter(this, new ArrayList<>());
        resultRecyclerView.setAdapter(adapter);

        loadData();



        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void loadData(){
        ArrayList<QueryItem> datalist = new ArrayList<>();
        String[] process = {"一群-點焊"};
        String[] check = {"生效"};
        String[] order_number = {"1MO1812040031","1MO1812040030","1MO1812040027","1MO1812030024","1MO1812030012","1MO1812030010","1MO1812040028","1MO1812030023"};
        String[] source = {"1SO1811270009","1SO1811270009","1SO1811270010","1SOA1811300002","1SOA1811300001","1SOA1811300001","1SO1811270008","1SOA1811300002"};
        String[] number = {"F260011ATN-2","F260011ATN-1","F260001-1","F10318M-3A","F10217M-5A","F10217M-1","F260001ATN-1A","F10318M-2A"};
        String[] number3= {"數量：3","數量：3","數量：20","數量：10","數量：10","數量：10","數量：6","數量：70"};
        String[] number4= {"結關日：2018-12-08"};
        String[] time = {"上線日：2018-12-07"};
        for (  int serial = 1;serial<9;serial++){
            String number2;
            if (serial == 1 || serial == 2 || serial == 3 || serial == 7) {
                number2 = "MATADOR";
            } else {
                number2 = "祥雲工具股份有限公司";
            }
            QueryItem item = new QueryItem( serial,order_number[serial - 1],source[serial - 1],number[serial - 1],number2,number3[serial - 1],
                    number4[0],time[0],process[0],check[0]);
            datalist.add(item);
        }
        adapter.updateData(datalist);
        resultRecyclerView.setAdapter(adapter);  // 設定進 RecyclerView
    }
}

