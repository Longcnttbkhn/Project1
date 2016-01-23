package com.example.hoang.project1.model;

import java.io.Serializable;

/**
 * Created by hoang on 11/3/2015.
 */
public class TaiKhoan implements Serializable{
    private String email;
    private String tenHienThi;
    private String matKhau;

    public String getMatKhau() {

        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenHienThi() {

        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
