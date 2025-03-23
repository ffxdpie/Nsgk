package com.fx.nsgk.loginandset;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_employee_List extends AppCompatActivity {

    Button btnEmployeelist, add;  // 定义按钮
    ListView listView;  // 定义 ListView


    protected void onResume() {
        super.onResume();
        // 页面加载时自动获取人员列表
        fetchUserList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        // 初始化按钮和 ListView
        btnEmployeelist = findViewById(R.id.btnEmployeelist);
        listView = findViewById(R.id.listView);
        add = findViewById(R.id.btnadd);
        // 添加员工
        add.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if(role.equals("管理员")){
                Intent intent = new Intent(activity_employee_List.this, activity_employee_management.class);
                startActivity(intent);
            }else {
                Toast.makeText(activity_employee_List.this, "无权限 " , Toast.LENGTH_SHORT).show();
            }

        });



        // 按钮点击事件，点击后刷新人员列表
        btnEmployeelist.setOnClickListener(v -> fetchUserList());
    }


    private void fetchUserList() {
        // 获取存储的 Token
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");

        if (token.isEmpty()) {
            Toast.makeText(activity_employee_List.this, "未登录或Token无效", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建 Retrofit 实例
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 发起请求获取用户列表
        Call<List<UserResponse>> call = apiService.getUsers(0, 200, "Bearer " + token);

        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.isSuccessful()) {
                    List<UserResponse> users = response.body();
                    if (users != null && !users.isEmpty()) {
                        // 创建适配器并设置到 ListView
                        UserAdapter userAdapter = new UserAdapter(activity_employee_List.this, users);
                        listView.setAdapter(userAdapter);

                        // 打印用户信息
                        for (UserResponse user : users) {
                            Log.d("TAG", "User Name: " + user.getName() + ", Role: " + user.getRole());
                        }
                    } else {
                        Log.d("TAG", "No users found");
                    }
                } else {
                    Log.e("TAG", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Log.e("TAG", "Request failed: " + t.getMessage());
            }
        });
    }
}
