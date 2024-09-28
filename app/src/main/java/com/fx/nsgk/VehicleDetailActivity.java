package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class VehicleDetailActivity extends AppCompatActivity {

    String vehicleName;
    private TextView textView12;
    private TextView textView13;
    private TextView textView14;
    private TextView textView15;
    private DatabaseHelper db;
    String TAG= "veh";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        textView15 = findViewById(R.id.textView15);
        db = new DatabaseHelper(this);



        //初始化控件
        // 获取 Intent
        Intent intent = getIntent();
        // 提取传递过来的 数据
        vehicleName = intent.getStringExtra("vehicleName");
        Vehicle vehicle = db.getVehicle(vehicleName);
        textView12.setText(vehicle.getName());
        textView13.setText(vehicle.getWeight());
        textView14.setText(vehicle.getHeight());
        textView15.setText(vehicle.getLength());


        Log.d(TAG, "vehicleName: " + vehicleName);
}
}