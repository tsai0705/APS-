package com.example.aps_true.ui.query.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.data.TabData;
import com.example.aps_true.ui.QuerySpinner;
import com.example.aps_true.ui.query.main.recyclerview.QueryMainActivity;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.CustomerResponse;
import com.example.aps_true.utils.api.response.OrderResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QueryActivity extends AppCompatActivity{

    private ImageButton backImageButton;
    private Button dateButton,order_numberButton,clientButton,submitButton;
    private QuerySpinner processSpinner;
    private EditText dateEditText,ordernumberEditText,clientEditText;
    private TextView usernameTextview;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData
    private TabData tabData = TabData.getInstance();
    private ApiClient apiClient;
    private GetApi getApi;
    private String[] soIds = new String[0];
    private String[] customers = new String[0];
    private ArrayList<String> filteredClientName = new ArrayList<>();
    private ArrayList<String> filteredSo = new ArrayList<>();
    private int choice = 0;
    private ArrayList<String> allSoIdsList = new ArrayList<>();
    private ArrayList<String> allCustomerNamesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);

        usernameTextview = findViewById(R.id.query_username_tv);
        usernameTextview.setText(loginData.getName());

        bindUI();

        // --- 變更 3: 在 onCreate 時預先載入所有資料 ---
        loadInitialData();

        final String[] str = {"點焊"};
        ArrayAdapter<String> strList = new ArrayAdapter<>(
                QueryActivity.this,
                android.R.layout.simple_spinner_item,
                str
        );

        processSpinner.setAdapter(strList);
        strList.setDropDownViewResource(R.layout.options_spinner);

        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(v -> {
            processSpinner.setBackgroundResource(R.drawable.process_button);
        });

        // --- 變更 4: OnClickListener 指向新的篩選方法 ---
        order_numberButton.setOnClickListener(this::filterAndShowOrderDialog);
        clientButton.setOnClickListener(this::filterAndShowClientDialog);
        dateButton.setOnClickListener(this::dateclick);
        submitButton.setOnClickListener(this::submitclick);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // onResume 會清除使用者 "上一次的選擇"
        ordernumberEditText.setText("");
        clientEditText.setText("");
        dateEditText.setText("");

        // 清除 TabData 中的 "選擇"
        tabData.setSo(new ArrayList<>());
        tabData.setCustomer(new ArrayList<>());

        // 清除 Activity 內的 "篩選後" 列表
        filteredSo.clear();
        filteredClientName.clear();
        soIds = new String[0];
        customers = new String[0];
    }

    protected void bindUI(){
        backImageButton = findViewById(R.id.query_back_ibtn);
        dateButton = findViewById(R.id.query_date_btn);
        order_numberButton = findViewById(R.id.query_order_number_btn);
        clientButton = findViewById(R.id.query_client_btn);
        submitButton = findViewById(R.id.query_submit_btn);
        processSpinner = findViewById(R.id.query_process_spinner);
        dateEditText = findViewById(R.id.query_date_et);
        ordernumberEditText = findViewById(R.id.query_order_number_et);
        clientEditText = findViewById(R.id.query_client_et);

        apiClient = new ApiClient();
        getApi = apiClient.ApsApi().create(GetApi.class);
    }

    protected void loadInitialData() {
        // 1. 載入所有訂單
        getApi.getOrder("", loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<OrderResponse>>() {
                    @Override
                    public void onNext(List<OrderResponse> responseList) {
                        allSoIdsList.clear();
                        for (OrderResponse order : responseList) {
                            allSoIdsList.add(order.getSoId());
                        }
                        Log.d("loadInitialData", "已載入 " + allSoIdsList.size() + " 筆訂單到主列表");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("loadInitialData", "載入訂單主列表失敗", e);
                    }
                    @Override
                    public void onComplete() {}
                });

        // 2. 載入所有客戶
        getApi.getCustomer("", loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<CustomerResponse>>() {
                    @Override
                    public void onNext(List<CustomerResponse> responseList) {
                        allCustomerNamesList.clear();
                        for (CustomerResponse customer : responseList) {
                            String customerName = customer.getCustomer_name();
                            if (customerName != null && !customerName.isEmpty() && !allCustomerNamesList.contains(customerName)) {
                                allCustomerNamesList.add(customerName);
                            }
                        }
                        Log.d("loadInitialData", "已載入 " + allCustomerNamesList.size() + " 筆客戶到主列表");
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("loadInitialData", "載入客戶主列表失敗", e);
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    protected void filterAndShowOrderDialog(View view) {
        String ordernumber = ordernumberEditText.getText().toString();
        filteredSo.clear(); // 清除上次篩選結果

        if (ordernumber != null && !ordernumber.isEmpty()) {
            for (String soId : allSoIdsList) {
                if (soId.contains(ordernumber)) {
                    filteredSo.add(soId);
                }
            }
        } else {
            // 沒有輸入：顯示全部
            filteredSo.addAll(allSoIdsList);
        }

        soIds = filteredSo.toArray(new String[0]);

        if (soIds.length == 0) {
            new AlertDialog.Builder(QueryActivity.this)
                    .setMessage("查無符合的訂單編號！")
                    .setPositiveButton("確定", null)
                    .show();
            return;
        }

        // 顯示對話框
        showOrderDialog();
    }

    protected void filterAndShowClientDialog(View view) {
        String client = clientEditText.getText().toString();
        filteredClientName.clear(); // 清除上次篩選結果

        if (client != null && !client.isEmpty()) {
            for (String customerName : allCustomerNamesList) {
                if (customerName != null && customerName.contains(client)) {
                    filteredClientName.add(customerName);
                }
            }
        } else {
            // 沒有輸入：顯示全部
            filteredClientName.addAll(allCustomerNamesList);
        }

        customers = filteredClientName.toArray(new String[0]);

        if (customers.length == 0) {
            new AlertDialog.Builder(QueryActivity.this)
                    .setMessage("查無符合的客戶名稱！")
                    .setPositiveButton("確定", null)
                    .show();
            return;
        }

        // 顯示對話框
        showClientDialog();
    }

    private void showOrderDialog() {
        choice = 0; // 重置預設選項
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        final int[] choiceHolder = {choice};
        dialog.setSingleChoiceItems(soIds, choiceHolder[0], (dialogInterface, i) -> choiceHolder[0] = i);

        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
            if (choiceHolder[0] >= 0 && choiceHolder[0] < soIds.length) {
                ordernumberEditText.setText(soIds[choiceHolder[0]]);
                ArrayList<String> soid = new ArrayList<>();
                soid.add(soIds[choiceHolder[0]]);
                tabData.setSo(soid); // *** 這會設定使用者的 "選擇" ***
                Log.d("showOrderDialog", "已設定 SO ID: " + soIds[choiceHolder[0]]);
            }
        });
        dialog.show();
    }

    private void showClientDialog() {
        choice = 0; // 重置預設選項
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        final int[] choiceHolder = {choice};
        dialog.setSingleChoiceItems(customers, choiceHolder[0], (dialogInterface, i) -> choiceHolder[0] = i);

        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
            if (choiceHolder[0] >= 0 && choiceHolder[0] < customers.length) {
                clientEditText.setText(customers[choiceHolder[0]]);
                ArrayList<String> clientname = new ArrayList<>();
                clientname.add(customers[choiceHolder[0]]);
                tabData.setCustomer(clientname); // *** 這會設定使用者的 "選擇" ***
                Log.d("showClientDialog", "已設定 Customer: " + customers[choiceHolder[0]]);
            }
        });
        dialog.show();
    }

    protected void dateclick(View view){
        choice = 0;
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(QueryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                StringBuilder result = new StringBuilder();
                String month = (m + 1 < 10) ? "0" + (m + 1) : String.valueOf(m + 1);
                String day = (d < 10) ? "0" + d : String.valueOf(d);
                result.append(y).append("-").append(month).append("-").append(day);
                dateEditText.setText(result.toString());
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    protected void submitclick(View view){

        // --- 處理訂單 ---
        ArrayList<String> soIdList = tabData.getSo();
        // 情況 1: 使用者沒有從 Dialog 選擇 (soIdList 是空的)
        if (soIdList.isEmpty()) {
            String orderFilter = ordernumberEditText.getText().toString();

            // 情況 1a: 使用者在 EditText 填了字
            if (!orderFilter.isEmpty()) {
                ArrayList<String> soFilterList = new ArrayList<>();
                for (String id : allSoIdsList) {
                    if (id.contains(orderFilter)) {
                        soFilterList.add(id);
                    }
                }
                tabData.setSo(soFilterList); // 傳送 "模糊篩選" 後的列表
                Log.d("submitclick", "傳送模糊篩選訂單: " + soFilterList.size() + " 筆");
            }
            // 情況 1b: 使用者 EditText 也是空的
            else {
                tabData.setSo(allSoIdsList); // *** 傳送 "全部" 列表 ***
                Log.d("submitclick", "傳送所有訂單: " + allSoIdsList.size() + " 筆");
            }
        }
        // 情況 2: 使用者已從 Dialog 選擇 (soIdList 有 1 個項目)
        else {
            Log.d("submitclick", "傳送 Dialog 選擇的訂單: " + soIdList.get(0));
        }


        // --- 處理客戶 (邏輯同上) ---
        ArrayList<String> customerList = tabData.getCustomer();
        // 情況 1: 使用者沒有從 Dialog 選擇
        if (customerList.isEmpty()) {
            String clientFilter = clientEditText.getText().toString();

            // 情況 1a: 使用者在 EditText 填了字
            if (!clientFilter.isEmpty()) {
                ArrayList<String> customerFilterList = new ArrayList<>();
                for (String name : allCustomerNamesList) {
                    if (name.contains(clientFilter)) {
                        customerFilterList.add(name);
                    }
                }
                tabData.setCustomer(customerFilterList); // 傳送 "模糊篩選" 後的列表
                Log.d("submitclick", "傳送模糊篩選客戶: " + customerFilterList.size() + " 筆");
            }
            // 情況 1b: 使用者 EditText 也是空的
            else {
                tabData.setCustomer(allCustomerNamesList); // *** 傳送 "全部" 列表 ***
                Log.d("submitclick", "傳送所有客戶: " + allCustomerNamesList.size() + " 筆");
            }
        }
        // 情況 2: 使用者已從 Dialog 選擇
        else {
            Log.d("submitclick", "傳送 Dialog 選擇的客戶: " + customerList.get(0));
        }

        // 啟動下一個 Activity
        Intent intent = new Intent(QueryActivity.this, QueryMainActivity.class);
        startActivity(intent);
    }

}