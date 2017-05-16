package com.rick.pieview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by MyPC on 2017/5/11.
 */

public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    private ArrayList<PieData> mData;
    private int mWidth, mHeight;
    private Paint mPaint = new Paint();

    //文字色块部分
    private PointF mStartPoint = new PointF(20, 20);
    private PointF mCurrentPoint = new PointF(mStartPoint.x, mStartPoint.y);
    private float mColorRectSideLength = 20;
    private float mTextInterval = 10;
    private float mRowMaxLength;


    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null == mData || mData.size() <= 0) {
            return;
        }
        float currentStartAngle = mStartAngle;                          // 当前起始角度
        canvas.translate(mWidth / 2, mHeight / 2);                      // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);        // 饼状图半径
        RectF rectF = new RectF(-r, -r, r, r);                          // 饼状图绘制区域

        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF, currentStartAngle, pie.getAngle(), true, mPaint);

            canvas.save();
            canvas.rotate(currentStartAngle + (pie.getAngle() / 2));
//            canvas.translate(mWidth / 8, mHeight / 8);
//            RectF colorRect = new RectF(mCurrentPoint.x, mCurrentPoint.y, mCurrentPoint.x + mColorRectSideLength, mCurrentPoint.y + mColorRectSideLength);
//            Path path = new Path();
//            path.addRect(colorRect, Path.Direction.CCW);
            mPaint.setColor(Color.BLACK);
//            canvas.drawTextOnPath(pie.getName(), path, 0, 0, mPaint);
            mPaint.setTextSize(20);
            canvas.drawText(pie.getName(), 0, 0, mPaint);
            canvas.restore();

            currentStartAngle += pie.getAngle();
        }

    }

    // 设置起始角度
    public void setmStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();
    }

    // 设置数据
    public void setData(ArrayList<PieData> pieDatas) {
        this.mData = pieDatas;
        initData();
        invalidate();
    }

    // 初始化数据
    private void initData() {
        if (null == mData || mData.size() <= 0) {
            return;
        }

        //计算数值和    设置颜色
        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.getValue();
            pie.setColor(mColors[i % mColors.length]);
        }

        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.getValue() / sumValue; // 百分比
            float angle = percentage * 360; //对应的角度

            pie.setPercentage(percentage);
            pie.setAngle(angle);


            Log.i("angle", "" + pie.getAngle());
        }

    }
}
