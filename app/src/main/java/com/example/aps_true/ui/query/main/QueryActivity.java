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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;
import com.example.aps_true.ui.QuerySpinner;
import com.example.aps_true.ui.query.main.recyclerview.QueryMainActivity;
import com.example.aps_true.utils.api.request.ApiClient;
import com.example.aps_true.utils.api.request.GetApi;
import com.example.aps_true.utils.api.response.CustomerResponse;
import com.example.aps_true.utils.api.response.OrderResponse;

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
    private ApiClient apiClient;
    private GetApi getApi;
    private String soid = "";
    private String client = "";

    private int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);

        usernameTextview = findViewById(R.id.query_username_tv);
        usernameTextview.setText(loginData.getName());

        bindUI();

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

    protected void orderclick(View view) {
        choice = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

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

                        // 轉成字串陣列給 Dialog 用
                        String[] soIds = new String[responseList.size()];
                        for (int i = 0; i < responseList.size(); i++) {
                            soIds[i] = responseList.get(i).getSoId();
                        }

                        dialog.setSingleChoiceItems(soIds, choice, (dialogInterface, i) -> choice = i);

                        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
                            ordernumberEditText.setText(soIds[choice]);
                            String soid = soIds[choice];
                        });

                        dialog.show();
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


    // 顯示訂單選擇對話框
    private void showOrderDialog(String[] items) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);
        dialog.setTitle("選擇訂單號");

        dialog.setSingleChoiceItems(items, choice, (dialogInterface, i) -> {
            choice = i;
        });

        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
            ordernumberEditText.setText(items[choice]);
        });

        dialog.setNegativeButton("取消", null);
        dialog.show();
    }


    // 客戶
    protected void clientclick(View view){
        choice = 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        String customer_name = "";
        getApi.getCustomer(customer_name, loginData.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<CustomerResponse>>() {
                    @Override
                    public void onNext(List<CustomerResponse> responseList) {
                        for (CustomerResponse customer : responseList) {
                            Log.d("getCustomer", "收到訂單: " + customer.getCustomer_name());
                        }

                        // 轉成字串陣列給 Dialog 用
                        String[] customers = new String[responseList.size()];
                        for (int i = 0; i < responseList.size(); i++) {
                            customers[i] = responseList.get(i).getCustomer_name();
                        }

                        dialog.setSingleChoiceItems(customers, choice, (dialogInterface, i) -> choice = i);

                        dialog.setPositiveButton("確定", (dialogInterface, i) -> {
                            clientEditText.setText(customers[choice]);
                            String client = customers[choice];
                        });

                        dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getCustomer", "API 錯誤: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("getCustomer", "請求完成");
                    }
                });
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
                Toast.makeText(QueryActivity.this, result, Toast.LENGTH_SHORT).show();
                dateEditText.setText(result.toString());
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //取出當前年月日作為預設選項
        dialog.show();
    }

    protected void submitclick(View view){
        Intent intent = new Intent(QueryActivity.this, QueryMainActivity.class);
        intent.putExtra("client", client);
        intent.putExtra("soid", soid);
        startActivity(intent);
    }


}
