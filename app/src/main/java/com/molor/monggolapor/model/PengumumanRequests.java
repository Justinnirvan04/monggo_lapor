package com.molor.monggolapor.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class PengumumanRequests implements Serializable {

    private String pengirim;
    private String topik;
    private String informasi;
    private String ditujukan;
    private String image;

    private String Key;

    public PengumumanRequests(){

    }
    public PengumumanRequests(String pengirim, String topik, String informasi,String ditujukan, String image){
        this.pengirim = pengirim;
        this.topik = topik;
        this.informasi = informasi;
        this.ditujukan = ditujukan;
        this.image = image;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getTopik() {
        return topik;
    }

    public void setTopik(String topik) {
        this.topik = topik;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public String getDitujukan() {
        return ditujukan;
    }

    public void setDitujukan(String ditujukan) {
        this.ditujukan = ditujukan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    @Override
    public String toString() {
        return "PengumumanRequests{" +
                "pengirim='" + pengirim + '\'' +
                ", topik='" + topik + '\'' +
                ", informasi='" + informasi + '\'' +
                ", image='" + image + '\'' +
                ", Key='" + Key + '\'' +
                '}';
    }
}
