package com.fx.nsgk.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeInfo {

    private Context context;

    // 构造函数接受 Context
    public MeInfo(Context context) {
        this.context = context;
    }

    // 方法用于获取用户信息，使用回调接口传递结果
    public void getMe(MeInfoCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
        if (token.isEmpty()) {
            Toast.makeText(context, "未登录或Token无效", Toast.LENGTH_SHORT).show();
            if (callback != null) {
                callback.onFailure(new Exception("Token无效"));
            }
            return;
        }

        // 创建 Retrofit 实例
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 发送请求
        Call<UserResponse> call = apiService.userinfo("Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        // 如果响应成功，显示成功的提示信息
                        if (callback != null) {
                            callback.onSuccess(userResponse);
                        }
                    } else {
                        if (callback != null) {
                            callback.onFailure(new Exception("用户信息为空"));
                        }
                    }
                } else {
                    // 如果请求失败，显示错误信息
                    Toast.makeText(context, "请求失败: " + response.message(), Toast.LENGTH_SHORT).show();
                    if (callback != null) {
                        callback.onFailure(new Exception("请求失败: " + response.message()));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // 如果请求失败，显示网络错误信息
                Toast.makeText(context, "请求错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }
}
