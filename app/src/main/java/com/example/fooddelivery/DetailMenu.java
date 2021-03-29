package com.example.fooddelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailMenu extends AppCompatActivity {

    private TextView tvNama, tvDeskripsi, tvQuantity, tvHarga;
    private ImageButton increment,decrement;
    private ImageView imageView;
    private Button btnSubmit;

    private int Quantity;
    private String Uri,IdMenu;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        databaseReference = FirebaseDatabase.getInstance().getReference("cart");

        tvNama = findViewById(R.id.namaDetail);
        tvDeskripsi = findViewById(R.id.deskripsiDetail);
        tvQuantity = findViewById(R.id.quantity);
        tvHarga = findViewById(R.id.hargaMenu);
        increment = findViewById(R.id.increment);
        decrement = findViewById(R.id.decrement);
        imageView = findViewById(R.id.imgDetail);
        btnSubmit = findViewById(R.id.submit);

        tvQuantity.setText("0");
        Quantity = Integer.parseInt(tvQuantity.getText().toString());

        Intent intent = getIntent();
        tvNama.setText(intent.getStringExtra("nama"));
        tvDeskripsi.setText(intent.getStringExtra("deskripsi"));
        tvHarga.setText(intent.getStringExtra("harga"));

        Uri = intent.getStringExtra("url");
        IdMenu = intent.getStringExtra("id");

        Picasso.with(this).load(Uri).into(imageView);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quantity++;
                tvQuantity.setText(String.valueOf(Quantity));
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Quantity == 0) {
                    Toast.makeText(DetailMenu.this, "Tidak Bisa Kurang Dari 0", Toast.LENGTH_SHORT).show();
                } else {
                    Quantity--;
                    tvQuantity.setText(String.valueOf(Quantity));
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(IdMenu);
            }
        });
    }

    private void addToCart(String idMenu) {
        int harga = Integer.parseInt(tvQuantity.getText().toString()) * Integer.parseInt(tvHarga.getText().toString());
        String idCart = String.valueOf(System.currentTimeMillis());
        String idCust = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DataCart cart = new DataCart(idCart, idMenu, idCust, tvNama.getText().toString(), tvQuantity.getText().toString(), String.valueOf(harga));
        databaseReference.child(idCart).setValue(cart);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(DetailMenu.this, "Barang Berhasil ditambah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailMenu.this, "Barang Gagal ditambah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}