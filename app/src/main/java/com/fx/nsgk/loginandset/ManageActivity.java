package com.fx.nsgk.loginandset;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import com.fx.nsgk.R;

public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);  // 设置布局

        // 初始化按钮
        Button btnAddEmployee = findViewById(R.id.btnAddEmployee);
        Button btnViewEmployees = findViewById(R.id.btnViewEmployees);
        Button btnAddTool = findViewById(R.id.btnAddtool);
        Button btnViewTools = findViewById(R.id.btnViewtools);

        // 添加员工
        btnAddEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(ManageActivity.this, EmployeeManagementActivity.class);
            startActivity(intent);
        });

        // 查看员工按钮点击事件
        btnViewEmployees.setOnClickListener(v -> {
            // 跳转到人员列表查看页面
            Intent intent = new Intent(ManageActivity.this, EmployeeListActivity.class);
            startActivity(intent);
        });

        // 添加工具
        btnAddTool.setOnClickListener(v -> {
            Intent intent = new Intent(ManageActivity.this, activity_tool_management.class);
            startActivity(intent);
        });

        // 查看工具按钮点击事件
        btnViewTools.setOnClickListener(v -> {
            // 跳转到人员列表查看页面
            Intent intent = new Intent(ManageActivity.this, activity_tools_list.class);
            startActivity(intent);
        });
    }
}
