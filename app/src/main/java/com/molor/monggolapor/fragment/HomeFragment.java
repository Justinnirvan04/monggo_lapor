package com.molor.monggolapor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.molor.monggolapor.DetailPengumuman;
import com.molor.monggolapor.R;
import com.molor.monggolapor.aspirasi;
import com.molor.monggolapor.lapor;

public class HomeFragment extends Fragment {

    Animation leftAnim, building_animation, awan;
    ImageView cloud1, building, cloud2;
    private ImageView pndhlpr, pndhasprs, pndhevnt;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        leftAnim = AnimationUtils.loadAnimation(getContext(),R.anim.left_animat);
//        building_animation = AnimationUtils.loadAnimation(getContext(), R.anim.building_animation);
//        awan = AnimationUtils.loadAnimation(getContext(), R.anim.cloud_animation);

//        cloud1 = v.findViewById(R.id.imageView);
//        building = v.findViewById(R.id.build);
//        cloud2 = v.findViewById(R.id.cloud2);

//        cloud1.setAnimation(leftAnim);
//        building.setAnimation(building_animation);
//        cloud2.setAnimation(awan);

        pndhlpr = v.findViewById(R.id.jalan);
        pndhasprs = v.findViewById(R.id.aspirasii);
        pndhevnt = v.findViewById(R.id.feeds);

        pndhlpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), lapor.class)
                        .putExtra("id", "")
                        .putExtra("subject", "")
                        .putExtra("judul", "")
                        .putExtra("laporan", "")
                        .putExtra("img", ""));
            }

        });

        pndhasprs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), aspirasi.class)
                .putExtra("id", "")
                .putExtra("dari", "")
                .putExtra("kepada", "")
                .putExtra("aspirasi", "")
                .putExtra("img", ""));
            }
        });

        pndhevnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DetailPengumuman.class)
                .putExtra("id", "")
                .putExtra("pengirim", "")
                .putExtra("topik", "")
                .putExtra("informasi", "")
                .putExtra("ditujukan", "")
                .putExtra("image", ""));
            }
        });

        return v;
    }
}