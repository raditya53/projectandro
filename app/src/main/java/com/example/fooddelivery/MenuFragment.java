package com.example.fooddelivery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.TextView;
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


public class MenuFragment extends Fragment {

    private EditText etSearch;
    private Button catMakanan,catMinuman,catDesert;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<DataMenu> menuList;
    private ImageView imgSearch;
    private MenuAdapter adapter;
    private TextView addmenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // dont u dare to put anything in this field
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearch = view.findViewById(R.id.search_bar);
        catMakanan = view.findViewById(R.id.Kategori_Makanan);
        catMinuman = view.findViewById(R.id.Kategori_minuman);
        catDesert = view.findViewById(R.id.Kategori_Desert);
        addmenu = view.findViewById(R.id.addData);

        imgSearch = view.findViewById(R.id.iconsearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setVisibility(View.VISIBLE);
                imgSearch.setVisibility(View.INVISIBLE);
            }
        });

        addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddMenu.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        menuList = new ArrayList<>();

    }

    @Override
    public void onResume() {
        super.onResume();
        menuList.clear();
        showAllMenu();
    }

    private void showAllMenu() {
        databaseReference = FirebaseDatabase.getInstance().getReference("data-barang");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    DataMenu dataMenu = item.getValue(DataMenu.class);
                    menuList.add(dataMenu);
                }
                viewMenu(menuList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void viewMenu(List list) {
        adapter = new MenuAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }


}