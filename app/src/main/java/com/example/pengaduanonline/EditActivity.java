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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final EditText detJudul = (EditText) findViewById(R.id.ed_judul);
        final EditText detTanggal = (EditText) findViewById(R.id.ed_tanggal);
        final EditText detLokasi = (EditText) findViewById(R.id.ed_lokasi);
        final EditText detIsi = (EditText) findViewById(R.id.ed_isi);
        Button bSave = (Button) findViewById(R.id.btn_simpan);

        Intent NI = getIntent();
        String output1 = NI.getStringExtra("edUser");
        String dJudul = NI.getStringExtra("edJudul");
        String dTanggal = NI.getStringExtra("edTanggal");
        String dLokasi = NI.getStringExtra("edLokasi");
        String dIsi = NI.getStringExtra("edIsi");
        String key = NI.getStringExtra("edKey");

        detJudul.setText(dJudul);
        detTanggal.setText(dTanggal);
        detLokasi.setText(dLokasi);
        detIsi.setText(dIsi);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editJudul = detJudul.getText().toString();
                String editTanggal = detTanggal.getText().toString();
                String editLokasi = detLokasi.getText().toString();
                String editIsi = detIsi.getText().toString();

                database.child("Data Pengaduan").child(key).setValue(new DataPengaduan(output1, editJudul, editTanggal, editLokasi, editIsi)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent homeIntent = new Intent(EditActivity.this, HomeActivity.class);
                        homeIntent.putExtra("namaUser", output1);
                        startActivity(homeIntent);

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

                            builder = new NotificationCompat.Builder(EditActivity.this, CHANNEL_ID);
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
                                .setContentText("Berhasil mengupdate data!");

                        Notification notification = builder.build();

                        int notificationCode = (int) (Math.random() * 1000);
                        manager.notify(notificationCode, notification);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditActivity.this, "Data gagal disimpan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}