package com.example.hoang.project1.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by hoang on 11/13/2015.
 */
public class PhongQuanLy extends PhongDangKy {
    public String[] listTinhTrang;
    private String idP;
    private int tinhTrang;
    private String soNguoi;
    private ArrayList<String> dsNguoiDangO;

    public PhongQuanLy(Context context){
        super(context);
        listTinhTrang = new String[] {"Cần tìm thêm", "Đầy"};
        dsNguoiDangO = new ArrayList<String>();
    }

    public void addNguoiDangO(String tenHienThi){
        dsNguoiDangO.add(tenHienThi);
    }

    public String getNguoiDangO(){
        StringBuilder stringBuilder = new StringBuilder();
        int size = 0;
        size = dsNguoiDangO.size();
        size--;
        for (int i = 0; i < size; i++) {
            stringBuilder.append(dsNguoiDangO.get(i) + ", ");
        }
        stringBuilder.append(dsNguoiDangO.get(size) + ".");
        return stringBuilder.toString();
    }
    public String getSoNguoi() {

        return soNguoi;
    }

    public void setSoNguoi(String soNguoi) {
        this.soNguoi = soNguoi;
    }

    public int getTinhTrang() {

        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = Integer.valueOf(tinhTrang);
    }

    public void setTinhTrang(int tinhTrang){
        this.tinhTrang = tinhTrang;
    }

    public String getIdP() {

        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }
}
