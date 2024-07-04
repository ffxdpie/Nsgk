package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class weightActivity extends AppCompatActivity {

    private EditText editTextText;
    private EditText editTextText1;
    private EditText editTextText2;
    private Spinner spinner;
    private TextView textViewResult;
    private DatabaseHelper dbHelper;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        editTextText = findViewById(R.id.editTextText);
        editTextText1 = findViewById(R.id.editTextText1);
        editTextText2 = findViewById(R.id.editTextText2);
        spinner = findViewById(R.id.spinner);
        textViewResult = findViewById(R.id.textViewResult);
        dbHelper = new DatabaseHelper(this);
        table = findViewById(R.id.tableLayout);

        // 初始化 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // 将 Toolbar 设置为 ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // 显示返回按钮
        findViewById(R.id.button).setOnClickListener(v -> {
            String inputText = editTextText.getText().toString();
            String inputText1 = editTextText1.getText().toString();
            String inputText2 = editTextText2.getText().toString();
            // 初始化 spinner
            ArrayAdapter<Object> adapter = new ArrayAdapter<>(weightActivity.this,
                    android.R.layout.simple_spinner_item, new ArrayList<>()); // 初始为空列表
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            // 检查输入框是否为空
            if (inputText.isEmpty() || inputText1.isEmpty() || inputText2.isEmpty()) {
                Toast.makeText(weightActivity.this, "请填写完整的信息", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // 解析输入的距离和重量
                double distance = Double.parseDouble(inputText);
                String angleStr = inputText1;  // 获取角度输入
                double weight = Double.parseDouble(inputText2);
                // 转换字符串angle为整数，因为角度通常表示为整数
                int angle = Integer.parseInt(angleStr);

                // 检查distance是否在7到24.5之间
                if (distance < 7 || distance > 24.5 || angle < 0 || angle > 90 || weight < 0 || weight > 160) {
                    Toast.makeText(this, "输入数据超出有效范围", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 如果所有输入都有效，执行后续逻辑
                Toast.makeText(weightActivity.this, "查询到工况", Toast.LENGTH_SHORT).show();


                // 查询匹配的 ID
                List<Integer> ids = dbHelper.queryIdsForWorkingDistance(distance, angleStr, weight);
                if (!ids.isEmpty()) {
                    table.removeAllViews(); // 清空表格以用于新数据
                    // 如果查询结果不为空，则:
                    for (int id : ids) {
                        WorkingData workingData = dbHelper.getWorkingDistanceAngle0ById(id);
                        if (workingData != null) {
                            updateUIWithWorkingData(workingData);
                            //更新 spinner
                            adapter.add(workingData.working);
                        } else {
                            Toast.makeText(weightActivity.this, "未找到匹配的IDs", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // 如果查询结果为空，则显示提示信息
                    Toast.makeText(weightActivity.this, "未找到匹配的ID", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                // 处理解析数字时的异常
                Toast.makeText(weightActivity.this, "数据不正确", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                // 处理范围错误
                Toast.makeText(weightActivity.this, "检查数据范围", Toast.LENGTH_SHORT).show();
            }
            textViewResult.setText("工况列表(点击工况详情查看详细数据)");
        });

//        findViewById(R.id.button2).setOnClickListener(v -> {
//            // 检查输入框是否为空
//            if (spinner.getAdapter() == null || spinner.getAdapter().getCount() == 0) {
//                Toast.makeText(weightActivity.this, "请先输入数据", Toast.LENGTH_SHORT).show();
//            } else {
//                // Spinner不为空，有绑定数据..就是选择工况id的那个,获取 Spinner 当前选中的项的 ID
//                int selectedId = (int) spinner.getSelectedItem();
//                // 使用选中的 ID 从数据库获取数据
//                WorkingData workingData = dbHelper.getWorkingDistanceAngle0ById(selectedId);
//                if (workingData != null) {
//                    table.removeAllViews(); // 清空表格以用于新数据
//                    updateUIWithWorkingData(workingData);
//                } else {
//                    Toast.makeText(weightActivity.this, "在数据库中没有找到id: " + selectedId, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            // 检查输入框是否为空
            if (spinner.getAdapter() == null || spinner.getAdapter().getCount() == 0) {
                Toast.makeText(weightActivity.this, "请先输入数据", Toast.LENGTH_SHORT).show();
            } else {
                // Spinner不为空，有绑定数据..就是选择工况id的那个,获取 Spinner 当前选中的项的 ID
                int selectedId = (int) spinner.getSelectedItem();
                // 使用选中的 ID 从数据库获取数据
                //WorkingData workingData = dbHelper.getWorkingDistanceAngle0ById(selectedId);
                //int workingCondition = workingData.working; // 假设前四位是工况
                // 创建 Intent 并启动新的 Activity
                Intent intent = new Intent(weightActivity.this, Activity_workingconditiondiagram.class);
                intent.putExtra("workingCondition",  selectedId);
                startActivity(intent);
            }
        });


//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish(); // 返回上一个页面
//            }
//        });
    }

    @SuppressLint("DefaultLocale")
    private void updateUIWithWorkingData(WorkingData workingData) {
        // 表格或其他视图也需要更新
        // 创建表头
        if (table.getChildCount() == 0) {
            // 创建表头
            TableRow headerRow = new TableRow(this);
            addTextToRowWithStyle(headerRow, "工况");
            addTextToRowWithStyle(headerRow, "吊臂伸出长度");
            addTextToRowWithStyle(headerRow, "角度0");
            table.addView(headerRow);
        }
        // 创建数据行
        TableRow dataRow = new TableRow(this);
        addTextToRowWithData(dataRow, String.valueOf(workingData.working));
        addTextToRowWithData(dataRow, String.format("%.2f", workingData.distance));
        addTextToRowWithData(dataRow, String.format("%.2f", workingData.angle0));
        table.addView(dataRow);
    }
    // 辅助方法：添加文本到行并设置样式
    private void addTextToRowWithStyle(TableRow row, String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(5, 5, 5, 5);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 1;  // 每个单元格均分宽度
        tv.setLayoutParams(params);
        tv.setBackgroundResource(R.drawable.cell_shape); // 设置背景为自定义drawable，用于绘制竖线
        row.addView(tv);
    }

    // 辅助方法：添加数据到行
    private void addTextToRowWithData(TableRow row, String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(5, 5, 5, 5);
        tv.setGravity(Gravity.CENTER);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 1;  // 每个单元格均分宽度
        tv.setLayoutParams(params);
        tv.setBackgroundResource(R.drawable.cell_shape); // 同上
        row.addView(tv);
    }

    //这个函数是左上角返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理 Toolbar 返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish(); // 返回上一个页面
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
