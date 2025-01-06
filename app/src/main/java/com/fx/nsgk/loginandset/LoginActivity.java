package com.fx.nsgk.loginandset;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.TokenResponse;
import com.fx.nsgk.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
//    private static final int PERMISSION_REQUEST_CODE = 1;
    private EditText etUsername, etPassword;

    // 权限请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 检查权限并请求
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    },
//                    PERMISSION_REQUEST_CODE);
//        }

        // 初始化界面元素
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        // 检查 SharedPreferences 中是否存储了有效的 Token
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        if (username != null && password != null){
            etUsername.setText(username);
            etPassword.setText(password);

        }
        //这段代码可以验证身份,以后有用
//        String accessToken = sharedPreferences.getString("access_token", null);
//        String tokenType = sharedPreferences.getString("token_type", null);
//        if (accessToken != null && tokenType != null) {
//            // 构造授权头部
//            String authHeader = tokenType + " " + accessToken;
//
//            // 使用 token 获取用户信息
//            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//            Call<UserResponse> call = apiService.Userinfo(authHeader);
//            call.enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                    if (response.isSuccessful()) {
//                        UserResponse userResponse = response.body();
//                        if (userResponse != null) {
//                            // 用户信息存在，跳转到主页面
//                            Intent intent = new Intent(LoginActivity.this, ManageActivity.class);
//                            Toast.makeText(LoginActivity.this, userResponse.getName(), Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                            finish();
//                        }
//                    } else {
//                        // 用户信息不存在或 Token 无效，继续登录
//                        Toast.makeText(LoginActivity.this, "Token 无效，重新登录", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    Toast.makeText(LoginActivity.this, "网络错误，请检查网络连接", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }


        // 设置登录按钮点击事件//
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入的用户名和密码
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // 校验用户名和密码
                if (username.isEmpty() || password.isEmpty()) {
                    // 如果用户名或密码为空，提示错误
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                } else {
                    // 创建 Retrofit 实例
                    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

                    // 调用 getToken 方法发送请求，使用输入框内容替代硬编码的用户名和密码
                    Call<TokenResponse> call = apiService.getToken(username, password);
                    call.enqueue(new Callback<TokenResponse>() {
                        @Override
                        public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                            if (response.isSuccessful()) {
                                TokenResponse tokenResponse = response.body();
                                if (tokenResponse != null) {
                                    // 获取 token 并显示
                                    String accessToken = tokenResponse.getAccess_token();
                                    String tokenType = tokenResponse.getToken_type();
                                    // 存储 Token 到 SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", username); // 存储 账户
                                    editor.putString("password", password);    // 存储 密码

                                    editor.putString("access_token", accessToken); // 存储 token
                                    editor.putString("token_type", tokenType);    // 存储 token 类型
                                    editor.apply();
//                                    Toast.makeText(LoginActivity.this, "Access Token: " + accessToken, Toast.LENGTH_LONG).show();

                                    // 登录成功，跳转到主界面
                                    Intent intent = new Intent(LoginActivity.this, ManageActivity.class); // 请替换为实际主界面的 Activity 名称
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // 请求失败，显示错误信息
                                Toast.makeText(LoginActivity.this, "检查密码和网络", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TokenResponse> call, Throwable t) {
                            // 网络请求失败
                            Toast.makeText(LoginActivity.this, "检查密码和网络: ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "权限已授予", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
