package com.fx.nsgk;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class SetupChart {

    static void XYset(LineChart chart1, ArrayList<Double> results){ // X 轴设置
        // 将 ArrayList 中的每个 Double 元素转换为 float 并填充到数组
        List<Entry> entries = new ArrayList<>();
        float[] weights = new float[results.size()];
        for (int i = 0; i < results.size(); i++) {
            weights[i] = results.get(i).floatValue();
        }

        float[] distances = new float[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 24.5F};

        for (int i = 0; i < distances.length; i++) {
            if (weights[i] != 0) {  // 不添加非0权重的数据点
                entries.add(new Entry(distances[i], weights[i]));
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, "工况表");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);
        LineData lineData = new LineData(dataSet);
        chart1.setData(lineData);

        XAxis xAxis = chart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1); // 每隔 1 单位显示一个刻度
        xAxis.setGranularityEnabled(true); // 启用粒度控制
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisMinimum(7); // 设置 X 轴的最小值
        xAxis.setAxisMaximum(25); // 设置 X 轴的最大值
        xAxis.setLabelCount(19, true); // 强制显示所有标签

        // 隐藏右侧的 Y 轴
        YAxis rightAxis = chart1.getAxisRight();
        rightAxis.setAxisLineColor(Color.BLACK);
        rightAxis.setEnabled(false); // 不显示右侧 Y 轴

        // 设置描述
        Description description = new Description();
        description.setText("纵坐标: 起重量 横坐标: 吊臂伸出距离");
//        description.setTextColor(Color.RED); // 设置文本颜色为蓝色
//        description.setTextSize(10);
//        description.setPosition(0, 0);      // 指定位置
        chart1.setDescription(description);
        chart1.invalidate(); // 刷新图表

    }


    //工况信息
    @SuppressLint("SetTextI18n")
    static String workinfo(int workingConditionId, Workingconditiontype wkt){
        String rt = wkt.rotationDescription;
        String cw = wkt.counterweightDescription;
        String ot = wkt.outriggerDescription;
        String rc = wkt.counterweightReach;
        return "工况:              "+workingConditionId + "\n"+ rt +"\n"+cw+"\n"+ot+"\n"+rc;
    }

}


