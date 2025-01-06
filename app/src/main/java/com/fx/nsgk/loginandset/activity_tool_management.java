package com.fx.nsgk.loginandset;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.ToolRequest;
import com.fx.nsgk.Response.ToolResponse;
import com.fx.nsgk.Response.UserRequest;
import com.fx.nsgk.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_tool_management extends AppCompatActivity {
    private EditText etUsername, etuse, ettime;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_management);

        // 初始化界面元素
        etUsername = findViewById(R.id.etUsername);
        etuse = findViewById(R.id.etuse);
        ettime = findViewById(R.id.etTime);
        btnSubmit = findViewById(R.id.btnSubmit);

        // 设置确认按钮点击事件
        btnSubmit.setOnClickListener(v -> {
            // 获取输入的用户名、密码和角色
            String username = etUsername.getText().toString();
            String use = etuse.getText().toString();
            String time = ettime.getText().toString();

            // 校验输入
            if (username.isEmpty() || use.isEmpty() ||time.isEmpty()) {
                Toast.makeText(activity_tool_management.this, "请输入", Toast.LENGTH_SHORT).show();
                return;
            }

            // 获取存储的 Token
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String token = sharedPreferences.getString("access_token", "");

            if (token.isEmpty()) {
                Toast.makeText(activity_tool_management.this, "未登录或Token无效", Toast.LENGTH_SHORT).show();
                return;
            }

            // 创建用户请求体
            ToolRequest toolRequest = new ToolRequest(username, use, time);

            // 创建 Retrofit 实例
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

            // 发送请求
            Call<ToolResponse> call = apiService.createTool("Bearer " + token, toolRequest);
            call.enqueue(new Callback<ToolResponse>() {
                @Override
                public void onResponse(Call<ToolResponse> call, Response<ToolResponse> response) {
                    if (response.isSuccessful()) {
                        ToolResponse toolResponse = response.body();
                        if (toolResponse != null) {
                            // 如果响应成功，显示成功的提示信息
                            Toast.makeText(activity_tool_management.this, "工具添加成功: " + toolResponse.getName(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // 如果请求失败，显示错误信息
                        Toast.makeText(activity_tool_management.this, "请求失败: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ToolResponse> call, Throwable t) {
                    // 如果请求失败，显示网络错误信息
                    Toast.makeText(activity_tool_management.this, "请求错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}