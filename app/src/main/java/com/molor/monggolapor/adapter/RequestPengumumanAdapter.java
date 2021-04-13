package com.molor.monggolapor.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.molor.monggolapor.Pengumuman;
import com.molor.monggolapor.R;
import com.molor.monggolapor.model.PengumumanRequests;

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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengumuman, parent, false);

        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView nama, informasi;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.lnrlyt);
            nama = itemView.findViewById(R.id.txt_kepada);
            informasi = itemView.findViewById(R.id.txt_informasi);
            imageView = itemView.findViewById(R.id.img_announce);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PengumumanRequests movie = moviesList.get(position);

        holder.nama.setText(movie.getPengirim());
        holder.informasi.setText(movie.getInformasi());
        Glide.with(mActivity).load(movie.getImage()).into(holder.imageView);

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
}
