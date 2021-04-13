package com.molor.monggolapor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class feeds extends AppCompatActivity {
ImageView mgnti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        mgnti=(ImageView) findViewById(R.id.devv);
        mgnti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new
                        Intent(getApplication(),aboutt.class);
                startActivity(intent);
            }

        });
    }
}