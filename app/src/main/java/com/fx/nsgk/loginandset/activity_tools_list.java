package com.fx.nsgk.loginandset;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.ToolResponse;
import com.fx.nsgk.Response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_tools_list extends AppCompatActivity {
    Button btntoollist,add ;  // 定义按钮
    ListView listView;  // 定义 ListView
    @Override
    protected void onResume() {
        super.onResume();
        fetchToolList();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_list);
        // 初始化按钮和 ListView
        btntoollist = findViewById(R.id.btntoollist);
        listView = findViewById(R.id.listView);
        add = findViewById(R.id.btnadd);


        // 添加员工
        add.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if(role.equals("工具员")){
                Intent intent = new Intent(this, activity_tool_management.class);
                startActivity(intent);
            }else {
                Toast.makeText(activity_tools_list.this, "无权限", Toast.LENGTH_SHORT).show();
            }
        });
        btntoollist.setOnClickListener(v -> fetchToolList());
    }



    private void fetchToolList() {
        // 获取存储的 Token
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");

        if (token.isEmpty()) {
            Toast.makeText(activity_tools_list.this, "未登录或Token无效", Toast.LENGTH_SHORT).show();
            return;
        }


        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ToolResponse>> call = apiService.getTools(0, 200, "Bearer " + token);

        call.enqueue(new Callback<List<ToolResponse>>() {
            @Override
            public void onResponse(Call<List<ToolResponse>> call, Response<List<ToolResponse>> response) {
                if (response.isSuccessful()) {
                    List<ToolResponse> tools = response.body();
                    if (tools != null && !tools.isEmpty()) {
                        // 创建适配器并设置到 ListView
                        ToolAdapter toolAdapter = new ToolAdapter(activity_tools_list.this, tools);
                        listView.setAdapter(toolAdapter);

                        // 打印用户信息
                        for (ToolResponse tool : tools) {
                            Log.d("TAG", "tools Name: " + tool.getName() + ", Description: " + tool.getDescription());
                        }
                    } else {
                        Log.d("TAG", "No users found");
                    }
                } else {
                    Log.e("TAG", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ToolResponse>> call, Throwable t) {
                Log.e("TAG", "Request failed: " + t.getMessage());
            }
        });
    }
    }
