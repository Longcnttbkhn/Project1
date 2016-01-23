package com.example.hoang.project1.util;

import android.content.ClipData;
import android.util.Log;
import android.widget.Toast;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.ItemPhong;
import com.example.hoang.project1.model.PhongDangKy;
import com.example.hoang.project1.model.PhongDeXuat;
import com.example.hoang.project1.model.PhongQuanLy;
import com.example.hoang.project1.model.PhongTimKiem;
import com.example.hoang.project1.model.TaiKhoan;
import com.example.hoang.project1.model.TaiKhoanChiTiet;

import java.net.URLEncoder;

/**
 * Created by hoang on 10/31/2015.
 */
public class Variable {
    /* Link XML */

    public static final String SERVICE = "http://192.168.173.1/php/ch01/project/project1/";

    public static final int ICON_PHONG_DANGKY = R.drawable.icon_dangky;
    public static final int ICON_PHONG_DEXUAT = R.drawable.icon_dexuat;
    public static final String CHAR_SET = "UTF-8";

    public static final String KEY_TAIKHOAN = "TAI_KHOAN";
    public static final String KEY_ITEMPHONG = "ITEM_PHONG";
    public static final String KEY_LINK = "KEY_LINK";


    public static String kiemTraEmail(String email) {
        String convertEmail = null;
        try {
            convertEmail = URLEncoder.encode(email, CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "kiemTraEmail.php?email=" + convertEmail);
        return stringBuilder.toString();
    }

    public static final String taoTaiKhoan(TaiKhoan taiKhoan) {
        String convertEmail = null;
        String convertTenHienThi = null;
        try {
            convertEmail = URLEncoder.encode(taiKhoan.getEmail(), CHAR_SET);
            convertTenHienThi = URLEncoder.encode(taiKhoan.getTenHienThi(), CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "taoTaiKhoan.php?email=" + convertEmail + "&tenHienThi=" + convertTenHienThi + "&matKhau=" + taiKhoan.getMatKhau());
        return stringBuilder.toString();
    }

    public static final String login(String email, String matKhau) {
        String convertEmail = null;
        try {
            convertEmail = URLEncoder.encode(email, CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "dangNhap.php?email=" + convertEmail + "&matKhau=" + matKhau);
        return stringBuilder.toString();
    }

    public static final String taoPhong(PhongDangKy phongDangKy) {
        String convertDiaChi = null,
                convertThongTin = null,
                convertTraLoi = null;
        try {
            convertDiaChi = URLEncoder.encode(phongDangKy.getDiaChiCuThe(), CHAR_SET);
            convertThongTin = URLEncoder.encode(phongDangKy.getThongTinBoSung(), CHAR_SET);
            convertTraLoi = URLEncoder.encode(phongDangKy.getTraLoiCauHoiBaoMat(), CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "taoPhong.php?idN=" + phongDangKy.getIdN()
                + "&dienTich=" + phongDangKy.getDienTich()
                + "&giaCa=" + phongDangKy.getGiaCa()
                + "&khuVuc=" + phongDangKy.getKhuVuc()
                + "&diaChi=" + convertDiaChi
                + "&boSung=" + convertThongTin
                + "&cauHoiBaoMat=" + phongDangKy.getCauHoiBaoMat()
                + "&traLoiCauHoiBaoMat=" + convertTraLoi
                + "&danhGia=" + phongDangKy.getDanhGia());
        return stringBuilder.toString();
    }
    public static final String deXuatPhong(PhongDeXuat phongDeXuat) {
        String convertDiaChi = null,
                convertThongTin = null;
        try {
            convertDiaChi = URLEncoder.encode(phongDeXuat.getDiaChiCuThe(), CHAR_SET);
            convertThongTin = URLEncoder.encode(phongDeXuat.getThongTinBoSung(), CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "deXuatPhong.php?idN=" + phongDeXuat.getIdN()
                + "&dienTich=" + phongDeXuat.getDienTich()
                + "&giaCa=" + phongDeXuat.getGiaCa()
                + "&khuVuc=" + phongDeXuat.getKhuVuc()
                + "&diaChi=" + convertDiaChi
                + "&boSung=" + convertThongTin);
        return stringBuilder.toString();
    }
    public static final String timMatKhau(String email){
        String convertEmail = null;
        try {
            convertEmail = URLEncoder.encode(email, CHAR_SET);
        } catch (Exception e){
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "timMatKhau.php?email=" + convertEmail);
        return stringBuilder.toString();
    }

    public static final String chiTietPhongDangO(String idN){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "chiTietPhongDangO.php?idN=" + idN);
        return stringBuilder.toString();
    }
    public static final String chiTietNguoiDung(String idN){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "chiTietNguoiDung.php?idN=" + idN);
        return stringBuilder.toString();
    }
    public static final String updatePhongDangO(PhongQuanLy phongQuanLy) {
        String
                convertThongTin = null,
                convertTraLoi = null;
        try {
            convertThongTin = URLEncoder.encode(phongQuanLy.getThongTinBoSung(), CHAR_SET);
            convertTraLoi = URLEncoder.encode(phongQuanLy.getTraLoiCauHoiBaoMat(), CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "capNhatPhongDangO.php?idN=" + phongQuanLy.getIdN()
                + "&idP=" + phongQuanLy.getIdP()
                + "&giaCa=" + phongQuanLy.getGiaCa()
                + "&boSung=" + convertThongTin
                + "&tinhTrang=" + phongQuanLy.getTinhTrang()
                + "&cauHoiBaoMat=" + phongQuanLy.getCauHoiBaoMat()
                + "&traLoiCauHoiBaoMat=" + convertTraLoi
                + "&danhGia=" + phongQuanLy.getDanhGia());
        return stringBuilder.toString();
    }
    public static final String roiPhong(String idN, String idP){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "roiPhong.php?idN=" + idN
                + "&idP=" + idP);

        return stringBuilder.toString();
    }
    public static final String xoaPhong(String idN, String idP){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "xoaPhong.php?idN=" + idN
                + "&idP=" + idP);
        return stringBuilder.toString();
    }
    public static final String updateThongTinCaNhan(TaiKhoanChiTiet taiKhoanChiTiet) {
        String convertTenHienThi = null;

        try {
            convertTenHienThi = URLEncoder.encode(taiKhoanChiTiet.getTenHienThi(), CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "capNhatThongTinCaNhan.php?idN=" + taiKhoanChiTiet.getIdN()
                + "&tenHienThi=" + convertTenHienThi
                + "&tinhTrang=" + taiKhoanChiTiet.getTinhTrang());
        return stringBuilder.toString();
    }

    public static final String doiMatKhau(String idN, String matKhauCu, String matKhauMoi){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "doiMatKhau.php?idN=" + idN
                + "&matKhauCu=" + matKhauCu
                + "&matKhauMoi=" + matKhauMoi);
        return stringBuilder.toString();
    }

    public static final String layPhong(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "layPhong.php");
        return stringBuilder.toString();
    }

    public static final String chiTietPhong(ItemPhong itemPhong){
        StringBuilder stringBuilder = new StringBuilder();
        if (itemPhong.getKieuPhong().equals("dangKy"))
            stringBuilder.append(SERVICE+ "chiTietPhongDangKy.php?idP=" + itemPhong.getIdP());
        else
            stringBuilder.append(SERVICE+ "chiTietPhongDeXuat.php?idP=" + itemPhong.getIdP());
        Log.d("Long", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static final String layCauHoiBaoMat(String idP){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "layCauHoiBaoMat.php?idP=" + idP);
        return stringBuilder.toString();
    }

    public static final String denOPhong(String idP, String idN, String traLoiCauHoiBaoMat){
        String convertTraLoi = null;
        try {
            convertTraLoi = URLEncoder.encode(traLoiCauHoiBaoMat, CHAR_SET);
        } catch (Exception e) {

        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "denOPhong.php?idP=" + idP
                + "&idN=" + idN
                + "&traLoiCauHoiBaoMat=" + convertTraLoi);
        return stringBuilder.toString();
    }
    public static final String timPhong(PhongTimKiem phongTimKiem){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVICE + "timPhong.php?idP=" + phongTimKiem.getIdP()
                + "&khuVuc=" + phongTimKiem.getKhuVuc()
                + "&dienTichMin=" + phongTimKiem.getDientTichMin()
                + "&dienTichMax=" + phongTimKiem.getDienTichMax()
                + "&giaMin=" + phongTimKiem.getGiaMin()
                + "&giaMax=" + phongTimKiem.getGiaMax()
                + "&kieuPhong=" + phongTimKiem.getKieuPhong()
                + "&tinhTrangPhong=" + phongTimKiem.getTinhTrangPhong());
        Log.d("Long", stringBuilder.toString());
        return stringBuilder.toString();
    }
}
