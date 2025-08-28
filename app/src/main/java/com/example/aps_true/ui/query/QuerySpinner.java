package com.example.aps_true.ui.query;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.widget.AppCompatSpinner;
import com.example.aps_true.R;

// 自定義的 Spinner 元件
public class QuerySpinner extends AppCompatSpinner {
    private boolean isInitialization = false;
    private boolean isSettingInitialValue = false;

    // 建構函數
    // @param context 上下文環境
    public QuerySpinner(Context context) {
        super(context);
        init();
    }

    // @param context 上下文環境
    // @param attrs XML 屬性集
    public QuerySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        // 初始化觸摸事件監聽器
        // 點選時是細框
        setOnTouchListener(new OnTouchListener() {
            @Override
            //View v: 被觸控的視圖元件（此指Spinner）
            //MotionEvent event: 觸控事件的物件
            public boolean onTouch(View v, MotionEvent event) {
                // ACTION_UP : 手指離開螢幕時觸發
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setBackgroundResource(R.drawable.process_button);
                }
                return false;
            }
        });

        // 處理選擇事件
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //初始化設置選擇項時不觸發切換成粗框
                if (!isInitialization) {
                    // 執行前先將標記設為 true 表示「正在進行初始化設定」， 用來防止遞迴呼叫
                    isSettingInitialValue = true;
                    // 使用外部類的實例
                    QuerySpinner.this.setSelection(0);
                    // 表示初始化設定完畢
                    isSettingInitialValue = false;
                    // 設置為細框
                    setBackgroundResource(R.drawable.process_button);
                    isInitialization = true;
                } else {
                    //除初始化外，點選項時切換成粗框
                    setBackgroundResource(R.drawable.select_button);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //沒被選擇則細框
                setBackgroundResource(R.drawable.process_button);
            }
        });
    }

    // 覆寫點擊事件處理
    // 在使用者點擊 Spinner 時觸發
    // @return 是否成功處理點擊事件
    // 展開下拉選單時是細框
    @Override
    public boolean performClick() {
        // 設置展開時的背景樣式（細框）
        setBackgroundResource(R.drawable.process_button);
        // 調用父類的點擊處理
        return super.performClick();
    }

    // 覆寫 setSelection 方法來處理相同項目的選擇
    @Override
    public void setSelection(int position) {
        if (isSettingInitialValue) {
            super.setSelection(position);
            return;
        }

        // 檢查是否選擇了相同的項目
        boolean sameSelected = position == getSelectedItemPosition();
        // 執行父類的選擇方法
        super.setSelection(position);

        // 如果是相同的選項且已經初始化且有設置監聽器
        if (sameSelected && isInitialization && getOnItemSelectedListener() != null) {
            // 手動觸發選擇事件，使得相同選項仍能觸發
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }
}