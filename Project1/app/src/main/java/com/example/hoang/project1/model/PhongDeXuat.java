package com.example.hoang.project1.model;

import android.content.Context;

/**
 * Created by hoang on 11/12/2015.
 */
public class PhongDeXuat extends Phong{
    private String idN;
    private String diaChiCuThe;
    private String thongTinBoSung;

    public PhongDeXuat(Context context){
        super(context);
    }

    public String getIdN() {
        return idN;
    }

    public void setIdN(String idN) {
        this.idN = idN;
    }

    public String getThongTinBoSung() {
        return thongTinBoSung;
    }

    public void setThongTinBoSung(String thongTinBoSung) {
        this.thongTinBoSung = thongTinBoSung;
    }

    public String getDiaChiCuThe() {

        return diaChiCuThe;
    }

    public void setDiaChiCuThe(String diaChiCuThe) {
        this.diaChiCuThe = diaChiCuThe;
    }

}
