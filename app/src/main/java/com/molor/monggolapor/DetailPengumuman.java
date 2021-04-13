package com.molor.monggolapor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.molor.monggolapor.adapter.RequestPengumumanAdapter;
import com.molor.monggolapor.model.PengumumanRequests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailPengumuman extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<PengumumanRequests> daftarReq;
    private RequestPengumumanAdapter recyclerViewAdapter;
    private RecyclerView rc_list_request;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengumuman);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        rc_list_request = findViewById(R.id.rcList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request.setLayoutManager(layoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(DetailPengumuman.this, null, "Please Waiting", true, false);

        databaseReference.child("Pengumuman_Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarReq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    PengumumanRequests pengumumanRequests = noteDataSnapshot.getValue(PengumumanRequests.class);
                    pengumumanRequests.setKey(noteDataSnapshot.getKey());

                    daftarReq.add(pengumumanRequests);
                }

                recyclerViewAdapter = new RequestPengumumanAdapter(daftarReq, DetailPengumuman.this);
                rc_list_request.setAdapter(recyclerViewAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+""+error.getMessage());
                loading.dismiss();
            }
        });
    }
}