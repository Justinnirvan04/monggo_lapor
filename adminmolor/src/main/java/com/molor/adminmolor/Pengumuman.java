package com.molor.adminmolor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.molor.adminmolor.model.PengumumanRequests;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Pengumuman extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    private EditText edtPengirim, edtTopik, edtInformasi, edtDitujukan;
    private Button btn_save, btn_cancel;
    private ProgressDialog loading;
    private ImageView imgPengumuman;

    private Uri imageUri;
    private String sId, sPengirim, sTopik, sInformasi, sDitujukan, sImage, SImagePengumuman;


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
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btnUpload);

        edtPengirim.setText(sPengirim);
        edtTopik.setText(sTopik);
        edtInformasi.setText(sInformasi);
        edtDitujukan.setText(sDitujukan);
        Toast.makeText(this, sImage, Toast.LENGTH_SHORT).show();
        Glide.with(this).load(sImage).into(imgPengumuman);

        imgPengumuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        if (sId.equals("")) {
            btn_save.setText("Submit");
            btn_cancel.setText("Cancel");
        } else {
            btn_save.setText("Edit");
            btn_cancel.setText("Delete");
        }

        findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Spengirim = edtPengirim.getText().toString();
                String Stopik = edtTopik.getText().toString();
                String Sinformasi = edtInformasi.getText().toString();
                String Sditujukan = edtDitujukan.getText().toString();

                if (btn_save.getText().equals("Submit")) {
                    if (Spengirim.equals("")) {
                        edtPengirim.setError("Silahkan Masukkan Nama Anda!");
                        edtPengirim.requestFocus();
                    } else if (Stopik.equals("")) {
                        edtTopik.setError("Silahkan Masukkan Topik Anda!");
                        edtTopik.requestFocus();
                    } else if (Sinformasi.equals("")) {
                        edtInformasi.setError("Silahkan Masukkan Informasi Anda!");
                        edtInformasi.requestFocus();
                    } else if (Sditujukan.equals("")) {
                        edtDitujukan.setError("Silahkan Masukkan Orang yang Anda Tuju!");
                        edtDitujukan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(Pengumuman.this, null, "Please wait", true, false);

                        submitAdmin(new PengumumanRequests(Spengirim.toLowerCase(), Stopik.toLowerCase(), Sinformasi.toLowerCase(), Sditujukan.toLowerCase(), SImagePengumuman));
                    }
                } else {
                    // perintah edit
                    if (Spengirim.equals("")) {
                        edtPengirim.setError("Silahkan Masukkan Nama Anda!");
                        edtPengirim.requestFocus();
                    } else if (Stopik.equals("")) {
                        edtTopik.setError("Silahkan Masukkan Topik Anda!");
                        edtTopik.requestFocus();
                    } else if (Sinformasi.equals("")) {
                        edtInformasi.setError("Silahkan Masukkan Informasi Anda!");
                        edtInformasi.requestFocus();
                    } else if (Sditujukan.equals("")) {
                        edtDitujukan.setError("Silahkan Masukkan Orang yang Anda Tuju!");
                        edtDitujukan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(Pengumuman.this, null, "Please wait", true, false);

                        editAdmin(new PengumumanRequests(Spengirim.toLowerCase(), Stopik.toLowerCase(), Sinformasi.toLowerCase(), Sditujukan.toLowerCase(), SImagePengumuman), sId);
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_cancel.getText().equals("Cancel")){
                    finish();
                } else {
                    databaseReference.child("Pengumuman_Request").child(sId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Pengumuman.this, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Pengumuman.this, List_Pengumuman.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    private void editAdmin(PengumumanRequests pengumumanRequests, String sId) {
        databaseReference.child("Pengumuman_Request").child(sId).setValue(pengumumanRequests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loading.dismiss();

                edtPengirim.setText("");
                edtTopik.setText("");
                edtInformasi.setText("");
                edtDitujukan.setText("");

                Toast.makeText(Pengumuman.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Pengumuman.this, List_Pengumuman.class);
                startActivity(intent);
            }
        });
    }

    private void submitAdmin(PengumumanRequests pengumumanRequests) {
        databaseReference.child("Pengumuman_Request").push().setValue(pengumumanRequests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loading.dismiss();

                edtPengirim.setText("");
                edtTopik.setText("");
                edtInformasi.setText("");
                edtDitujukan.setText("");

                Toast.makeText(Pengumuman.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Pengumuman.this, List_Pengumuman.class);
                startActivity(intent);
            }
        });
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(android.content.Intent.createChooser(intent, "Pilih Foto"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData() !=null){
            imageUri = data.getData();
            imgPengumuman.setImageURI(imageUri);
            uploadImagetoFirebaseStrorage();
        }
    }

    private void uploadImagetoFirebaseStrorage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload Image");
        progressDialog.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/*" + randomKey);

        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        SImagePengumuman = task.getResult().toString();
                        Toast.makeText(Pengumuman.this, "upload image" + SImagePengumuman, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Pengumuman.this, "upload image" + e, Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progresspercent = (100.00 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Progress : "+ (int) progresspercent + "%");
            }
        });
    }
}