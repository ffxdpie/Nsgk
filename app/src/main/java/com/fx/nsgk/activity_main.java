package com.fx.nsgk;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class activity_main extends AppCompatActivity {
    String TAG = "my-tag_main";
    private TextView  textView;
    private EditText editTextNumber;
    private String gk;
    private Workingconditiontype wkt ;
    DatabaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imageButton1 = findViewById(R.id.imageButton1);
        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        ImageButton imageButton = findViewById(R.id.imageButton);
        ImageButton imageButton5 = findViewById(R.id.imageButton5);
        editTextNumber = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);

        try {
            dbHelper = new DatabaseHelper(this);
            dbHelper.copyDatabaseFromAssets(this);
        }  finally {
            if (dbHelper != null) {
                dbHelper.close(); // 确保 dbHelper 被关闭
            }
        }

        if (savedInstanceState != null){
            String s = savedInstanceState.getString("key");
            textView.setText(s);
        }
        imageButton.setOnClickListener(v -> {
            // 创建一个 Intent，用于启动 weightActivity
            Intent intent = new Intent(activity_main.this, weightActivity.class);
            startActivity(intent); // 启动新的 Activity
        });
        imageButton1.setOnClickListener(v -> {
            // 创建一个 Intent，用于启动 weightActivity
            Intent intent = new Intent(activity_main.this, WorksElect.class);
            startActivity(intent); // 启动新的 Activity
        });


        imageButton3.setOnClickListener(v -> {
            // 创建一个 Intent，用于启动 weightActivity
            Intent intent = new Intent(activity_main.this, LocomotiveInformationActivity.class);
            startActivity(intent); // 启动新的 Activity
        });

        imageButton5.setOnClickListener(v -> {
            // 创建一个 Intent，用于启动 weightActivity
            Intent intent = new Intent(activity_main.this, Activity_Canvas.class);
            startActivity(intent); // 启动新的 Activity
        });



        imageButton2.setOnClickListener(v -> {
            // 创建一个 Intent，用于启动 weightActivity
            Intent intent = new Intent(activity_main.this, Activity_workingconditiondiagram.class);
            gk = editTextNumber.getText().toString();
            wkt = dbHelper.WorkingType(gk);
            Log.d(TAG, "gk =" +gk);
            Log.d(TAG, "wkt: "+ wkt);
            if (wkt == null){
                Toast.makeText(activity_main.this, "匹配不到该工况", Toast.LENGTH_SHORT).show();
            }else {
                int workingCondition = Integer.parseInt(gk);
                intent.putExtra("workingCondition", workingCondition);
                startActivity(intent); // 启动新的 Activity
            }
        }
        );

    }

    //数据持久化
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key",textView.getText().toString());
    }
}

