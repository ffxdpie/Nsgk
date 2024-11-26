package com.fx.nsgk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class IntersectFanView extends View {

    private Paint paintLargeFan;      // 大扇形的画笔
    private Paint paintSmallFan;      // 小扇形的画笔
    private Paint paintIntersection;  // 交集区域的画笔

    private Path largeFanPath;        // 大扇形路径
    private Path smallFanPath;        // 小扇形路径
    private Path intersectionPath;    // 交集路径

    private RectF largeFanRect;       // 大扇形的外接矩形
    private RectF smallFanRect;       // 小扇形的外接矩形

    private float largeFanSweepAngle = 120f; // 大扇形的扫过角度
    private float smallFanSweepAngle = 90f;  // 小扇形的扫过角度

    public IntersectFanView(Context context) {
        super(context);
        init();
    }

    public IntersectFanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 初始化画笔和对象
    private void init() {
        // 初始化画笔
        paintLargeFan = new Paint();
        paintLargeFan.setColor(Color.RED); // 大扇形颜色
        paintLargeFan.setStyle(Paint.Style.FILL);
        paintLargeFan.setAntiAlias(true);

        paintSmallFan = new Paint();
        paintSmallFan.setColor(Color.BLUE); // 小扇形颜色
        paintSmallFan.setStyle(Paint.Style.FILL);
        paintSmallFan.setAntiAlias(true);

        paintIntersection = new Paint();
        paintIntersection.setColor(Color.GREEN); // 交集区域颜色
        paintIntersection.setStyle(Paint.Style.FILL);
        paintIntersection.setAntiAlias(true);

        // 预先初始化路径对象
        largeFanPath = new Path();
        smallFanPath = new Path();
        intersectionPath = new Path();

        // 预先初始化矩形
        largeFanRect = new RectF();
        smallFanRect = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 圆心坐标
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        // 设置大扇形的外接矩形
        largeFanRect.set(centerX - 300, centerY - 300, centerX + 300, centerY + 300);

        // 设置小扇形的外接矩形
        smallFanRect.set(centerX - 200, centerY - 200, centerX + 200, centerY + 200);

        // 计算大扇形的起始角度，使其从中心向两边展开
        float largeFanStartAngle = -largeFanSweepAngle / 2;

        // 清空路径并绘制大扇形
        largeFanPath.reset();
        largeFanPath.moveTo(centerX, centerY); // 从圆心开始
        largeFanPath.arcTo(largeFanRect, largeFanStartAngle, largeFanSweepAngle); // 绘制大扇形
        largeFanPath.close();

        // 计算小扇形的起始角度，使其从中心向两边展开
        float smallFanStartAngle = -smallFanSweepAngle / 2;

        // 清空路径并绘制小扇形
        smallFanPath.reset();
        smallFanPath.moveTo(centerX, centerY); // 从圆心开始
        smallFanPath.arcTo(smallFanRect, smallFanStartAngle, smallFanSweepAngle); // 绘制小扇形
        smallFanPath.close();

        // 计算交集路径
        intersectionPath.reset();
        intersectionPath.op(largeFanPath, smallFanPath, Path.Op.INTERSECT);

        // 绘制大扇形
        canvas.drawPath(largeFanPath, paintLargeFan);

        // 绘制小扇形
        canvas.drawPath(smallFanPath, paintSmallFan);

        // 绘制交集区域
        canvas.drawPath(intersectionPath, paintIntersection);
    }

    /**
     * 更新两个扇形的角度，并刷新视图
     *
     * @param largeAngle 大扇形扫过角度
     * @param smallAngle 小扇形扫过角度
     */
    public void updateAngles(float largeAngle, float smallAngle) {
        this.largeFanSweepAngle = largeAngle;
        this.smallFanSweepAngle = smallAngle;
        invalidate(); // 触发重绘
    }
}
