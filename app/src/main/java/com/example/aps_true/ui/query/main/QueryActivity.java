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
    private ArrayList<String> clientName = new ArrayList<>();
    private ArrayList<String> so = new ArrayList<>();
    private int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);

        usernameTextview = findViewById(R.id.query_username_tv);
        usernameTextview.setText(loginData.getName());

        bindUI();
        getOrderData();

        final String[] str = {"點焊"};
        ArrayAdapter<String> strList = new ArrayAdapter<>(
                QueryActivity.this,
                android.R.layout.simple_spinner_item,
                str
        );

        processSpinner.setAdapter(strList);
        strList.setDropDownViewResource(R.layout.options_spinner);

        // 點擊監聽空白處
        View rootView = findViewById(android.R.id.content);
        rootView.setOnClickListener(v -> {
            // 點擊空白處時，如果 Spinner 沒有被選中，設置為細框
            processSpinner.setBackgroundResource(R.drawable.process_button);
        });

        order_numberButton.setOnClickListener(this::orderclick);
        clientButton.setOnClickListener(this::clientclick);
        dateButton.setOnClickListener(this::dateclick);
        submitButton.setOnClickListener(this::submitclick);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    protected void getOrderData(){
        String so_id = "";
        getApi.getOrder(so_id, loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<OrderResponse>>() {
                    @Override
                    public void onNext(List<OrderResponse> responseList) {
                        for (OrderResponse order : responseList) {
                            Log.d("getOrder", "收到訂單: " + order.getSoId());
                        }

                        String ordernumber = ordernumberEditText.getText().toString();
                        if (ordernumber != null && !ordernumber.isEmpty()){
                            // order是responseList裡的每個 OrderResponse 物件
                            for (OrderResponse order : responseList) {
                                String soId = order.getSoId();
                                // 篩選是否包含在ordernumberEditText輸入的文字
                                if (soId.contains(ordernumber)) {
                                    so.add(soId);
                                }
                            }
                        }else {
                            // 顯示全部
                            for (OrderResponse order : responseList) {
                                String soId = order.getSoId();
                                so.add(soId);
                            }
                            // 全部添加
                            tabData.setSo(so);
                        }

                        // 轉成字串陣列給 Dialog 用
                        soIds = so.toArray(new String[0]);
                        getClientData();

                        // 如果沒有任何 soId，顯示提示
                        if (soIds.length == 0) {
                            new AlertDialog.Builder(QueryActivity.this)
                                    .setMessage("查無符合的訂單編號！")
                                    .setPositiveButton("確定", null)
                                    .show();
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getOrder", "API 錯誤: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getOrder", "請求完成");
                    }
                });
    }

    protected void getClientData() {
        String customer_name = "";
        getApi.getCustomer(customer_name, loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<CustomerResponse>>() {
                    @Override
                    public void onNext(List<CustomerResponse> responseList) {
                        Log.d("getCustomer", "收到 " + responseList.size() + " 筆客戶資料");
                        clientName.clear();
                        String client = clientEditText.getText().toString();

                        if (client != null && !client.isEmpty()) {
                            Log.d("getCustomer", "客戶名稱包含 '" + client + "'");
                            for (CustomerResponse customer : responseList) {
                                String customerName = customer.getCustomer_name();

                                if (customerName != null && customerName.contains(client)) {
                                    if (!clientName.contains(customerName)) {
                                        clientName.add(customerName);
                                        Log.d("getCustomer", "符合: " + customerName);
                                    }
                                }
                            }
                        }
                        // 沒有輸入：顯示全部
                        else {
                            Log.d("getCustomer", "顯示全部客戶");

                            for (CustomerResponse customer : responseList) {
                                String customerName = customer.getCustomer_name();
                                if (customerName != null && !customerName.isEmpty()) {
                                    clientName.add(customerName);
                                }
                            }
                        }

                        tabData.setCustomer(clientName);
                        Log.d("getCustomer", "已設定 " + clientName.size() + " 筆客戶到 TabData");
                        customers = clientName.toArray(new String[0]);

                        if (customers.length == 0) {
                            Log.w("getCustomer", "查無符合的客戶");
                            runOnUiThread(() -> {
                                new AlertDialog.Builder(QueryActivity.this)
                                        .setMessage("查無符合的客戶名稱！")
                                        .setPositiveButton("確定", null)
                                        .show();
                            });
                        } else {
                            Log.d("getCustomer", "查詢完成: " + customers.length + " 筆客戶");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getCustomer", "API 錯誤", e);
                        runOnUiThread(() -> {
                            new AlertDialog.Builder(QueryActivity.this)
                                    .setMessage("載入客戶失敗: " + e.getMessage())
                                    .setPositiveButton("確定", null)
                                    .show();
                        });
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    protected void orderclick(View view) {
        choice = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        //宣告一個 reference(choiceHolder) 不能變的 int 陣列（內容可以變）
        final int[] choiceHolder = {choice};
        // choiceHolder[0] : 預設選項
        // (dialogInterface, i) -> choiceHolder[0] = i：當使用者選擇其中一項時，把 index 存進 choiceHolder[0]
        dialog.setSingleChoiceItems(soIds, choiceHolder[0], (dialogInterface, i) -> choiceHolder[0] = i);

        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
            ordernumberEditText.setText(soIds[choiceHolder[0]]);
            ArrayList<String> soid = new ArrayList<>();
            soid.add(soIds[choiceHolder[0]]);
            ArrayList<String> soClear = new ArrayList<>();
            tabData.setSo(soClear);
            tabData.setSo(soid);
        });
        ArrayList<String> soIdList = tabData.getSo();
        Log.d("getSo", "so size=" + soIdList.size());
        dialog.show();

    }

    protected void clientclick(View view){
        choice = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        final int[] choiceHolder = {choice};
        dialog.setSingleChoiceItems(customers, choice, (dialogInterface, i) -> choice = i);

        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
            clientEditText.setText(customers[choiceHolder[0]]);
            ArrayList<String> clientname = new ArrayList<>();
            clientname.add(customers[choiceHolder[0]]);
            ArrayList<String> customerClear = new ArrayList<>();
            tabData.setCustomer(customerClear);
            tabData.setCustomer(clientname);
        });
        ArrayList<String> customerList = tabData.getCustomer();
        Log.d("getCustomer", "customer size=" + customerList.size());
        dialog.show();
    }

    protected void dateclick(View view){
        choice = 0; //預設選擇Item1(從0開始)
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(QueryActivity.this, new DatePickerDialog.OnDateSetListener() {
            // 當日期選擇完畢並按下"OK"按鈕 的事件觸發處理
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                StringBuilder result = new StringBuilder();
                //m+1:在Android（Java）的Calendar和DatePicker中，月份從0開始計數而不是從1開始。
                result.append(y).append("-").append(m+1).append("-").append(d);
                dateEditText.setText(result.toString());
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //取出當前年月日作為預設選項
        dialog.show();
    }

    protected void submitclick(View view){
        Intent intent = new Intent(QueryActivity.this, QueryMainActivity.class);
        ArrayList<String> soIdList = tabData.getSo();
        Log.d("getSo", "so size=" + soIdList.size());
        ArrayList<String> customerList = tabData.getCustomer();
        Log.d("getCustomer", "customer size=" + customerList.size());
        intent.putExtra("total", soIdList.size());
        ordernumberEditText.setText("");
        clientEditText.setText("");
        startActivity(intent);
    }


}
