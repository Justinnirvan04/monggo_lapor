package com.molor.monggolapor;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Pengumuman extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    private EditText edtPengirim, edtTopik, edtInformasi, edtDitujukan;
    private ImageView imgPengumuman;

    private String sId, sPengirim, sTopik, sInformasi, sDitujukan, sImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        sId = getIntent().getStringExtra("id");
        sPengirim = getIntent().getStringExtra("pengirim");
        sTopik = getIntent().getStringExtra("topik");
        sInformasi = getIntent().getStringExtra("informasi");
        sDitujukan = getIntent().getStringExtra("ditujukan");
        sImage = getIntent().getStringExtra("image");

        edtInformasi = findViewById(R.id.edtInformasi);
        edtTopik = findViewById(R.id.edtTopik);
        edtPengirim = findViewById(R.id.edtPengirim);
        edtDitujukan = findViewById(R.id.etDiTujukam);
        imgPengumuman = findViewById(R.id.profilePic);

        edtPengirim.setText(sPengirim);
        edtTopik.setText(sTopik);
        edtInformasi.setText(sInformasi);
        edtDitujukan.setText(sDitujukan);
//        Toast.makeText(this, sImage, Toast.LENGTH_SHORT).show();
        Glide.with(this).load(sImage).into(imgPengumuman);

    }

}