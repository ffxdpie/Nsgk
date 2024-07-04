package com.fx.nsgk;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fx.nsgk.R.id;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Objects;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_workingconditiondiagram extends AppCompatActivity {
    String TAG = "mytag_aw";
    private LineChart chart;
    private DatabaseHelper dbHelper;
    private int workingConditionId;
    private EditText numberEditText;
    int angle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workingconditiondiagram);
        dbHelper = new DatabaseHelper(this);
        chart = findViewById(R.id.chart);
        ImageButton imageButton = findViewById(R.id.imageButton4);
        Button button = findViewById(id.button3);
        numberEditText = findViewById(R.id.editTextNumber2);

        if (savedInstanceState != null){
            String s = savedInstanceState.getString("key");
            numberEditText.setText(s);
        }else {
            numberEditText.setText("0");
        }

        // 获取 Intent
        Intent intent = getIntent();
        // 提取传递过来的 数据
        workingConditionId = intent.getIntExtra("workingCondition",2273);  // 如果不存在，默认为 2273
        angle = intent.getIntExtra("angle",0);
        Log.d(TAG, "workingConditionId: "+ workingConditionId);
        //设置文本内容
        TextView workinginfo = findViewById(R.id.workinginfo);
        Workingconditiontype wkt = dbHelper.WorkingType(String.valueOf(workingConditionId));
        workinginfo.setText(SetupChart.workinfo(workingConditionId,wkt));
        // 获取 Toolbar 并设置标题
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("工况 " + workingConditionId);  // 动态设置标题
        setSupportActionBar(toolbar); // 将 Toolbar 设置为 ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        //绘图
        setupChart();


        // 复制文本
        imageButton.setOnClickListener(v -> {
            // 获取剪贴板管理器：
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建ClipData对象
            ClipData clip = ClipData.newPlainText("simple text", workinginfo.getText());
            // 将ClipData内容放到系统剪贴板上。
            clipboard.setPrimaryClip(clip);
            // 可选：给用户反馈信息
            Toast.makeText(Activity_workingconditiondiagram.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();

        });

        button.setOnClickListener(v -> setupChart());
    }


// 绘图
    private void setupChart() {
        if (numberEditText.getText() != null) {
            String numberStr = numberEditText.getText().toString(); // 获取EditText的文本并转换为字符串
            Log.d(TAG, "setupChart: " + numberStr);
            angle = Integer.parseInt(numberStr); // 尝试将字符串转换为整数
            if (angle > 90) {
                Toast.makeText(Activity_workingconditiondiagram.this, "角度应该小于90度", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            angle = 0;
        }
        ArrayList<Double> results = dbHelper.getAngleByWorking(angle, workingConditionId);
        SetupChart.XYset(chart ,results);
    }



    //数据持久化
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key",numberEditText.getText().toString());
    }

    //右上角返回
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 处理 Toolbar 返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish(); // 返回上一个页面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

