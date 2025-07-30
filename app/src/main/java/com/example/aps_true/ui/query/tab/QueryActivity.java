package com.example.aps_true.ui.query.tab;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aps_true.R;
import com.example.aps_true.ui.main.MainActivity;
import com.example.aps_true.ui.query.QianguanActivity;
import com.example.aps_true.ui.query.QueryMainActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import java.util.HashMap;
import java.util.regex.Pattern;

public class QueryActivity extends AppCompatActivity{

    private ImageButton backImageButton;
    private Button moredateButton,moreordernumberButton,moreclientButton;
    private Spinner processSpinner;
    private int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);

        bindUI();

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moreordernumberButton.setOnClickListener(this::moreorderclick);
        moreclientButton.setOnClickListener(this::moreclientclick);
        moredateButton.setOnClickListener(this::moredateclick);

    }

    protected void bindUI(){
        backImageButton = findViewById(R.id.query_back_ibtn);
        moredateButton = findViewById(R.id.query_moredate_btn);
        moreordernumberButton = findViewById(R.id.query_moreordernumber_btn);
        moreclientButton = findViewById(R.id.query_moreclient_btn);
        processSpinner = findViewById(R.id.query_process_spinner);
    }

    protected void moreorderclick(View view){

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
                // 跳轉到 前關
                Intent intent = new Intent(QueryActivity.this, QueryMainActivity.class);
                intent.putExtra("ORDER_NUMBER", items[choice]);
                startActivity(intent);
            }
        });
        dialog.show(); // 顯示dialog

    }

    protected void moreclientclick(View view){

        choice = 0; //預設選擇Item2(從0開始)
        //前關 本階 後關 裝配 銷售
        String[] items = {"我", "沒", "看", "到", "(M1315) MATADOR  GmbH"};
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
                Intent intent = new Intent(QueryActivity.this, QueryMainActivity.class);
                intent.putExtra("ORDER_NUMBER", items[choice]);
                startActivity(intent);
            }
        });
        dialog.show(); // 顯示dialog
    }

    protected void moredateclick(View view){

    }
}
