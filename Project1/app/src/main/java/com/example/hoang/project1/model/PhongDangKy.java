package com.example.hoang.project1.model;

import android.content.Context;

import com.example.hoang.project1.R;

/**
 * Created by hoang on 11/9/2015.
 */
public class PhongDangKy extends PhongDeXuat{
    public String[] listCauHoi;
    private int cauHoiBaoMat;
    private String traLoiCauHoiBaoMat;
    private float danhGia;

    public PhongDangKy(Context context) {
        super(context);
        listCauHoi = context.getResources().getStringArray(R.array.cauHoiBaoMat);
    }
    public String getTraLoiCauHoiBaoMat() {
        return traLoiCauHoiBaoMat;
    }

    public void setTraLoiCauHoiBaoMat(String traLoiCauHoiBaoMat) {
        this.traLoiCauHoiBaoMat = traLoiCauHoiBaoMat;
    }

    public float getDanhGia() {

        return danhGia;
    }

    public void setDanhGia(float danhGia) {
        this.danhGia = danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = Float.valueOf(danhGia);
    }
    public int getCauHoiBaoMat() {
        return cauHoiBaoMat;
    }

    public void setCauHoiBaoMat(String cauHoiBaoMat) {
        this.cauHoiBaoMat = Integer.valueOf(cauHoiBaoMat);
    }
    public void setCauHoiBaoMat(int cauHoiBaoMat){
        this.cauHoiBaoMat = cauHoiBaoMat;
    }
}
