package com.fx.nsgk.loginandset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fx.nsgk.Response.UserResponse;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<UserResponse> users;

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

        return convertView;
    }
}
