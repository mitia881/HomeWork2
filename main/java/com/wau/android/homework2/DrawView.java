package com.wau.android.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;



import androidx.annotation.Nullable;

public class DrawView extends View {
    private DroViewListener listener;
    private Paint paint;
    private int[] colorList = {getResources().getColor(R.color.color_RED),
            getResources().getColor(R.color.color_GREEN),
            getResources().getColor(R.color.color_YELLOW),
            getResources().getColor(R.color.color_BLYE)};

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public void setListener(DroViewListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF bottomCircle = new RectF(
                (float) (getWidth() * 0.1),
                (float) (getHeight() * 0.1),
                (float) (getWidth() * 0.9),
                (float) (getHeight() * 0.9));
        //Top - left
        paint.setColor(colorList[0]);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(bottomCircle, 180, 90, true, paint);

        // Top - right
        paint.setColor(colorList[1]);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(bottomCircle, 270f, 90f, true, paint);

        //Bottom - right
        paint.setColor(colorList[2]);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(bottomCircle, 0f, 90f, true, paint);

        //Bottom - left
        paint.setColor(colorList[3]);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(bottomCircle, 90f, 90f, true, paint);

        RectF topCircle = new RectF(
                (float) (getWidth() * 0.35),
                (float) (getHeight() * 0.35),
                (float) (getWidth() * 0.65),
                (float) (getHeight() * 0.65));
        //Center circle
        paint.setColor(getResources().getColor(R.color.color_PURPLE));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(topCircle, 0f, 360f, true, paint);
    }

    private boolean checkCenterCircleClick(Float x, Float y) {
        float centerCircleRadius = (float) ((getWidth() * 0.65 - getWidth() * 0.35) / 2);
        if (x != null && y != null) {
            return x < ((float) getWidth() / 2f + centerCircleRadius)
                    && x > ((float) getWidth() / 2f - centerCircleRadius)
                    && y < ((float) getHeight() / 2f + centerCircleRadius)
                    && y > ((float) getHeight() / 2f - centerCircleRadius);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isTouchOnCenterCircle = checkCenterCircleClick(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //Press
                if (isTouchOnCenterCircle) {
                    mixColorList();
                    invalidate();
                } else {
                    String message = ("Coordinates[x:" + event.getX() +
                            ",y:" + event.getY() + "]");
                    listener.onCircleClick(message);
                }
                break;
        }
        return true;
    }

    private void mixColorList() {
        for (int i = 1; i <= 4; i++) {
            int random1 = (int) (Math.random() * 4);
            int random2 = (int) (Math.random() * 4);
            int buff = colorList[random1];
            colorList[random1] = colorList[random2];
            colorList[random2] = buff;
        }
    }
}