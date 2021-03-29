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

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
   private Context context;
    private List<Barang> lst;

    public ImageAdapter(Context context, List<Barang> lst) {
        this.context = context;
        this.lst = lst;
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
     public TextView tvName, tvDekripsi;
     public ImageView imageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDekripsi = itemView.findViewById(R.id.tv_deskripsi);
            imageView = itemView.findViewById(R.id.img_view);
        }
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.images_item, parent, false);
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Barang brang = lst.get(position);
        holder.tvName.setText(brang.getDeskripsi());
        holder.tvDekripsi.setText(brang.getImageUrl());
        Picasso.with(context).load(brang.getId()).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailBarang.class);
                intent.putExtra("id", brang.getId());
                intent.putExtra("nama", brang.getNama());
                intent.putExtra("deksripsi", brang.getDeskripsi());
                intent.putExtra("url", brang.getImageUrl());
                context.startActivity(intent);
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent1 = new Intent(context, register.class);
                intent1.putExtra("id", brang.getId());
                intent1.putExtra("nama", brang.getNama());
                intent1.putExtra("deksripsi", brang.getDeskripsi());
                intent1.putExtra("url", brang.getImageUrl());
                context.startActivity(intent1);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


}
