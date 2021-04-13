package com.molor.monggolapor;

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
import com.molor.monggolapor.model.Requests;
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

import org.json.JSONObject;

import java.util.UUID;

public class lapor extends AppCompatActivity {
    private DatabaseReference database;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    private EditText etsubject, etjudul, etlaporan;
    private ImageView ImageLapor;
    private ProgressDialog loading;
    private Button btn_save, btn_cancel, btn_choosen;

    private Uri imageUri;

    private String sPid, sPsubject, sPjudul, sPlaporan, sPimageLapor, SImageLpr;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "Your Firebase server key";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION_TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor);

        database = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        sPid = getIntent().getStringExtra("id");
        sPsubject = getIntent().getStringExtra("subject");
        sPjudul = getIntent().getStringExtra("judul");
        sPlaporan = getIntent().getStringExtra("laporan");
        sPimageLapor = getIntent().getStringExtra("img");

        etsubject = findViewById(R.id.subject);
        etjudul = findViewById(R.id.judul);
        etlaporan = findViewById(R.id.isiLaporan);
        ImageLapor = findViewById(R.id.imageLapor);

        btn_save = findViewById(R.id.btnSave);
        btn_cancel = findViewById(R.id.btnCancel);
        btn_choosen = findViewById(R.id.Choose);

        etsubject.setText(sPsubject);
        etjudul.setText(sPjudul);
        etlaporan.setText(sPlaporan);
        Glide.with(this).load(sPimageLapor).into(ImageLapor);

        btn_choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        if (sPid.equals("")) {
            btn_save.setText("Submit");
            btn_cancel.setText("Cancel");
        } else {
            btn_save.setText("Edit");
            btn_cancel.setText("Delete");
        }

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Ssubject = etsubject.getText().toString();
                String Sjudul = etjudul.getText().toString();
                String Slaporan = etlaporan.getText().toString();


                if (btn_save.getText().equals("Submit")) {
                    if (Ssubject.equals("")) {
                        etsubject.setError("Silahkan Masukkan Nama Anda!");
                        etsubject.requestFocus();
                    } else if (Sjudul.equals("")) {
                        etjudul.setError("Silahkan Masukkan Judul!");
                        etjudul.requestFocus();
                    } else if (Slaporan.equals("")) {
                        etlaporan.setError("Lapoarkan Masalah Anda!");
                        etlaporan.requestFocus();
                    }
                    else {
                        loading = ProgressDialog.show(lapor.this, null, "Please wait", true, false);

                        submitUser(new Requests(Ssubject.toLowerCase(), Sjudul.toLowerCase(), Slaporan.toLowerCase(), SImageLpr));
                    }
                } else {
                    // perintah edit
                    if (Ssubject.equals("")) {
                        etsubject.setError("Silahkan Masukkan Nama Anda!");
                        etsubject.requestFocus();
                    } else if (Sjudul.equals("")) {
                        etjudul.setError("Silahkan Masukkan Judul!");
                        etjudul.requestFocus();
                    } else if (Slaporan.equals("")) {
                        etlaporan.setError("Lapoarkan Masalah Anda!");
                        etlaporan.requestFocus();
                    } else {
                        loading = ProgressDialog.show(lapor.this, null, "Please wait", true, false);

                        editUser(new Requests(Ssubject.toLowerCase(), Sjudul.toLowerCase(), Slaporan.toLowerCase(), SImageLpr), sPid);
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
                    database.child("Requests").child(sPid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(lapor.this, "Data Berhasil dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

        private void editUser (Requests requests, String id){
            database.child("Request").child(id).setValue(requests).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    loading.dismiss();

                    etsubject.setText("");
                    etjudul.setText("");
                    etlaporan.setText("");

                    Toast.makeText(lapor.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(lapor.this, isi.class);
                    startActivity(intent);
                }
            });
        }

        private void submitUser (Requests requests){
            NOTIFICATION_TITLE = etsubject.getText().toString();
            NOTIFICATION_MESSAGE = etlaporan.getText().toString();
            TOPIC = "/topics/userABC"; //topic must match with what the receiver subcribed

            JSONObject notification = new JSONObject();
            JSONObject notificationBody = new JSONObject();
            try {
                notificationBody.put("name", NOTIFICATION_TITLE);
                notificationBody.put("message", NOTIFICATION_MESSAGE);

                notification.put("to", TOPIC);
                notification
            }

            database.child("Request")
                    .push()
                    .setValue(requests)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    loading.dismiss();

                    etsubject.setText("");
                    etjudul.setText("");
                    etlaporan.setText("");

                    Toast.makeText(lapor.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(lapor.this, isi.class);
                    startActivity(intent);
                }
            });
        }

        private void showImageChooser(){
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
                    ImageLapor.setImageURI(imageUri);
                    uploadImagetoFirebaseStrorage();
            }
        }

        private void uploadImagetoFirebaseStrorage() {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Upload Image");
            progressDialog.show();

            final String randomKey = UUID.randomUUID().toString();
            StorageReference riversRef = storageReference.child("image/*" + randomKey);

            riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            SImageLpr = task.getResult().toString();
                            Toast.makeText(lapor.this, "upload image" + SImageLpr, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(lapor.this, "upload image" + e, Toast.LENGTH_SHORT).show();
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