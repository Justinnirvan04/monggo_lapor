package com.molor.monggolapor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class splashscreen extends AppCompatActivity {
    private int waktu_loading=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new
                        Intent(splashscreen.this,MainActivity.class);
                startActivity(home);
                finish();
            }
        },waktu_loading);
    }
}