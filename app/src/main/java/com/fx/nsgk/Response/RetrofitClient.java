package com.fx.nsgk.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.uiuuyr.top/")  // 设置 API 的基础 URL
//                   .baseUrl("http://192.168.1.4:800/")  // 设置 API 的基础 URL
                    .addConverterFactory(GsonConverterFactory.create())  // 使用 Gson 转换器
                    .build();
        }
        return retrofit;
    }
}

