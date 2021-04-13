package com.molor.adminmolor.adapter;

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
import com.molor.adminmolor.MainActivity;
import com.molor.adminmolor.R;
import com.molor.adminmolor.model.Requests;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Requests> moviesList;
    private Activity mActivity;

    public RecyclerViewAdapter(List<Requests> moviesList, Activity mActivity) {
        this.moviesList = moviesList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Requests movie = moviesList.get(position);

        holder.txt_judul.setText(movie.getJudul());
        holder.txt_laporan.setText(movie.getLaporan());
        Glide.with(mActivity).load(movie.getImageLapor()).into(holder.img_laporan);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getDetail = new Intent(mActivity, MainActivity.class);

                getDetail.putExtra("id", movie.getKey());
                getDetail.putExtra("subject", movie.getSubject());
                getDetail.putExtra("judul", movie.getJudul());
                getDetail.putExtra("laporan", movie.getLaporan());
                getDetail.putExtra("img", movie.getImageLapor());

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
        public TextView txt_judul, txt_laporan;
        public ImageView img_laporan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.lnrlyt);
            txt_judul = itemView.findViewById(R.id.txt_judul);
            txt_laporan = itemView.findViewById(R.id.txt_laporan);
            img_laporan = itemView.findViewById(R.id.img_lapor);
        }
    }
}
