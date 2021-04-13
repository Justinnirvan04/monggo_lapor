package com.molor.monggolapor.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class AspirasiRequests implements Serializable {

    private String Dari;
    private String Kepada;
    private String Aspirasi;

    private String key;

    public AspirasiRequests(){

    }
    public AspirasiRequests(String Dari, String Kepada, String Aspirasi){
        this.Dari = Dari;
        this.Kepada = Kepada;
        this.Aspirasi = Aspirasi;
    }

    public String getDari() {
        return Dari;
    }

    public void setDari(String dari) {
        Dari = dari;
    }

    public String getKepada() {
        return Kepada;
    }

    public void setKepada(String kepada) {
        Kepada = kepada;
    }

    public String getAspirasi() {
        return Aspirasi;
    }

    public void setAspirasi(String aspirasi) {
        Aspirasi = aspirasi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "AspirasiRequests{" +
                "Dari='" + Dari + '\'' +
                ", Kepada='" + Kepada + '\'' +
                ", Aspirasi='" + Aspirasi + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
