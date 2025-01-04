package com.fx.nsgk;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);  // 设置布局

        // 初始化按钮
        Button btnAddEmployee = findViewById(R.id.btnAddEmployee);
        Button btnViewEmployees = findViewById(R.id.btnViewEmployees);

        // 查看员工按钮点击事件
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageActivity.this, EmployeeManagementActivity.class);
                startActivity(intent);
            }
        });

        // 添加员工
        btnViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到人员列表查看页面
                Intent intent = new Intent(ManageActivity.this, EmployeeListActivity.class);
                startActivity(intent);
            }
        });
    }
}
