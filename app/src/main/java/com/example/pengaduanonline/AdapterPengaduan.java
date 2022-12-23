package com.example.pengaduanonline;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdapterPengaduan extends RecyclerView.Adapter<AdapterPengaduan.MyViewHolder>{
    private List<DataPengaduan> mList;
    private Activity activity;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String username;

    public AdapterPengaduan(List<DataPengaduan> mList, Activity activity, String username) {
        this.mList = mList;
        this.activity = activity;
        this.username = username;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.pengaduan, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataPengaduan data = mList.get(position);

        holder.judulLap.setText(data.getJudul());
        holder.tanggalLap.setText(data.getUsername());

        if (mList.get(position).getUsername().equals(username)){
            holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.child("Data Pengaduan").child(data.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(activity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setMessage("Apakah yakin mau menghapus " + data.getJudul() + "?");
                    builder.show();
                }
            });
        }
        else {
            holder.btn_hapus.setVisibility(View.INVISIBLE);
        }

//        database.child("Data Pengaduan").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot shot) {
//                if (shot.exists()) {
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        if (mList.get(position).getUsername().equals(username)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(v.getContext(), DetailActivity.class);
                    newIntent.putExtra("dUser", data.getUsername());
                    newIntent.putExtra("dJudul", data.getJudul());
                    newIntent.putExtra("dTanggal", data.getTanggal());
                    newIntent.putExtra("dLokasi", data.getLokasi());
                    newIntent.putExtra("dIsi", data.getIsi());
                    newIntent.putExtra("dKey", data.getKey());
                    v.getContext().startActivity(newIntent);
                }
            });
        }
        else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder al = new AlertDialog.Builder(v.getContext());
                    al.setTitle("Bukan Aduanmu!");
                    al.setMessage("Aduan yang anda pilih bukan merupakan aduan anda. Silahkan pilih aduan yang lainnya.");
                    al.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    al.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView judulLap, tanggalLap;
        CardView card_hasil;
        ImageView btn_hapus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judulLap = itemView.findViewById(R.id.judul_laporan);
            tanggalLap = itemView.findViewById(R.id.tanggal_laporan);
            btn_hapus = itemView.findViewById(R.id.btn_hapus);
        }
    }
}
