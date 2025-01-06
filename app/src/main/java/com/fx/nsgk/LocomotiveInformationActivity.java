package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocomotiveInformationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VehicleAdapter adapter;
    private DatabaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locomotive_information);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);

//        // 获取所有车辆数据
//        List<Vehicle> vehicleList = db.getAllVehicles();
//
//        // 设置适配器
//        adapter = new VehicleAdapter(this, vehicleList);
//        recyclerView.setAdapter(adapter);
        setAdapter();

        //设置搜索按钮的作用
        Button button = findViewById(R.id.button6);
        button.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.editTextText3);
            String name = textView.getText().toString();
            // 判断 name 是否为空
            if (name.isEmpty()) {
                // 如果 name 为空，可以选择显示一个提示或者不执行后续操作
                setAdapter();
                return;  // 或者处理空字符串的逻辑
            }

            // 获取查找车辆数据
            List<Vehicle> vehicleList1 = db.getOneVehicles(name);
            if (vehicleList1 != null && !vehicleList1.isEmpty()) {
                // 设置适配器
                adapter = new VehicleAdapter(this, vehicleList1);
                recyclerView.setAdapter(adapter);
            } else {
                setAdapter();
                Toast.makeText(LocomotiveInformationActivity.this, "还没有该车型的数据", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setAdapter(){
        db = new DatabaseHelper(this);

        // 获取所有车辆数据
        List<Vehicle> vehicleList = db.getAllVehicles();

        // 设置适配器
        adapter = new VehicleAdapter(this, vehicleList);
        recyclerView.setAdapter(adapter);
    }
}