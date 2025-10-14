package com.example.aps_true.ui.query.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aps_true.R;
import com.example.aps_true.data.LoginData;

public class SettingActivity extends AppCompatActivity{

    private ImageButton backButton;
    private TextView usernameTextview;
    private LoginData loginData = LoginData.getInstance(); //連接LoginData

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        backButton = findViewById(R.id.setting_back_ibtn);
        usernameTextview = findViewById(R.id.setting_username_tv);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        usernameTextview.setText(loginData.getName());
    }

}
