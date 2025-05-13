package com.fx.nsgk.loginandset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends BaseAdapter {

    private final Context context;
    private final List<UserResponse> users;


    public UserAdapter(Context context, List<UserResponse> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView nameTextView = convertView.findViewById(android.R.id.text1);
        TextView roleTextView = convertView.findViewById(android.R.id.text2);

        UserResponse user = users.get(position);
        nameTextView.setText(user.getName());
        roleTextView.setText(user.getRole());

        convertView.setOnClickListener(v -> {
            Toast.makeText(context, "详细信息 "+nameTextView.getText() , Toast.LENGTH_SHORT).show();
            Intent intent = getIntent(activity_employee_details.class,user);
            context.startActivity(intent);

        });

        convertView.setOnLongClickListener(v -> {
            // 创建 PopupMenu 对象，并将其锚定到当前视图（v）
            PopupMenu popupMenu = new PopupMenu(context, v);
            // 为 PopupMenu 加载菜单资源文件
            popupMenu.getMenuInflater().inflate(R.menu.menu_employee_options, popupMenu.getMenu());
            // 设置菜单项点击事件监听器
            popupMenu.setOnMenuItemClickListener(item -> {
                SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                String role = sharedPreferences.getString("role", "");
                if (item.getItemId() == R.id.menu_renew_option) {
                    if(role.equals("管理员")){
                        Toast.makeText(context, "编辑 " + nameTextView.getText(), Toast.LENGTH_SHORT).show();
                        // 登录成功，跳转到主界面
                        Intent intent = getIntent(activity_employee_edit.class,user);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "无权限 " , Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else if (item.getItemId() == R.id.menu_delete_option) {
                    if(role.equals("管理员")){
                        deleteUser(position);
                        Toast.makeText(context, "删除 " + nameTextView.getText(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "无权限 " , Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } return false;
            });
            // 显示菜单
            popupMenu.show();
            return true;
        });
        return convertView;
    }
    @NonNull
    private Intent getIntent(Class<?> targetActivity, UserResponse user) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtra("user_name", user.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


    private void deleteUser(int position) {
        UserResponse user = users.get(position);
        String userName = user.getName();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
        if (token.isEmpty()) {
            Toast.makeText(context, "未登录或Token无效", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<UserResponse> call = apiService.deleteuser(userName,"Bearer " + token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        // 如果响应成功，显示成功的提示信息
                        Toast.makeText(context, "人员删除成功: " + userResponse.getName(), Toast.LENGTH_LONG).show();
                        // 从列表中移除工具并刷新适配器
                        users.remove(position);
                        notifyDataSetChanged();
                    }
                } else {
                    // 如果请求失败，显示错误信息
                    Toast.makeText(context, "请求失败: " + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // 如果请求失败，显示网络错误信息
                Toast.makeText(context, "请求错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();


            }

        });
    }
}
