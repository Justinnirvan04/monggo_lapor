package com.molor.adminmolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private ImageView gntlprn, gntasprs, gntpngmmn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gntlprn = findViewById(R.id.gmb_lpor);
        gntasprs = findViewById(R.id.aspirasi_gmb);
        gntpngmmn = findViewById(R.id.pengumuman);

        gntlprn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, ListActivity.class)
                        .putExtra("id", "")
                        .putExtra("subject", "")
                        .putExtra("judul", "")
                        .putExtra("laporan", "")
                        .putExtra("img", ""));
            }
        });

        gntasprs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, DetailAspirasi.class)
                        .putExtra("id", "")
                        .putExtra("dari", "")
                        .putExtra("kepada", "")
                        .putExtra("aspirasi", ""));
            }
        });

        gntpngmmn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, List_Pengumuman.class));
            }
        });
    }
}