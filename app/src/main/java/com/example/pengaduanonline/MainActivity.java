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

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String key1, key2;
    SPPengaduan SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button blogin = (Button) findViewById(R.id.login_button);
        Button bregister = (Button) findViewById(R.id.register_button);
        final EditText input1 = (EditText) findViewById(R.id.username);
        final EditText input2 = (EditText) findViewById(R.id.password);
        SP = new SPPengaduan(this);

        if (SP.getSPLogin() == true){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usern = input1.getText().toString();
                String password = input2.getText().toString();

                database.child("Data User").orderByChild("username").equalTo(usern).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            database.child("Data User").orderByChild("password").equalTo(password).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (dataSnapshot.exists()) {
                                        Toast.makeText(MainActivity.this, "Berhasil login!", Toast.LENGTH_SHORT).show();
                                        Intent messageIntent = new Intent(MainActivity.this, HomeActivity.class);
                                        SP.saveSPString(SPPengaduan.SP_USERNAME, usern);
                                        SP.saveSPBoolean(String.valueOf(SPPengaduan.SP_INFO), true);
                                        messageIntent.putExtra("namaUser", usern);
                                        startActivity(messageIntent);
                                        finish();
                                    }
                                    else {
                                        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                                        myAlertBuilder.setTitle("Gagal Login!");
                                        myAlertBuilder.setMessage("Username atau password anda salah. Silahkan coba lagi!");
                                        myAlertBuilder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        myAlertBuilder.show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                            myAlertBuilder.setTitle("Gagal Login!");
                            myAlertBuilder.setMessage("Username atau password anda salah. Silahkan coba lagi!");
                            myAlertBuilder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            myAlertBuilder.show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masukRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(masukRegister);
            }
        });
    }
}