package com.fx.nsgk;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorksElect extends AppCompatActivity  {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workselect);
        dbHelper = new DatabaseHelper(this);
        // 初始化 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // 将 Toolbar 设置为 ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner1 = findViewById(R.id.spinner);
                Spinner spinner2 = findViewById(R.id.spinner1);
                Spinner spinner3 = findViewById(R.id.spinner2);
                Spinner spinner4 = findViewById(R.id.spinner3);
                Spinner spinner5 = findViewById(R.id.spinner4);
                // rcdistance 配重伸出距离
                // Otype 支腿类型
                // Cwtype 配重类型
                // Rtype  回转类型
                int rcdistance = Integer.parseInt(spinner1.getSelectedItem().toString());
                int Otype = Integer.parseInt(spinner2.getSelectedItem().toString());
                int Cwtype = Integer.parseInt(spinner3.getSelectedItem().toString());
                int Rtype = Integer.parseInt(spinner4.getSelectedItem().toString());
                List<Integer> gkValues = dbHelper.queryWork(rcdistance, Otype, Cwtype, Rtype);
                ArrayAdapter<Integer> adapter;
                if (!gkValues.isEmpty()) {
                    adapter = new ArrayAdapter<>(WorksElect.this,
                            android.R.layout.simple_spinner_item, gkValues);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner5.setAdapter(adapter);
                } else {
                    adapter = new ArrayAdapter<>(WorksElect.this,
                            android.R.layout.simple_spinner_item, new ArrayList<>());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner5.setAdapter(adapter);
                    Toast.makeText(WorksElect.this, "未找到匹配的工况", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理 Toolbar 返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish(); // 返回上一个页面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }


