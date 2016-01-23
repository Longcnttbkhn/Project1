package com.example.hoang.project1.model;

/**
 * Created by hoang on 12/3/2015.
 */
public class ItemPhongDangKy extends ItemPhongDeXuat {
    private float danhGia;
    private String tinhTrang;

    public float getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(String danhGia) {
        this.danhGia = Float.valueOf(danhGia);
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        int maTT = Integer.valueOf(tinhTrang);
        if (maTT == 2) this.tinhTrang = "Trống";
        else if (maTT == 0) this.tinhTrang = "Cần tìm thêm";
        else if (maTT == 1) this.tinhTrang = "Đầy";
    }
}
