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

import com.molor.adminmolor.FormAspirasi;
import com.molor.adminmolor.R;
import com.molor.adminmolor.model.AspirasiRequests;


import java.util.List;

public class RequestRecyclerViewAdapterAspirasi extends RecyclerView.Adapter<RequestRecyclerViewAdapterAspirasi.MyViewHolder> {

    private List<AspirasiRequests> moviesList;
    private Activity mActivity;

    public RequestRecyclerViewAdapterAspirasi(List<AspirasiRequests> moviesList, Activity mActivity) {
        this.moviesList = moviesList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aspirasi, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AspirasiRequests movie = moviesList.get(position);

        holder.txt_kepada.setText(movie.getKepada());
        holder.txt_subject.setText(movie.getAspirasi());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getDetail = new Intent(mActivity, FormAspirasi.class);

                getDetail.putExtra("id", movie.getKey());
                getDetail.putExtra("dari", movie.getDari());
                getDetail.putExtra("kepada", movie.getKepada());
                getDetail.putExtra("aspirasi", movie.getAspirasi());

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
        public TextView txt_kepada, txt_subject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.lnrlyt);
            txt_kepada = itemView.findViewById(R.id.txt_kepada);
            txt_subject = itemView.findViewById(R.id.txt_subject);
        }
    }
}
