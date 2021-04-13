package com.molor.adminmolor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.molor.adminmolor.adapter.RequestPengumumanAdapter;
import com.molor.adminmolor.model.PengumumanRequests;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_Pengumuman extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private ArrayList<PengumumanRequests> daftarReq;
    private RequestPengumumanAdapter recyclerViewAdapter;
    private RecyclerView rc_list_request;
    private ProgressDialog loading;
    private FloatingActionButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__pengumuman);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_add = findViewById(R.id.fab_add);
        rc_list_request = findViewById(R.id.rcList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request.setLayoutManager(layoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(List_Pengumuman.this, null, "Please Waiting", true, false);

        databaseReference.child("Pengumuman_Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarReq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    PengumumanRequests pengumumanRequests = noteDataSnapshot.getValue(PengumumanRequests.class);
                    pengumumanRequests.setKey(noteDataSnapshot.getKey());

                    daftarReq.add(pengumumanRequests);
                }

                recyclerViewAdapter = new RequestPengumumanAdapter(daftarReq, List_Pengumuman.this);
                rc_list_request.setAdapter(recyclerViewAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+""+error.getMessage());
                loading.dismiss();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List_Pengumuman.this, Pengumuman.class)
                        .putExtra("id", "")
                        .putExtra("pengirim", "")
                        .putExtra("topik", "")
                        .putExtra("informasi", "")
                        .putExtra("ditujukan", "")
                        .putExtra("image", ""));
            }
        });
    }
}