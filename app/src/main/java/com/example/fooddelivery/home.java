package com.example.fooddelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {
private EditText editText;
private Button btn1,btn2,btn3,searchbtn;
private RecyclerView recyclerView;
private DatabaseReference databaseReference;
private List<Barang> lbrg;
private ImageView imageView;
private ImageAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        lbrg = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText =view.findViewById(R.id.search_bar);
        btn1 = view.findViewById(R.id.Kategori_Makanan);
        btn2 = view.findViewById(R.id.Kategori_minuman);
        btn3 = view.findViewById(R.id.Kategori_Desert);
        searchbtn = view.findViewById(R.id.btnsearch);
        imageView = view.findViewById(R.id.iconsearch);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                searchbtn.setVisibility(View.VISIBLE);

            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchData(editText.getText().toString());
            }
        });
    }
    private void SearchData(String textSearch) {
        lbrg.clear();
        Toast.makeText(getContext(), "Data Dicari", Toast.LENGTH_SHORT).show();
        Query query = databaseReference.orderByChild("deskripsi").startAt(textSearch).endAt(textSearch + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Barang brg = item.getValue(Barang.class);
                    lbrg.add(brg);
                }
                viewData(lbrg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showAll() {
        databaseReference = FirebaseDatabase.getInstance().getReference("data-barang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Barang brg = item.getValue(Barang.class);
                    lbrg.add(brg);
                }
                viewData(lbrg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData(List list) {
        adapter = new ImageAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        showAll();
    }

}