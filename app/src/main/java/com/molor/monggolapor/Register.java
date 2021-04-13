package com.molor.monggolapor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.molor.monggolapor.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtEmail, edtNIK;
    Button btnDaftar;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference Masyarakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.username);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        edtNIK = findViewById(R.id.nik);
        btnDaftar = findViewById(R.id.btn_regist);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String username = edtUsername.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String pass = edtPassword.getText().toString().trim();
        final String nik = edtNIK.getText().toString().trim();

        if (username.isEmpty()){
            edtUsername.setError("Isikan nama lengkap anda!");
            edtUsername.requestFocus();
            return;
        }
        if (email.isEmpty()){
            edtEmail.setError("Isikan email anda");
            edtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Isikan email yang valid");
            edtEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            edtPassword.setError("Isikan password!");
            edtPassword.requestFocus();
            return;
        }
        if (pass.length()<6){
            edtPassword.setError("Isikan password minimal 6 huruf");
            edtPassword.requestFocus();
            return;
        }
        if (nik.isEmpty()){
            edtNIK.setError("Isikan NIK anda!");
            edtNIK.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(username, email, pass, nik);

                    FirebaseDatabase.getInstance().getReference("user")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser User = mAuth.getCurrentUser();
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Intent intent = new Intent(Register.this, isi.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                User.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(), "Anda telah terdaftar", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag", task.getException().getMessage());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, isi.class);
            Toast.makeText(getApplicationContext(), "Anda telah login", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}
