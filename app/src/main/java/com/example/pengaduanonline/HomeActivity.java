package com.example.pengaduanonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton tambah;
    ImageView logout;
    AdapterPengaduan adapterPengaduan;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<DataPengaduan> listPengaduan;
    RecyclerView daftar_pengaduan;
    SPPengaduan SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SP = new SPPengaduan(this);
        logout = findViewById(R.id.btn_logout);
        final TextView textUsern = (TextView) findViewById(R.id.usern);
        Intent intent1= getIntent();
        String output1 = intent1.getStringExtra("namaUser");
        String outputJudul = intent1.getStringExtra("textJudul");
        String outputTanggal = intent1.getStringExtra("textTanggal");
        String outputLokasi = intent1.getStringExtra("textLokasi");
        String outputIsi = intent1.getStringExtra("textIsi");
        textUsern.setText("Halo," + SP.getSpUsername() + "!");

        tambah = findViewById(R.id.add_button);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msg = new Intent(HomeActivity.this, AddActivity.class);
                msg.putExtra("pUsern", SP.getSpUsername());
                startActivity(msg);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SP.saveSPBoolean(String.valueOf(SP.SP_INFO), false);
                Intent signout = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(signout);
                finish();
            }
        });

        daftar_pengaduan = findViewById(R.id.daftar_pengaduan);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        daftar_pengaduan.setLayoutManager(mLayout);
        daftar_pengaduan.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void tampilData() {

        database.child("Data Pengaduan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPengaduan = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    DataPengaduan pgdn = item.getValue(DataPengaduan.class);
                    pgdn.setKey(item.getKey());
                    listPengaduan.add(pgdn);
                }
                adapterPengaduan = new AdapterPengaduan(listPengaduan, HomeActivity.this, SP.getSpUsername());
                daftar_pengaduan.setAdapter(adapterPengaduan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}