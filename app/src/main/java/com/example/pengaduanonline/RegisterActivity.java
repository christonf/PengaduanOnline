package com.example.pengaduanonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText regUsern, regPass;
    Button btn_register;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsern = findViewById(R.id.reguser);
        regPass = findViewById(R.id.regpass);
        btn_register = findViewById(R.id.register2_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUsern = regUsern.getText().toString();
                String getPass = regPass.getText().toString();

                if (getUsern.isEmpty()) {
                    regUsern.setError("Masukkan nama");
                }
                else if (getPass.isEmpty()) {
                    regPass.setError("Password masih kosong");
                }
                else {
                    database.child("Data User").orderByChild("username").equalTo(getUsern).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snshot) {
                            if (snshot.exists()) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                alert.setTitle("Gagal Registrasi!");
                                alert.setMessage("Username sudah terambil. Silahkan masukkan username lain!");
                                alert.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }
                            else {
                                database.child("Data User").push().setValue(new DataUser(getUsern, getPass)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this, "Berhasil melakukan registrasi!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Gagal melakukan registrasi!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}