package com.example.aps_true.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.DashPathEffect;
import android.util.AttributeSet;
import android.view.View;
import com.example.aps_true.R;

public class DividerView extends View {
    private Paint paint;
    private int lineColor;
    private float dashThickness;
    private float dashLength;
    private float dashGap;
    private int orientation; // 0=水平, 1=垂直

    public DividerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DividerView);
        lineColor = ta.getColor(R.styleable.DividerView_divider_line_color, 0xFF000000);
        dashThickness = ta.getDimension(R.styleable.DividerView_dashThickness, 2);
        dashLength = ta.getDimension(R.styleable.DividerView_dashLength, 10);
        dashGap = ta.getDimension(R.styleable.DividerView_dashGap, 5);
        orientation = ta.getInt(R.styleable.DividerView_divider_orientation, 0);
        ta.recycle();

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeWidth(dashThickness);
        paint.setPathEffect(new DashPathEffect(new float[]{dashLength, dashGap}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (orientation == 0) {
            // 水平虛線
            float centerY = getHeight() / 2f;
            canvas.drawLine(0, centerY, getWidth(), centerY, paint);
        } else {
            // 垂直虛線
            float centerX = getWidth() / 2f;
            canvas.drawLine(centerX, 0, centerX, getHeight(), paint);
        }
    }
}