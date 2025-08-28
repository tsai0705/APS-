package com.example.aps_true.ui.query.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aps_true.R;
import com.example.aps_true.ui.query.QuerySpinner;
import com.example.aps_true.ui.query.main.recyclerview.QueryMainActivity;

public class QueryActivity extends AppCompatActivity{

    private ImageButton backImageButton;
    private Button dateButton,order_numberButton,clientButton,submitButton;
    private QuerySpinner processSpinner;
    private EditText dateEditText,ordernumberEditText,clientEditText;
    private int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);

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
    }

    protected void orderclick(View view){

        choice = 0; //預設選擇Item2(從0開始)
        //前關 本階 後關 裝配 銷售
        String[] items = {"1MO1812040071", "1MO1812040031", "1MO1812040025", "1MO1812040005", "1SO1811270009"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        // 參數1：設置選項List;  參數2:設置預設選中項
        dialog.setSingleChoiceItems(items, choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choice = i;//紀錄使用者點選的選項之索引值
            }
        });

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ordernumberEditText.setText(items[choice]);
            }
        });
        dialog.show(); // 顯示dialog

    }

    protected void clientclick(View view){

        choice = 0; //預設選擇Item2(從0開始)
        //前關 本階 後關 裝配 銷售
        String[] items = {"我", "沒", "找", "到", "(M1315) MATADOR  GmbH"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        // 參數1：設置選項List;  參數2:設置預設選中項
        dialog.setSingleChoiceItems(items, choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choice = i;//紀錄使用者點選的選項之索引值
            }
        });

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clientEditText.setText(items[choice]);
            }
        });
        dialog.show(); // 顯示dialog
    }

    protected void dateclick(View view){
        choice = 0; //預設選擇Item2(從0開始)
        //前關 本階 後關 裝配 銷售
        String[] items = {"2018-12-05", "2018-12-06", "2018-12-07", "2018-12-08", "沒找到=-="};
        AlertDialog.Builder dialog = new AlertDialog.Builder(QueryActivity.this);

        // 參數1：設置選項List;  參數2:設置預設選中項
        dialog.setSingleChoiceItems(items, choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                choice = i;//紀錄使用者點選的選項之索引值
            }
        });

        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dateEditText.setText(items[choice]);
            }
        });
        dialog.show(); // 顯示dialog
    }

    protected void submitclick(View view){
        Intent intent = new Intent(QueryActivity.this, QueryMainActivity.class);
        startActivity(intent);
    }


}
