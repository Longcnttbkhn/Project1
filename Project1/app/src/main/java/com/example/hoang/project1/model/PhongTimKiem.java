package com.example.hoang.project1.model;

import android.content.Context;

import com.example.hoang.project1.R;

/**
 * Created by hoang on 12/7/2015.
 */
public class PhongTimKiem {
    private String idP;
    private float dienTichMax;
    private float dientTichMin;
    private int khuVuc;
    public String[] listKhuVuc;
    private int giaMax;
    private int giaMin;

    private String kieuPhong;
    private String tinhTrangPhong;

    public PhongTimKiem(Context context){
        listKhuVuc = context.getResources().getStringArray(R.array.khuVuc);
    }

    public String getIdP() {
        return idP;
    }

    public void setIdP(String idP) {
        if (idP.equals("")) this.idP = "null";
        else
            this.idP = idP;
    }

    public float getDienTichMax() {
        return dienTichMax;
    }

    public void setDienTichMax(String dienTichMax) {
        if (dienTichMax.equals(""))
            this.dienTichMax = 100;
        else
            this.dienTichMax = Float.valueOf(dienTichMax);
    }

    public float getDientTichMin() {
        return dientTichMin;
    }

    public void setDientTichMin(String dientTichMin) {
        if (dientTichMin.equals(""))
            this.dientTichMin = 0;
        else
            this.dientTichMin = Float.valueOf(dientTichMin);
    }

    public int getKhuVuc() {
        return khuVuc;
    }


    public void setKhuVuc(int khuVuc){
        this.khuVuc = khuVuc;
    }

    public int getGiaMax() {
        return giaMax;
    }

    public void setGiaMax(String giaMax) {
        if (giaMax.equals(""))
            this.giaMax = 10000;
        else
            this.giaMax = Integer.valueOf(giaMax);
    }

    public int getGiaMin() {
        return giaMin;
    }

    public void setGiaMin(String giaMin) {
        if (giaMin.equals(""))
            this.giaMin = 0;
        else
            this.giaMin = Integer.valueOf(giaMin);
    }

    public String getKieuPhong() {
        return kieuPhong;
    }

    public void setKieuPhong(String kieuPhong) {
        this.kieuPhong = kieuPhong;
    }

    public String getTinhTrangPhong() {
        return tinhTrangPhong;
    }

    public void setTinhTrangPhong(String tinhTrangPhong) {
        this.tinhTrangPhong = tinhTrangPhong;
    }

    public boolean kiemTraDienTichPhong(){
        boolean kiemtra = true;
        if ((dienTichMax < 0) || (dientTichMin < 0)){
            kiemtra = false;
        } else
            if (dienTichMax < dientTichMin)
                kiemtra = false;
        return kiemtra;
    }

    public boolean kiemTraGiaPhong() {
        boolean kiemtra = true;
        if ((giaMax < 0) || (giaMin < 0)){
            kiemtra = false;
        } else
            if (giaMax < giaMin)
                kiemtra = false;
        return kiemtra;
    }
}
