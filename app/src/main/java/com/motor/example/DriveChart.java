package com.motor.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Ride Speed Chart
 */
public class DriveChart extends View {
    private static final float DEFAULT_SIZE = 100;
    //最大角度260  最小140
    private static final float FULL_CIRCLE_ANGLE = 260f;
    private RectF chartRect;
    private int strokeWidth = 13;
    private int defaultSize;
    private float density;
    //最大速度120
    private float maxSpeed = 120;
    private int degree = 0;
    private boolean isShade;


    public DriveChart(Context context) {
        this(context, null);
    }

    public DriveChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DriveChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.density = getContext().getResources().getDisplayMetrics().density;
        this.defaultSize = (int) (DEFAULT_SIZE * density);
        chartRect = new RectF();
    }

    /**
     * 设置大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        switch (widthSpec) {
            case MeasureSpec.AT_MOST:
                width = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                width = getMeasuredWidth();
                break;
        }

        switch (heightSpec) {
            case MeasureSpec.AT_MOST:
                height = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                height = getMeasuredHeight();
                break;
        }
        int chartSize = Math.min(width, height);
        int centerX = width / 2 - chartSize / 2;
        int centerY = height / 2 - chartSize / 2;
        int strokeHalf = strokeWidth / 2;

        chartRect.set(centerX + strokeHalf, centerY + strokeHalf, centerX + chartSize - strokeHalf, centerY + chartSize - strokeHalf);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (degree > 0) {
            drawChart(canvas, calculatePercents(degree));
        }
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    public int getDegree() {
        return degree;
    }

    private float calculatePercents(float degree) {
        return degree * FULL_CIRCLE_ANGLE / maxSpeed;
    }

    private void drawChart(Canvas canvas, float degree) {
        Paint paint = new Paint();

        //设置样式
        paint.setStyle(Paint.Style.STROKE);
        LinearGradient mLinearGradient = new LinearGradient(
                0,
                0,
                FULL_CIRCLE_ANGLE,
                0,
                getResources().getColor(R.color.color2A69F6),
                getResources().getColor(R.color.colorAccent),
                Shader.TileMode.CLAMP
        );
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        //根据是否需要渐变处理
        if (isShade) {
            paint.setShader(mLinearGradient);
            canvas.drawArc(chartRect, 140, degree, false, paint);
        } else {
            paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            canvas.drawArc(chartRect, 140, FULL_CIRCLE_ANGLE, false, paint);
        }


    }

    public void setShade(boolean shade) {
        isShade = shade;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = (int) (strokeWidth * density);
        invalidate();
    }

}
