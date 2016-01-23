package com.example.hoang.project1.model;

import android.content.Context;

import com.example.hoang.project1.R;

/**
 * Created by hoang on 11/13/2015.
 */
public class Phong {
    public String[] listKhuVuc;
    private String dienTich;
    private String giaCa;
    private int khuVuc;

    public Phong(Context context) {
        listKhuVuc = context.getResources().getStringArray(R.array.khuVuc);
    }

    public int getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = Integer.valueOf(khuVuc);
    }

    public void setKhuVuc(int khuVuc){
        this.khuVuc = khuVuc;
    }
    public String getGiaCa() {

        return giaCa;
    }

    public void setGiaCa(String giaCa) {
        this.giaCa = giaCa;
    }

    public String getDienTich() {

        return dienTich;
    }

    public void setDienTich(String dienTich) {
        this.dienTich = dienTich;
    }
}
