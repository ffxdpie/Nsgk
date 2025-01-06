package com.fx.nsgk.nsgk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fx.nsgk.DatabaseHelper;
import com.fx.nsgk.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorksElect extends AppCompatActivity  {

    private DatabaseHelper dbHelper;
    Spinner spinner,spinner1,spinner2,spinner3,spinner4;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workselect);
        dbHelper = new DatabaseHelper(this);
        spinner4 = findViewById(R.id.spinner4);
        // 初始化 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // 将 Toolbar 设置为 ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                spinner = findViewById(R.id.spinner);
                spinner1 = findViewById(R.id.spinner1);
                spinner2 = findViewById(R.id.spinner2);
                spinner3 = findViewById(R.id.spinner3);

                textView = findViewById(R.id.textView8);

                // rcdistance 配重伸出距离
                // Otype 支腿类型
                // Cwtype 配重类型
                // Rtype  回转类型
                int rcdistance = (int) spinner.getSelectedItemId();
                int Otype = (int) spinner1.getSelectedItemId();
                int Cwtype = (int) spinner3.getSelectedItemId();
                int Rtype = (int) spinner2.getSelectedItemId();
                if (rcdistance == 0 && Otype == 0 && Cwtype == 0 && Rtype == 0){
                    Toast.makeText(WorksElect.this, "至少选择一个", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("select", "onClick: " + rcdistance +Otype + Cwtype+ Rtype);

                List<Integer> gkValues = dbHelper.queryWork(rcdistance,Otype, Cwtype,Rtype);
                ArrayAdapter<Integer> adapter;
                if (!gkValues.isEmpty()) {
                    adapter = new ArrayAdapter<>(WorksElect.this,
                            android.R.layout.simple_spinner_item, gkValues);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner4.setAdapter(adapter);
                } else {
                    adapter = new ArrayAdapter<>(WorksElect.this,
                            android.R.layout.simple_spinner_item, new ArrayList<>());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner4.setAdapter(adapter);
                    textView.setText("检查各个选择之间是否冲突");

                }
            }
        });


        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();  // 获取选中的文本
                Toast.makeText(parent.getContext(), "选择了工况:  " + selectedItem, Toast.LENGTH_LONG).show();
                Workingconditiontype wkt = dbHelper.WorkingType(selectedItem);
                SetupChart setupChart = new SetupChart(WorksElect.this ,null, dbHelper);
                textView.setText(setupChart.workinfo(Integer.parseInt(selectedItem),wkt));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 可以处理没有选项被选中的情况
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


