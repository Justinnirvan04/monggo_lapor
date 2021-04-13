package com.molor.monggolapor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.molor.monggolapor.model.AspirasiRequests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class aspirasi extends AppCompatActivity  {

    private DatabaseReference databaseReference;

    private EditText etDari, etKepada, etAspirasi;
    private ProgressDialog loading;
    private Button bt_save, bt_cancel, bt_choose;

    private String sPid, sPdari, sPkepada, sPaspirasi, sPimgaspirasi;

    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspirasi);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPdari = getIntent().getStringExtra("dari");
        sPkepada = getIntent().getStringExtra("kepada");
        sPaspirasi = getIntent().getStringExtra("aspirasi");


        etDari = findViewById(R.id.dari);
        etKepada = findViewById(R.id.kepada);
        etAspirasi = findViewById(R.id.subject);
        bt_save = findViewById(R.id.btnSave);
        bt_cancel = findViewById(R.id.btnCancel);

        etDari.setText(sPdari);
        etKepada.setText(sPkepada);
        etAspirasi.setText(sPaspirasi);

        if (sPid.equals("")){
            bt_save.setText("Save");
            bt_cancel.setText("Cancel");
        } else {
            bt_save.setText("Edit");
            bt_cancel.setText("Hapus");
        }

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sdari = etDari.getText().toString();
                String Skepada = etKepada.getText().toString();
                String Saspirasi = etAspirasi.getText().toString();

                if (bt_cancel.getText().equals("Save")){
                    if (Sdari.equals("")){
                        etDari.setError("Silahkan Masukkan Nama Anda!");
                        etDari.requestFocus();
                    } else if (Skepada.equals("")){
                        etKepada.setError("Masukkan Nama Orang yang Anda Tuju");
                        etKepada.requestFocus();
                    } else if (Saspirasi.equals("")){
                        etAspirasi.setError("Masukkan Aspirasi Anda!");
                        etAspirasi.requestFocus();
                    } else {
                        loading = ProgressDialog.show(aspirasi.this, null, "Please be Waiting", true, false);

                        submitUser(new AspirasiRequests(Sdari.toLowerCase(), Skepada.toLowerCase(), Saspirasi.toLowerCase()));
                    }
                } else {
                    //edit
                    if (Sdari.equals("")){
                        etDari.setError("Silahkan Masukkan Nama Anda!");
                        etDari.requestFocus();
                    } else if (Skepada.equals("")){
                        etKepada.setError("Masukkan Nama Orang yang Anda Tuju");
                        etKepada.requestFocus();
                    } else if (Saspirasi.equals("")){
                        etAspirasi.setError("Masukkan Aspirasi Anda!");
                        etAspirasi.requestFocus();
                    } else {
                        loading = ProgressDialog.show(aspirasi.this, null, "Please be Waiting", true, false);

                        editUser(new AspirasiRequests(Sdari.toLowerCase(), Skepada.toLowerCase(), Saspirasi.toLowerCase()), sPid);
                    }
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_cancel.getText().equals("Cancel")){
                    finish();
                } else {
                    databaseReference.child("Aspirasi_Request").child(sPid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(aspirasi.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(aspirasi.this, isi.class);
                            startActivity(i);
                        }
                    });
                }
            }
        });
    }

    private void editUser(AspirasiRequests aspirasiRequests, String sPid) {
        databaseReference.child("Aspirasi_Request").push().setValue(aspirasiRequests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loading.dismiss();

                etDari.setText("");
                etKepada.setText("");
                etAspirasi.setText("");

                Toast.makeText(aspirasi.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(aspirasi.this, isi.class);
                startActivity(intent);
            }
        });
    }

    private void submitUser(AspirasiRequests aspirasiRequests) {
        databaseReference.child("Aspirasi_Request").push().setValue(aspirasiRequests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loading.dismiss();

                etDari.setText("");
                etKepada.setText("");
                etAspirasi.setText("");

                Toast.makeText(aspirasi.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(aspirasi.this, isi.class);
                startActivity(intent);
            }
        });
    }
}