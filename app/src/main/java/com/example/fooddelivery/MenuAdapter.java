package com.example.fooddelivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private Context ctx;
    private List<DataMenu> dataMenuList;

    public MenuAdapter(Context ctx, List<DataMenu> dataMenuList) {
        this.ctx = ctx;
        this.dataMenuList = dataMenuList;
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        public TextView tvNama, tvHarga;
        public ImageView imageView;

        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_name);
            tvHarga = itemView.findViewById(R.id.tv_deskripsi);
            imageView = itemView.findViewById(R.id.img_view);
        }
    }
    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.images_item, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        DataMenu dataMenu = dataMenuList.get(position);
        holder.tvNama.setText(dataMenu.getNama());
        holder.tvHarga.setText(dataMenu.getHarga());

        Picasso.with(ctx).load(dataMenu.getImgUrl()).fit().centerCrop().into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, DetailMenu.class);
                intent.putExtra("id", dataMenu.getId());
                intent.putExtra("nama", dataMenu.getNama());
                intent.putExtra("deskripsi", dataMenu.getDeskripsi());
                intent.putExtra("harga", dataMenu.getHarga());
                intent.putExtra("url", dataMenu.getImgUrl());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMenuList.size();
    }
}
