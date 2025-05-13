package com.fx.nsgk.loginandset;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fx.nsgk.R;
import com.fx.nsgk.databinding.ActivityToolDetailsBinding;

public class activity_tool_details extends AppCompatActivity {

    private ActivityToolDetailsBinding binding;
    protected void onResume() {
        super.onResume();
        //        绑定控件 viewBinding
        binding = ActivityToolDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 获取传递的数据
        if (getIntent() != null) {
            String name = getIntent().getStringExtra("tool_name");
            String description = getIntent().getStringExtra("tool_description");
            String setupTime = getIntent().getStringExtra("tool_setup_time");
            String expiryTime = getIntent().getStringExtra("tool_expiry_time");
            String toolModel = getIntent().getStringExtra("tool_model");
            String toolQuantity =getIntent().getStringExtra("tool_quantity");


            binding.etToolname.setText(name);
            binding.etuse.setText(description);
            binding.etexpiryTime.setText(expiryTime);
            binding.etsetupTime.setText(setupTime);
            binding.etmodel.setText(toolModel);
            binding.etquantity.setText(toolQuantity);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_tool_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }
}