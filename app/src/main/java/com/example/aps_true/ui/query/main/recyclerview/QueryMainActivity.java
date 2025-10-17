package com.example.aps_true.ui.query.main.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.data.TabData;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.ManufactureResponse;
import com.example.aps_true.utils.api.response.SaleResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

// recycleview 查詢結果
public class QueryMainActivity extends AppCompatActivity {
    private ImageButton backImageButton;
    private RecyclerView resultRecyclerView;
    private TextView usernameTextview;
    private int dataReady = 0;
    private final int TOTAL_API = 2; // mo, item
    private LoginData loginData = LoginData.getInstance(); //連接LoginData
    private TabData tabData = TabData.getInstance();
    private QueryAdapter adapter;
    private ApiClient apiClient;
    private GetApi getApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_querymain);

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);

        usernameTextview = findViewById(R.id.querymain_username_tv);
        usernameTextview.setText(loginData.getName());
        backImageButton = findViewById(R.id.querymain_back_ibtn);
        resultRecyclerView = findViewById(R.id.querymain_result_rv);

        String clientname = getIntent().getStringExtra("client");
        String soid = getIntent().getStringExtra("soid");

        // RecyclerView 設定
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new QueryAdapter(this, new ArrayList<>());
        resultRecyclerView.setAdapter(adapter);

        getData();

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getData(){
        // moid
        String moid = "";
        getApi.getMo(moid, loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ManufactureResponse>>() {
                    @Override
                    public void onNext(List<ManufactureResponse> responseList) {
                        for (ManufactureResponse manu : responseList) {
                            Log.d("getMo", "收到訂單: " + manu.getMoId());
                        }

                        ArrayList<String> mo = new ArrayList<>();
                        // 轉成字串陣列給 Dialog 用
                        String[] moIds = new String[responseList.size()];
                        for (int i = 0; i < responseList.size(); i++) {
                            moIds[i] = responseList.get(i).getMoId();
                            mo.add(moIds[i]);
                        }
                        tabData.setMo(mo);
                        checkDataReady();
                    }

                    @Override
                    public void onError(Throwable e) {
                        checkDataReady();
                        Log.e("getMo", "API 錯誤: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getMo", "請求完成");
                    }
                });

        // item
        String not = "";
        getApi.getSale(not,not,not, loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<SaleResponse>>() {
                    @Override
                    public void onNext(List<SaleResponse> responseList) {
                        for (SaleResponse sale : responseList) {
                            Log.d("getSale", "收到訂單: " + sale.getRecords().getItemId());
                        }

                        ArrayList<String> item = new ArrayList<>();
                        // 轉成字串陣列給 Dialog 用
                        String[] itemIds = new String[responseList.size()];
                        for (int i = 0; i < responseList.size(); i++) {
                            itemIds[i] = responseList.get(i).getRecords().getItemId();
                            item.add(itemIds[i]);
                        }
                        tabData.setItem(item);
                        checkDataReady();
                    }

                    @Override
                    public void onError(Throwable e) {
                        checkDataReady();
                        Log.e("getSale", "API 錯誤: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getSale", "請求完成");
                    }
                });
    }

    private void checkDataReady() {
        dataReady++;
        if (dataReady >= TOTAL_API) {
            loadData();
        }
    }

    protected void loadData(){
        ArrayList<QueryItem> datalist = new ArrayList<>();
        String[] process = {"一群-點焊"};
        String[] check = {"生效"};
        ArrayList<String> mo_id = tabData.getMo();
        ArrayList<String> so_id = tabData.getSo();
        ArrayList<String> item_id = tabData.getItem();
        ArrayList<String> client = tabData.getCustomer();
        ArrayList<String> qty = tabData.getQty();
        String[] number4= {"結關日：2018-12-08"};
        ArrayList<String> date = tabData.getDate();

        for ( int serial = 0; serial<mo_id.size(); serial++ ){
            QueryItem item = new QueryItem( serial+1,mo_id.get(serial),so_id.get(serial),item_id.get(serial),client.get(serial),
                    "數量"+qty.get(serial),number4[0],"上線日："+date.get(serial),process[0],check[0]);
            datalist.add(item);
        }
        adapter.updateData(datalist);
        resultRecyclerView.setAdapter(adapter);  // 設定進 RecyclerView
    }
}

