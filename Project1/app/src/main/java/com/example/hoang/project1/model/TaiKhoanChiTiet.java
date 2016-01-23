package com.example.hoang.project1.model;

/**
 * Created by hoang on 11/13/2015.
 */
public class TaiKhoanChiTiet extends TaiKhoan {
    public String[] TINH_TRANG;
    public String[] TINH_TRANG2;
    private String idN;
    private String idP;
    private int tinhTrang;

    public TaiKhoanChiTiet(){
        TINH_TRANG = new String[] {"Muốn chuyển", "Đã có chỗ ở"};
        TINH_TRANG2 = new String[] {"Muốn chuyển", "Đã có chỗ ở", "Chưa có chỗ ở"};
    }
    public int getTinhTrang() {
        return tinhTrang;
    }

    public String[] getTINH_TRANG(){
        if (tinhTrang != 2)
            return TINH_TRANG;
        else return TINH_TRANG2;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setTinhTrang(String tinh_trang){
        this.tinhTrang = Integer.valueOf(tinh_trang);
    }
    public String getIdP() {

        return idP;
    }

    public void setIdP(String idP) {
        this.idP = idP;
    }

    public String getIdN() {

        return idN;
    }

    public void setIdN(String idN) {
        this.idN = idN;
    }
}
