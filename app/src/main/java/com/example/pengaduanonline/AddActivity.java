package com.example.pengaduanonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {
    EditText ad_judul, ad_tanggal, ad_lokasi, ad_isi;
    Button btn_tambah;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();

        ad_judul = findViewById(R.id.ad_judul);
        ad_tanggal = findViewById(R.id.ad_tanggal);
        ad_lokasi = findViewById(R.id.ad_lokasi);
        ad_isi = findViewById(R.id.ad_isi);
        btn_tambah = findViewById(R.id.btn_tambah);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String output2 = intent.getStringExtra("pUsern");
                String getAdJudul = ad_judul.getText().toString();
                String getAdTanggal = ad_tanggal.getText().toString();
                String getAdLokasi = ad_lokasi.getText().toString();
                String getAdIsi = ad_isi.getText().toString();

                if (getAdJudul.isEmpty()) {
                    ad_judul.setError("Masukkan judul!");
                }
                else if (getAdTanggal.isEmpty()) {
                    ad_tanggal.setError("Masukkan tanggal!");
                }
                else if (getAdLokasi.isEmpty()) {
                    ad_lokasi.setError("Masukkan lokasi!");
                }
                else if (getAdIsi.isEmpty()) {
                    ad_isi.setError("Masukkan isi laporan!");
                }
                else {
                    database.child("Data Pengaduan").push().setValue(new DataPengaduan(output2, getAdJudul, getAdTanggal, getAdLokasi, getAdIsi))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder;

                            Context context = getApplicationContext();
                            Resources res = context.getResources();

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                String CHANNEL_ID = "christo_chanel";

                                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "ChristoChannel",
                                        NotificationManager.IMPORTANCE_HIGH);
                                channel.setDescription("Christo channel description");
                                manager.createNotificationChannel(channel);

                                builder = new NotificationCompat.Builder(AddActivity.this, CHANNEL_ID);
                            }
                            else {
                                builder = new NotificationCompat.Builder(context);
                            }

                            PendingIntent action = PendingIntent.getActivity(context, 0, new Intent(context, AddActivity.class),
                                    PendingIntent.FLAG_CANCEL_CURRENT);

                            builder.setContentIntent(action)
                                    .setSmallIcon(R.drawable.ic_notif_new)
                                    .setTicker("Small text!")
                                    .setAutoCancel(true)
                                    .setContentTitle("Berhasil!")
                                    .setContentText("Berhasil menambah data!");

                            Notification notification = builder.build();

                            int notificationCode = (int) (Math.random() * 1000);
                            manager.notify(notificationCode, notification);

                            Intent msgInt = new Intent(AddActivity.this, HomeActivity.class);
                            msgInt.putExtra("namaUser", output2);
                            msgInt.putExtra("textJudul", getAdJudul);
                            msgInt.putExtra("textTanggal", getAdTanggal);
                            msgInt.putExtra("textLokasi", getAdLokasi);
                            msgInt.putExtra("textIsi", getAdIsi);
                            startActivity(msgInt);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Gagal menyimpan pengaduan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}