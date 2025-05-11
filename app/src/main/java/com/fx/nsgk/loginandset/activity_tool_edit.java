package com.fx.nsgk.loginandset;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.ToolRequest;
import com.fx.nsgk.Response.ToolResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_tool_edit extends AppCompatActivity {
    private EditText etToolname, etuse, etsetup_time, etexpiry_time, etmodel,etquantity;
    Button btnSubmit;
    String name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_edit);


        // 初始化界面元素
        etToolname = findViewById(R.id.etToolname);
        etuse = findViewById(R.id.etuse);
        etsetup_time = findViewById(R.id.etsetup_time);
        etexpiry_time =findViewById(R.id.etexpiry_time);

        etmodel =findViewById(R.id.etmodel);
        etquantity = findViewById(R.id.etquantity);
        btnSubmit = findViewById(R.id.btnSubmit);

        // 获取传递的数据
        if (getIntent() != null) {
            name = getIntent().getStringExtra("tool_name");
            String description = getIntent().getStringExtra("tool_description");
            String setupTime = getIntent().getStringExtra("tool_setup_time");
            String expiryTime = getIntent().getStringExtra("tool_expiry_time");
            String toolModel = getIntent().getStringExtra("tool_model");
            String toolQuantity =getIntent().getStringExtra("tool_quantity");


            etToolname.setText(name);
            etuse.setText(description);
            etsetup_time.setText(setupTime);
            etexpiry_time.setText(expiryTime);
            etmodel.setText(toolModel);
            etquantity.setText(toolQuantity);
        }

        // 设置确认按钮点击事件
        btnSubmit.setOnClickListener(v -> {
            // 获取输入的用户名、密码和角色
            String toolname = etToolname.getText().toString();
            String use = etuse.getText().toString();
            String setup_time = etsetup_time.getText().toString();
            String expiry_time = etexpiry_time.getText().toString();
            String model = etmodel.getText().toString();
            String quantity = etquantity.getText().toString();

        // 校验输入
        if (toolname.isEmpty() || use.isEmpty() ||setup_time.isEmpty() ||expiry_time.isEmpty()) {
            Toast.makeText(activity_tool_edit.this, "请输入", Toast.LENGTH_SHORT).show();
            return;
        }
        // 获取存储的 Token
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
            if (token.isEmpty()) {
                Toast.makeText(activity_tool_edit.this, "未登录或Token无效", Toast.LENGTH_SHORT).show();
                return;
            }

            // 创建用户请求体
            ToolRequest toolRequest = new ToolRequest(toolname, use, setup_time , expiry_time, model, quantity);

            // 创建 Retrofit 实例
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            //putTool 更新工具
            Call<ToolResponse> call = apiService.putTool(name,toolRequest,"Bearer " + token);
            call.enqueue(new Callback<ToolResponse>() {
                @Override
                public void onResponse(@NonNull Call<ToolResponse> call, @NonNull Response<ToolResponse> response) {
                    if (response.isSuccessful()) {
                        ToolResponse toolResponse = response.body();
                        if (toolResponse != null) {
                            // 如果响应成功，显示成功的提示信息
                            Toast.makeText(activity_tool_edit.this, "工具修改成功: " + toolResponse.getName(), Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(activity_tool_edit.this, activity_tools_list.class);
//                            startActivity(intent);
                            Intent resultIntent = new Intent();
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    } else {
                        // 如果请求失败，显示错误信息
                        Toast.makeText(activity_tool_edit.this, "请求失败: " + response.message(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ToolResponse> call, Throwable t) {
                    // 如果请求失败，显示网络错误信息
                    Toast.makeText(activity_tool_edit.this, "请求错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });

    });
    }
}