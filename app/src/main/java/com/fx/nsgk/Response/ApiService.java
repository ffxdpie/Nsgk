package com.fx.nsgk.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

// 定义接口
public interface ApiService {

    // 登录 定义 POST 请求
    @FormUrlEncoded
    @POST("users/token")  // 这是你要发送请求的路径
    Call<TokenResponse> getToken(
            @Field("username") String username,  // 发送的字段
            @Field("password") String password   // 发送的字段
    );
    // 添加用户
    @POST("users")
    Call<UserResponse> createUser(
            @Header("Authorization") String authorization,  // 传递 Bearer token
            @Body UserRequest userRequest  // 传入请求体
    );

    // 获取用户列表
    @GET("users")  // 获取用户列表的接口
    Call<List<UserResponse>> getUsers(
            @Query("skip") int skip,  // 分页参数 skip
            @Query("limit") int limit,  // 分页参数 limit
            @Header("Authorization") String authorization  // 传递 Bearer token
    );

    // 获取用户信息
    @GET("users/{name}")
    Call<UserResponse> getUser(
            @Path("name") String userName,  // 修改为通过 name 获取
            @Header("Authorization") String authHeader
    );
    // 更新用户信息
    @PUT("users/{user_name}")
    Call<UserResponse> PutUser(
            @Path("name") String userName,
            @Header("Authorization") String authHeader
    );
    //删除用户
    @DELETE("users/{user_name}")
    Call<UserResponse> deleteuser(
            @Path("name") String userName,
            @Header("Authorization") String authHeader
    );

    // 使用token获取用户信息
    @GET("users/token/me")
    Call<UserResponse> Userinfo(
            @Header("Authorization") String authHeader
    );

    //添加工具
    @POST("tools")
    Call<ToolResponse> createTool(
            @Header("Authorization") String authorization,  // 传递 Bearer token
            @Body ToolRequest toolRequest  // 传入请求体
    );

    //获取所有工具
    @GET("tools")  // 获取用户列表的接口
    Call<List<ToolResponse>> getTools(
            @Query("skip") int skip,  // 分页参数 skip
            @Query("limit") int limit,  // 分页参数 limit
            @Header("Authorization") String authorization  // 传递 Bearer token
    );

    //更新工具
    @PUT("tools/{tool_id}")
    Call<ToolResponse> PutTool(
            @Path("name") String toolName,
            @Header("Authorization") String authHeader
    );

    //删除工具
    @DELETE
    Call<ToolResponse> Delete(
            @Path("name") String toolName,
            @Header("Authorization") String authHeader
    );

}
