package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.fx.nsgk.VehicleDetailActivity;
import com.fx.nsgk.databinding.ActivityVehicleDetailBinding;


public class VehicleDetailActivity extends AppCompatActivity {
    private ActivityVehicleDetailBinding binding;
    String vehicleName;

    private DatabaseHelper db;
    String TAG= "veh";


    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        绑定控件 viewBinding
        binding = ActivityVehicleDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DatabaseHelper(this);

        //初始化控件
        // 获取 Intent
        Intent intent = getIntent();
        // 提取传递过来的 数据
        vehicleName = intent.getStringExtra("vehicleName");
        Vehicle vehicle = db.getVehicle(vehicleName);
        binding.dataName.setText(vehicle.getName());
        binding.dataOneWeight.setText(vehicle.getOneWeight() + " 吨");
        binding.datazxjweight.setText(vehicle.getZxjWeight()+ " 吨");
        binding.dataTransformerWeight.setText(vehicle.getQybyqWeight()+ " 吨");
        binding.dataCarLength.setText(vehicle.getCarLength()+ " MM");
        binding.dataLength.setText(vehicle.getLength()+ " MM");
        binding.datavehiclewidth.setText(vehicle.getVehicleWidth()+ " 吨");
        binding.datawidth.setText(vehicle.getWidth()+ " MM");
        binding.dataweight1.setText(vehicle.getWeight1()+ " 吨");
        binding.dataweight2.setText(vehicle.getWeight2()+ " 吨");
        binding.dataweight3.setText(vehicle.getWeight3()+ " 吨");
        binding.dataweight4.setText(vehicle.getWeight4()+ " 吨");
        binding.dataheight.setText(vehicle.getHeight()+ " MM");
        binding.dataheight1.setText(vehicle.getHeight1()+ " MM");
        binding.dataheight2.setText(vehicle.getHeight2()+ " MM");
        binding.dataheight3.setText(vehicle.getHeight3()+ " MM");
        binding.dataheight4.setText(vehicle.getHeight4()+ " MM");
        binding.dataimage.setText(vehicle.getImage());
        binding.datamodel.setText(vehicle.getModel());

        Log.d(TAG, "vehicleName: " + vehicleName);
}
}