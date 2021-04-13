package com.molor.adminmolor.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Requests implements Serializable {

    private String subject;
    private String laporan;
    private String judul;
    private String ImageLapor;

    private String key;

    public Requests(){

    }

    public Requests(String subject, String judul, String laporan, String ImageLapor){

        this.subject = subject;
        this.judul = judul;
        this.laporan = laporan;
        this.ImageLapor = ImageLapor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLaporan() {
        return laporan;
    }

    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }

    public String getImageLapor() {
        return ImageLapor;
    }

    public void setImageLapor(String imageUrl) {
        this.ImageLapor = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString(){
        return ""+subject+"\n"+""+judul+"\n"+""+laporan+"\n"+""+ImageLapor;
    }
}

