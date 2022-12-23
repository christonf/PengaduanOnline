package com.example.pengaduanonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView detJudul = (TextView) findViewById(R.id.judul_lap);
        final TextView detTanggal = (TextView) findViewById(R.id.tanggal_lap);
        final TextView detLokasi = (TextView) findViewById(R.id.lokasi_lap);
        final TextView detIsi = (TextView) findViewById(R.id.isi_lap);
        Button bEdit = (Button) findViewById(R.id.btn_edit);

        Intent newI= getIntent();
        String output1 = newI.getStringExtra("dUser");
        String oJudul = newI.getStringExtra("dJudul");
        String oTanggal = newI.getStringExtra("dTanggal");
        String oLokasi = newI.getStringExtra("dLokasi");
        String oIsi = newI.getStringExtra("dIsi");
        String oKey = newI.getStringExtra("dKey");

        detJudul.setText(oJudul);
        detTanggal.setText(oTanggal);
        detLokasi.setText(oLokasi);
        detIsi.setText(oIsi);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nInt = new Intent(DetailActivity.this, EditActivity.class);
                nInt.putExtra("edUser", output1);
                nInt.putExtra("edJudul", oJudul);
                nInt.putExtra("edTanggal", oTanggal);
                nInt.putExtra("edLokasi", oLokasi);
                nInt.putExtra("edIsi", oIsi);
                nInt.putExtra("edKey", oKey);
                startActivity(nInt);
            }
        });
    }
}