package com.molor.monggolapor;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    Button btnLogin, btnDaftar;
    ProgressBar progressBar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.email);
        edtPass = findViewById(R.id.password);
        btnDaftar = findViewById(R.id.btRegister);
        btnLogin = findViewById(R.id.masuk);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        final String Email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        if (Email.isEmpty()){
            edtEmail.setError("Email perlu diisi!");
            edtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            edtEmail.setError("Format email salah!");
            edtEmail.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            edtPass.setError("Password perlu diisi!");
            edtPass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                FirebaseUser User = mAuth.getCurrentUser();

                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), isi.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Akun Belum terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, isi.class);
            Toast.makeText(getApplicationContext(), "Anda sudah login", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}