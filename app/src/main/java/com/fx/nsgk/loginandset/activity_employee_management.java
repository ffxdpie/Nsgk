package com.fx.nsgk.loginandset;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.UserRequest;
import com.fx.nsgk.Response.UserResponse;
import com.fx.nsgk.databinding.ActivityEmployeeManagementBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_employee_management extends AppCompatActivity {
    private ActivityEmployeeManagementBinding binding;

    private Spinner spinnerRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 设置确认按钮点击事件
        binding.btnSubmit.setOnClickListener(v -> {
            // 获取输入的用户名、密码和角色
            String username = binding.etUsername.getText().toString();
            String password = binding.etPassword.getText().toString();
            String role = binding.spinnerRole.getSelectedItem().toString();
            String position = binding.etposition.getText().toString();
            String phoneNumber = binding.etphoneNumber.getText().toString();
            String birthDate = binding.etbirthDate.getText().toString();
            Log.d("role", "onCreate: "+role);

            // 校验输入
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity_employee_management.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 获取存储的 Token
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String token = sharedPreferences.getString("access_token", "");

            if (token.isEmpty()) {
                Toast.makeText(activity_employee_management.this, "未登录或Token无效", Toast.LENGTH_SHORT).show();
                return;
            }

            // 创建用户请求体
            UserRequest userRequest = new UserRequest(username, password, role, position, phoneNumber, birthDate);

            // 创建 Retrofit 实例
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

            // 发送请求
            Call<UserResponse> call = apiService.createUser("Bearer " + token, userRequest);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        UserResponse userResponse = response.body();
                        if (userResponse != null) {
                            // 如果响应成功，显示成功的提示信息
                            Toast.makeText(activity_employee_management.this, "用户创建成功: " + userResponse.getName(), Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } else {
                        // 如果请求失败，显示错误信息
                        Toast.makeText(activity_employee_management.this, "请求失败: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    // 如果请求失败，显示网络错误信息
                    Toast.makeText(activity_employee_management.this, "请求错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}

