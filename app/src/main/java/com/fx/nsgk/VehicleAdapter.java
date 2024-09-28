package com.fx.nsgk;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {
    private Context context;
    private List<Vehicle> vehicleList;
    String TAG= "veh";

    public VehicleAdapter(Context context, List<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.tvName.setText(vehicle.getName());
        holder.tvWeight.setText(vehicle.getWeight());
        holder.tvLength.setText(vehicle.getLength());
        holder.tvHeight.setText(vehicle.getHeight());
        // 加载图片 (如果你使用Glide或Picasso)
        // Glide.with(context).load(vehicle.getImage()).into(holder.ivVehicleImage);

        Button btnDetail = holder.itemView.findViewById(R.id.button5);
        btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, VehicleDetailActivity.class);
            intent.putExtra("vehicleName", vehicle.getName());  // 传递name
            context.startActivity(intent);
            Log.d(TAG, "veh =" + vehicle.getName() );

        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvWeight, tvLength, tvHeight;
        ImageView ivVehicleImage;


        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvLength = itemView.findViewById(R.id.tvLength);
            tvHeight = itemView.findViewById(R.id.tvHeight);
        }
    }
}
