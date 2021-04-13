package com.molor.adminmolor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    private EditText etSubject, etJudul, etLaporan;
    private ProgressDialog loading;
    private ImageView imageView;

    private String sPid, sPsubject, sPjudul, sPlaporan, sPimage;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        sPid = getIntent().getStringExtra("id");
        sPsubject = getIntent().getStringExtra("subject");
        sPjudul = getIntent().getStringExtra("judul");
        sPlaporan = getIntent().getStringExtra("laporan");
        sPimage = getIntent().getStringExtra("img");

        etSubject = findViewById(R.id.subject);
        etJudul = findViewById(R.id.judul);
        etLaporan = findViewById(R.id.isiLaporan);
        imageView = findViewById(R.id.imageLapor);

        etSubject.setText(sPsubject);
        etJudul.setText(sPjudul);
        etLaporan.setText(sPlaporan);
        Glide.with(this).load(sPimage).into(imageView);
//        Toast.makeText(MainActivity.this, "gambar tampil " + sPimage, Toast.LENGTH_SHORT).show();
    }
}