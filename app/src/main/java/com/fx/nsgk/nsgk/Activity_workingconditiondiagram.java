package com.fx.nsgk.nsgk;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.fx.nsgk.DatabaseHelper;
import com.fx.nsgk.R;
import com.fx.nsgk.R.id;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_workingconditiondiagram extends AppCompatActivity {
    String TAG = "mytag_aw";
    private ViewPager2 ViewPager2;
    private final String[] titles = {"吊臂长度和起重量", "角度和起重量", "测试"};
    int workingConditionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workingconditiondiagram);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ImageButton imageButton = findViewById(id.imageButton4);
        TabLayout tab_layout = findViewById(R.id.tab_layout);
        ViewPager2 = findViewById(R.id.viewPager);

        //初始化控件
        // 获取 Intent
        Intent intent = getIntent();
        // 提取传递过来的 数据
        workingConditionId = intent.getIntExtra("workingCondition", 2273);  // 如果不存在，默认为 2273
        int angle = 0;
        int dist = 7;

        Log.d(TAG, "workingConditionId: "+ workingConditionId);
        //设置文本内容
        TextView workinginfo = findViewById(R.id.workinginfo);
        Workingconditiontype wkt = dbHelper.WorkingType(String.valueOf(workingConditionId));
        SetupChart setupChart = new SetupChart(this ,null, dbHelper);
        workinginfo.setText(setupChart.workinfo(workingConditionId,wkt));
        // 获取 Toolbar 并设置标题
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("工况 " + workingConditionId);  // 动态设置标题
        setSupportActionBar(toolbar); // 将 Toolbar 设置为 ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 显示返回按钮




//   ViewPager2设置
        ViewPager2.setAdapter(new FragmentStateAdapter(this) {

            @NonNull
            @Override
            public androidx.fragment.app.Fragment createFragment(int position) {
                String title = titles[position];
                return Fragment.newInstance(title , workingConditionId , angle , dist);
            }

            @Override
            public int getItemCount() {
                return titles.length;
            }
        });

        //viewPager和tab_layout关联在一起
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tab_layout, ViewPager2,
                (tab, position) -> tab.setText(titles[position]));
        tabLayoutMediator.attach();
        //简而言之，TabLayoutMediator 的作用是使 TabLayout 的标签页与 ViewPager2 中的页面滑动同步，
        // 而 attach() 方法是使这种同步关联生效的关键调用。这样设置后，用户界面的交互性和整体体验会更连贯、更直观。


        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //设置viewPager选中当前页
                ViewPager2.setCurrentItem(tab.getPosition(),false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            int clicknumber = 0;
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                clicknumber ++;
                if (clicknumber == 10){
                    Toast.makeText(Activity_workingconditiondiagram.this, "石书源", Toast.LENGTH_SHORT).show();
                    clicknumber = 0;
                };


            }
        });


        // 复制文本
        imageButton.setOnClickListener(v -> {
            // 获取剪贴板管理器：
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建ClipData对象
            ClipData clip = ClipData.newPlainText("simple text", workinginfo.getText());
            // 将ClipData内容放到系统剪贴板上。
            clipboard.setPrimaryClip(clip);
            // 可选：给用户反馈信息
            Toast.makeText(Activity_workingconditiondiagram.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();

        });
    }



    //数据持久化
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("key",numberEditText.getText().toString());
//    }

    //右上角返回
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // 处理 Toolbar 返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish(); // 返回上一个页面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

