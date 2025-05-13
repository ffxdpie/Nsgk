package com.fx.nsgk.loginandset;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.PopupMenu;

import androidx.annotation.NonNull;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.ToolResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToolAdapter extends BaseAdapter {

    private final Context context;
    private final List<ToolResponse> tools;

    public ToolAdapter(Context context, List<ToolResponse> tools) {
        this.context = context;
        this.tools = tools;
    }


    @Override
    public int getCount() {
        return tools.size();
    }

    @Override
    public Object getItem(int position) {
        return tools.get(position);
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

        ToolResponse tool = tools.get(position);
        nameTextView.setText(tool.getName());
        roleTextView.setText(tool.getDescription());

        convertView.setOnClickListener(v -> {
            Toast.makeText(context, "详细信息 "+nameTextView.getText() , Toast.LENGTH_SHORT).show();
            Intent intent = getIntent(activity_tool_details.class,tool);
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
                    if(role.equals("工具员")){
                        Toast.makeText(context, "编辑 " + nameTextView.getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent(activity_tool_details.class,tool);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "无权限 " , Toast.LENGTH_SHORT).show();
                    }

                    return true;
                } else if (item.getItemId() == R.id.menu_delete_option) {
                    if(role.equals("工具员")){
                        Log.d("nameTextView.getText()", nameTextView.getText().toString());
                        deleteTool(position);
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
    private Intent getIntent(Class<?> targetActivity,ToolResponse tool) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtra("tool_name", tool.getName());
        intent.putExtra("tool_description", tool.getDescription());
        intent.putExtra("tool_setup_time", tool.getSetup_time());
        intent.putExtra("tool_expiry_time", tool.getExpiry_time());
        intent.putExtra("tool_model",tool.getModel());
        intent.putExtra("tool_quantity", tool.getQuantity());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }


    private void deleteTool(int position) {

        ToolResponse tool = tools.get(position);
        String toolName = tool.getName();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");
        if (token.isEmpty()) {
            Toast.makeText(context, "未登录或Token无效", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ToolResponse> call = apiService.deleteTool(toolName,"Bearer " + token);
        call.enqueue(new Callback<ToolResponse>() {
            @Override
            public void onResponse(Call<ToolResponse> call, Response<ToolResponse> response) {
                if (response.isSuccessful()) {
                    ToolResponse toolResponse = response.body();
                    if (toolResponse != null) {
                        // 如果响应成功，显示成功的提示信息
                        Toast.makeText(context, "工具删除成功: " + toolResponse.getName(), Toast.LENGTH_LONG).show();
                        // 从列表中移除工具并刷新适配器
                        tools.remove(position);
                        notifyDataSetChanged();
                    }
                } else {
                    // 如果请求失败，显示错误信息
                    Toast.makeText(context, "请求失败: " + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ToolResponse> call, Throwable t) {
                // 如果请求失败，显示网络错误信息
                Toast.makeText(context, "请求错误: " + t.getMessage(), Toast.LENGTH_SHORT).show();


            }

        });
    }


}

