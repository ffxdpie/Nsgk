package com.fx.nsgk.loginandset;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.MeInfo;
import com.fx.nsgk.Response.MeInfoCallback;
import com.fx.nsgk.Response.UserResponse;

public class activity_manage extends AppCompatActivity {
    private MeInfo meInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);  // 设置布局

        // 初始化按钮
//        Button btnAddEmployee = findViewById(R.id.btnAddEmployee);
        Button btnViewEmployees = findViewById(R.id.btnViewEmployees);
        Button btnViewTools = findViewById(R.id.btnViewtools);

        meInfo = new MeInfo(this);
        // 获取用户信息
        meInfo.getMe(new MeInfoCallback() {
            @Override
            public void onSuccess(UserResponse userResponse) {
                // 处理成功的用户信息
                String userName = userResponse.getName();
                String userRole = userResponse.getRole();
                // 例如，显示在 UI 上
                Toast.makeText(activity_manage.this, "欢迎, " + userName+"\n 身份:" + userRole , Toast.LENGTH_SHORT).show();
                // 存储 Token 到 SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("role", userRole); // 存储 权限
                editor.apply();
            }

            @Override
            public void onFailure(Throwable t) {
                // 处理失败的情况
                Toast.makeText(activity_manage.this, "获取用户信息失败: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                // TODO: 根据需要处理失败，例如重试请求或导航到登录页面
            }
        });



        // 查看员工按钮点击事件
        btnViewEmployees.setOnClickListener(v -> {
            // 跳转到人员列表查看页面
            Intent intent = new Intent(activity_manage.this, activity_employee_List.class);
            startActivity(intent);
        });


        // 查看工具按钮点击事件
        btnViewTools.setOnClickListener(v -> {
            // 跳转到人员列表查看页面
            Intent intent = new Intent(activity_manage.this, activity_tools_list.class);
            startActivity(intent);
        });
    }
}
