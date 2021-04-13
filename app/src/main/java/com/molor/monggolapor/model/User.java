package com.molor.monggolapor.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private String nama;
    private String nik;


    public User(String nama, String email, String pass, String nik) {
        this.nama = nama;
        this.email = email;
        this.password = pass;
        this.nik = nik;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String pass) {
        this.password = pass;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }
//    @Override
//    public String toString() {
//        return " " + email + "\n" + " " + nama + "\n" + " " + nis + "\n";
//    }
}

