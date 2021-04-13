package com.molor.adminmolor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormAspirasi extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private EditText etDari, etKepada, etAspirasi;
    private ProgressDialog loading;

    private String sPid, sPdari, sPkepada, sPaspirasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_aspirasi);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        sPid = getIntent().getStringExtra("id");
        sPdari = getIntent().getStringExtra("dari");
        sPkepada = getIntent().getStringExtra("kepada");
        sPaspirasi = getIntent().getStringExtra("aspirasi");

        etDari = findViewById(R.id.dari);
        etKepada = findViewById(R.id.kepada);
        etAspirasi = findViewById(R.id.subject);

        etDari.setText(sPdari);
        etKepada.setText(sPkepada);
        etAspirasi.setText(sPaspirasi);
    }
}