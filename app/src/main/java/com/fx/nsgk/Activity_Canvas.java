package com.fx.nsgk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;


public class Activity_Canvas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_canvas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button button1 = findViewById(R.id.button7);;
        button1.setOnClickListener(v -> {
            updateImage();
        });

        // 创建一个 Handler，用于在主线程中定时更新图像
//        Handler handler = new Handler();

        // 设置每隔五秒更新一次图像
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                updateImage();  // 更新图像
//                handler.postDelayed(this, 50);  // 继续每五秒调用一次
//            }
//        }, 50);  // 首次延迟五秒后开始

    }

    private void updateImage() {
        IntersectFanView intersectFanView = findViewById(R.id.intersectFanView);
        // 生成随机角度
        float largeAngle = getRandomAngle(30, 360);  // 大扇形角度，范围30°到180°
        float smallAngle = getRandomAngle(30, 360);  // 小扇形角度，范围30°到150°
        // 更新扇形角度
        intersectFanView.updateAngles(largeAngle, smallAngle);

    }

    /**
     * 获取一个指定范围内的随机角度
     * @param min 最小角度
     * @param max 最大角度
     * @return 随机角度
     * @xiaosi 在这里可以胡说八道
     * @hah
     */
    private float getRandomAngle(int min, int max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }
}