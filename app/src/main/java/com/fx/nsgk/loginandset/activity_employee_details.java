package com.fx.nsgk.loginandset;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fx.nsgk.R;
import com.fx.nsgk.Response.ApiService;
import com.fx.nsgk.Response.RetrofitClient;
import com.fx.nsgk.Response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.fx.nsgk.databinding.ActivityEmployeeDetailsBinding;

public class activity_employee_details extends AppCompatActivity {

    private ActivityEmployeeDetailsBinding binding;

    String name;




    protected void onResume() {
        super.onResume();
        //        绑定控件 viewBinding
        binding = ActivityEmployeeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 获取传递的数据
        if (getIntent() != null) {
            name = getIntent().getStringExtra("user_name");
        }

        Refresh_employee_details(name);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Employeedetails), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void Refresh_employee_details(String name) {

        // 获取存储的 Token
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("access_token", "");

        if (token.isEmpty()) {
            Toast.makeText(activity_employee_details.this, "未登录或Token无效", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建 Retrofit 实例
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // 发起请求获取用户信息
        Call<UserResponse> call = apiService.getUser(name, "Bearer " + token);

        call.enqueue(new Callback<UserResponse>() {
            UserResponse userResponse;
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    userResponse = response.body();}
                if (userResponse != null) {
                    binding.etUsername.setText(userResponse.getName());
                    binding.etRole.setText(userResponse.getRole());
                    binding.etposition.setText(userResponse.getPosition());
                    binding.etphoneNumber.setText(userResponse.getPhone_number());
                    binding.etbirthDate.setText(userResponse.getBirth_date());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }


}