package com.molor.monggolapor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.molor.monggolapor.R;
import com.molor.monggolapor.aboutt;

public class AboutFragment extends Fragment {

    ViewFlipper v_flipper;
    ImageView devv;

    LinearLayout ll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        devv = view.findViewById(R.id.devv);

        devv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), aboutt.class);
                startActivity(intent);
            }
        });

        return view;
    }
}