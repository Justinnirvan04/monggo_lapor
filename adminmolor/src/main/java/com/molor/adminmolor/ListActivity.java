package com.molor.adminmolor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.molor.adminmolor.adapter.RecyclerViewAdapter;
import com.molor.adminmolor.model.Requests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private ArrayList<Requests> daftarReq;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView rc_list_request;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        rc_list_request = findViewById(R.id.rc_list_request);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request.setLayoutManager(layoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(ListActivity.this, null, "Please Waiting", true, false);

        databaseReference.child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarReq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){
                    Requests requests = noteDataSnapshot.getValue(Requests.class);
                    requests.setKey(noteDataSnapshot.getKey());

                    daftarReq.add(requests);
                }

                recyclerViewAdapter = new RecyclerViewAdapter(daftarReq, ListActivity.this);
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