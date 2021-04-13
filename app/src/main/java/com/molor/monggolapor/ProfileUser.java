package com.molor.monggolapor;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileUser extends AppCompatActivity {

    private TextView profileName, profileEmail, profileNIK, profileGender, profileStatus, profileNumber, profileAddress;
    private ImageView btnWa, btnFb, btnIg, btnTwt;
    private ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileUser.this, EditProfile.class));
            }
        });

        profileAddress = findViewById(R.id.profile_address);
        profileEmail = findViewById(R.id.profile_email);
        profileGender = findViewById(R.id.profile_gender);
        profileName = findViewById(R.id.profile_name);
        profileNIK = findViewById(R.id.profile_nik);
        profileNumber = findViewById(R.id.profile_number);
        profileStatus = findViewById(R.id.profile_status);
        imgUser = findViewById(R.id.profile_image);
        btnWa = findViewById(R.id.buttonwhatsapp);
        btnFb = findViewById(R.id.profile_fb);
        btnIg = findViewById(R.id.profile_ig);
        btnTwt = findViewById(R.id.profile_twitter);

        DatabaseReference masyarakat = FirebaseDatabase.getInstance().getReference("user");
        DatabaseReference childMasyarakat = masyarakat.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        childMasyarakat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    profileName.setText(snapshot.child("nama").getValue().toString());
                    profileEmail.setText(snapshot.child("email").getValue().toString());
                    profileNIK.setText(snapshot.child("nik").getValue().toString());
                    profileNumber.setText(snapshot.child("wa").getValue().toString());
                    profileAddress.setText(snapshot.child("alamat").getValue().toString());
                    profileGender.setText(snapshot.child("gender").getValue().toString());
                    profileStatus.setText(snapshot.child("status").getValue().toString());

                    final String waNumb = snapshot.child("wa").getValue().toString();
                    final String fbLink = snapshot.child("fb").getValue().toString();
                    final String IgLink = snapshot.child("ig").getValue().toString();
                    final String Twtlink = snapshot.child("twt").getValue().toString();

                    if (!waNumb.equals("")){
                        btnWa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startSupportChat(waNumb);
                            }
                        });
                    } else {
                        btnWa.setVisibility(View.GONE);
                    }
                    if (!fbLink.equals("")){
                        btnFb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gotoFb(fbLink);
                            }
                        });
                    } else {
                        btnFb.setVisibility(View.GONE);
                    }
                    if (!IgLink.equals("")){
                        btnIg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gotoIg(IgLink);
                            }
                        });
                    } else {
                        btnIg.setVisibility(View.GONE);
                    }
                    if (!Twtlink.equals("")){
                        btnTwt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gotoTwt(Twtlink);
                            }
                        });
                    } else {
                        btnTwt.setVisibility(View.GONE);
                    }
                    Glide.with(getApplicationContext())
                            .load(snapshot.child("imgUri").getValue().toString()).into(imgUser);
                    Toast.makeText(getApplicationContext(), snapshot.child("imgUri").getValue().toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void gotoFb(String fbLink) {
        startActivity(getOpenFacebookIntent(fbLink));
    }

    private Intent getOpenFacebookIntent(String fbLink) {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            getPackageManager().getPackageInfo("com.facebook.lite", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com/profile.php?id=100009488632531"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW, Uri.parse(fbLink));
        }
    }

    private void gotoIg(String igLink) {
        Uri uri = Uri.parse(igLink);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(igLink)));
        }
    }

    private void gotoTwt(String twtlink) {
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/GFerlindawati")));
        } catch (Exception e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twtlink)));
        }
    }

    private void startSupportChat(String number) {
        try {
            String trimToNumber = "+6282328884360";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://whatsapp.com/" + number + "/?text" + "Hello Seyeng..."));
            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}