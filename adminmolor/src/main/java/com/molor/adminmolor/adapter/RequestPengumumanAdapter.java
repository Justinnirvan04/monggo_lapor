package com.molor.adminmolor.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molor.adminmolor.Pengumuman;
import com.molor.adminmolor.R;
import com.molor.adminmolor.model.PengumumanRequests;


import java.util.List;

public class RequestPengumumanAdapter extends RecyclerView.Adapter<RequestPengumumanAdapter.MyViewHolder> {
    private List<PengumumanRequests> moviesList;
    private Activity mActivity;

    public RequestPengumumanAdapter(List<PengumumanRequests> moviesList, Activity mActivity) {
        this.moviesList = moviesList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public RequestPengumumanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengumuman, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestPengumumanAdapter.MyViewHolder holder, int position) {
        final PengumumanRequests movie = moviesList.get(position);

        holder.nama.setText(movie.getPengirim());
        holder.informasi.setText(movie.getInformasi());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getDetail = new Intent(mActivity, Pengumuman.class);

                getDetail.putExtra("id", movie.getKey());
                getDetail.putExtra("pengirim", movie.getPengirim());
                getDetail.putExtra("topik", movie.getTopik());
                getDetail.putExtra("informasi", movie.getInformasi());
                getDetail.putExtra("ditujukan", movie.getInformasi());
                getDetail.putExtra("image", movie.getImage());

                mActivity.startActivity(getDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView nama, informasi;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            nama = itemView.findViewById(R.id.tv_upload);
            informasi = itemView.findViewById(R.id.tv_berita);
        }
    }
}
