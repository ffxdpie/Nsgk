package com.fx.nsgk.loginandset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fx.nsgk.Response.ToolResponse;

import java.util.List;

public class ToolAdapter extends BaseAdapter {

    private Context context;
    private List<ToolResponse> tools;

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

        return convertView;
    }
}

