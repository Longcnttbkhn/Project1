package com.example.hoang.project1.model;

import com.example.hoang.project1.R;
import com.example.hoang.project1.util.Variable;

import java.io.Serializable;

/**
 * Created by hoang on 12/2/2015.
 */
public class ItemPhong implements Serializable{
    private String kieuPhong;
    private String idP;
    private String khuVuc;
    private String dienTich;
    private String giaCa;
    private int icon;

    public int getIcon() {
        return icon;
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

    public String getKhuVuc() {

        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }

    public String getIdP() {

        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public void setKieuPhong(String kieuPhong) {
        this.kieuPhong = kieuPhong;
        if (kieuPhong.equals("dangKy"))
            icon = Variable.ICON_PHONG_DANGKY;
        else icon = Variable.ICON_PHONG_DEXUAT;
    }

    public String getKieuPhong(){
        return kieuPhong;
    }
}
