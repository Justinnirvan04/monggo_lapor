package com.molor.monggolapor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.molor.monggolapor.model.Masyarakat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int CHOOSE_IMAGE= 101;
    EditText etNama, etEmail, etNik, etTlpWA, etAlamat, etFb, etIg, etTwt;
    Spinner spJekel, spStatus;
    TextView tvVerifikasiEmail, tvJenkelError, tvStatusError;
    Uri uriProfileImage;
    CircleImageView imageView;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth mAuth;
    FirebaseUser User;
    ConstraintLayout editProfile;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mAuth = FirebaseAuth.getInstance();

        etNama = findViewById(R.id.edtNamaP);
        etEmail = findViewById(R.id.edtEmailP);
        etNik = findViewById(R.id.edtNisP);
        etAlamat = findViewById(R.id.edtAlamatP);
        etFb = findViewById(R.id.edtURLFbP);
        etIg = findViewById(R.id.edtIgUserP);
        etTwt = findViewById(R.id.edtTwUserP);
        etTlpWA = findViewById(R.id.edtTeleponP);
        tvJenkelError = findViewById(R.id.tvJenkelError);
        tvStatusError = findViewById(R.id.tvStatusError);
        tvVerifikasiEmail = findViewById(R.id.textView6);
        spJekel = findViewById(R.id.spJenkelP);
        spStatus = findViewById(R.id.spStatusP);
        imageView = findViewById(R.id.imgChoose);
        editProfile = findViewById(R.id.EditProfileActivity);

        ArrayAdapter<CharSequence> arrayAdapterGender = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        arrayAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJekel.setAdapter(arrayAdapterGender);
        spJekel.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> arrayAdapterStatus = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        arrayAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(arrayAdapterStatus);
        spStatus.setOnItemSelectedListener(this);

        User = mAuth.getCurrentUser();
        if (User.isEmailVerified()){
            Toast.makeText(this, "Email is verified", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog dialog = new AlertDialog.Builder(EditProfile.this)
                    .setTitle("Email Verification")
                    .setMessage("Email belum diverifikasi, silahkan verifikasi terlebih dahulu. Belum menerima Email?")
                    .setNegativeButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Kirim Ulang", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            User.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Email berhasil dikirim", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).create();
            dialog.show();
        }
        progressBar = findViewById(R.id.progressBar);
        loadUserInformation();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        findViewById(R.id.buttonsave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String nik = etNik.getText().toString();
                String gender = spJekel.getSelectedItem().toString();
                String status = spStatus.getSelectedItem().toString();
                String alamat = etAlamat.getText().toString();

                saveUserInformation(new Masyarakat(etEmail.getText().toString(), etNama.getText().toString(), etNik.getText().toString(), profileImageUrl));
            }
        });

    }

    private void loadUserInformation() {
        FirebaseUser User = mAuth.getCurrentUser();
        if (User != null){
            if (User.getPhotoUrl() != null){
                Toast.makeText(getApplicationContext(),User.getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();
                Log.d("tag", "PhotoLink" + User.getPhotoUrl().toString());
            }
            DatabaseReference masyarakat = FirebaseDatabase.getInstance().getReference("user");
            DatabaseReference childMasyarakat = masyarakat.child(mAuth.getCurrentUser().getUid());
            childMasyarakat.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        etEmail.setText(snapshot.child("email").getValue().toString());
                        etNama.setText(snapshot.child("nama").getValue().toString());
                        etNik.setText(snapshot.child("nik").getValue().toString());
                        etAlamat.setText(snapshot.child("alamat").getValue().toString());
                        etTlpWA.setText(snapshot.child("wa").getValue().toString());
                        etIg.setText(snapshot.child("ig").getValue().toString());
                        etFb.setText(snapshot.child("fb").getValue().toString());
                        etTwt.setText(snapshot.child("twt").getValue().toString());
                        if (snapshot.child("gender").getValue().toString().equals("laki-laki")) {
                            spJekel.setSelection(1);
                        } else if (snapshot.child("gender").getValue().toString().equals("perempuan")) {
                            spJekel.setSelection(2);
                        }
                        if (snapshot.child("status").getValue().toString().equals("WNI")) {
                            spStatus.setSelection(1);
                        } else if (snapshot.child("status").getValue().toString().equals("WNA")) {
                            spStatus.setSelection(2);
                        }
                        Glide.with(getApplicationContext())
                                .load(snapshot.child("imgUri").getValue().toString()).into(imageView);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),""+e, Toast.LENGTH_SHORT);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void saveUserInformation(Masyarakat masyarakat) {
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String nik = etNik.getText().toString();
        String gender = spJekel.getSelectedItem().toString();
        String status = spStatus.getSelectedItem().toString();
        String alamat = etAlamat.getText().toString();
        String wa = etTlpWA.getText().toString();
        String ig = etIg.getText().toString();
        String fb = etFb.getText().toString();
        String twiter = etTwt.getText().toString();

        if (nama.isEmpty()){
            etNama.setError("Nama Harus di isi");
            etNama.requestFocus();
            return;
        }
        if (email.isEmpty()){
            etEmail.setError("email Harus di isi");
            etEmail.requestFocus();
            return;
        }
        if (nik.isEmpty()){
            etNik.setError("nik Harus di isi");
            etNik.requestFocus();
            return;
        }
        if (gender.equals("- Pilih Gender -")){
            tvJenkelError.setError("Gender Harus di isi");
            tvJenkelError.requestFocus();
            Snackbar.make(editProfile,"Mohon Mengisi Gender", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
        if (status.equals("- Pilih Status -")){
            tvStatusError.setError("Status Harus di isi");
            tvStatusError.requestFocus();
            Snackbar.make(editProfile,"Mohon Mengisi Status",BaseTransientBottomBar.LENGTH_SHORT).show();
        }
        if (alamat.isEmpty()){
            etAlamat.setError("alamat Harus di isi");
            etAlamat.requestFocus();
            return;
        }
        if (!fb.contains("Facebook")&& !fb.isEmpty()){
            etFb.setError("Mohon Masukan url dengan benar");
            etFb.requestFocus();
        }
        if (!ig.contains("Instagram")&& !ig.isEmpty()){
            etIg.setError("Mohon Masukan url dengan benar");
            etIg.requestFocus();
        }
        if (!twiter.contains("Twiter")&& !twiter.isEmpty()){
            etTwt.setError("Mohon Masukan url dengan benar");
            etTwt.requestFocus();
        }
        FirebaseUser User = mAuth.getCurrentUser();

        if (User!= null){
           progressBar.setVisibility(View.VISIBLE);
           HashMap<String, Object> values = new HashMap<>();
           values.put("nama",etNama.getText().toString());
           values.put("email",etEmail.getText().toString());
           values.put("nik",etNik.getText().toString());
           values.put("gender",gender);
           values.put("status",status);
           values.put("alamat",alamat);
           values.put("wa",wa);
           values.put("fb",fb);
           values.put("ig",ig);
           values.put("twt",twiter);
           values.put("imgUri", profileImageUrl);
           DatabaseReference masyarakattt = FirebaseDatabase.getInstance().getReference("user");
           DatabaseReference childMasyarakat = masyarakattt.child(mAuth.getCurrentUser().getUid());
           childMasyarakat.updateChildren(values).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                    progressBar.setVisibility(View.GONE);
               }
           });
        }
    }

    private void showImageChooser() {
        android.content.Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(android.content.Intent.createChooser(intent, "Pilih foto profile"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfileImage = data.getData();
            try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                    imageView.setImageBitmap(bitmap);
                    uploadImageToFirebaseStorage();
                } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("PhotoProfile/" +System.currentTimeMillis()+ ".jpg");
        if (uriProfileImage != null){
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    profileImageUrl = task.getResult().toString();
                                    Toast.makeText(EditProfile.this, "upload sukses" + profileImageUrl, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfile.this, "gagal upload", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
