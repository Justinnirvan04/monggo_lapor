package com.molor.adminmolor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.molor.adminmolor.adapter.RequestRecyclerViewAdapterAspirasi;
import com.molor.adminmolor.model.AspirasiRequests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailAspirasi extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private ArrayList<AspirasiRequests> daftarReq;
    private RequestRecyclerViewAdapterAspirasi recyclerViewAdapter;
    private RecyclerView rc_list_request;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aspirasi);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        rc_list_request = findViewById(R.id.rc_aspirasi_request);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request.setLayoutManager(layoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(DetailAspirasi.this, null, "Please Waiting", true, false);

        databaseReference.child("Aspirasi_Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarReq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    AspirasiRequests aspirasiRequest = noteDataSnapshot.getValue(AspirasiRequests.class);
                    aspirasiRequest.setKey(noteDataSnapshot.getKey());

                    daftarReq.add(aspirasiRequest);
                }

                recyclerViewAdapter = new RequestRecyclerViewAdapterAspirasi(daftarReq, DetailAspirasi.this);
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