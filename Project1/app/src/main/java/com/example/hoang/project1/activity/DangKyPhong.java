package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.PhongDangKy;
import com.example.hoang.project1.model.TaiKhoan;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 11/9/2015.
 */
public class DangKyPhong extends Activity {
    TaiKhoanChiTiet taiKhoanChiTiet;
    PhongDangKy phongDangKy;
    EditText ed_DienTich, ed_GiaPhong, ed_DiaChi, ed_BoSung, ed_TraLoiBaoMat;
    Spinner spn_KhuVuc, spn_CauHoiBaoMat;
    RatingBar rtb_DanhGia;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_phong);
        taiKhoanChiTiet = (TaiKhoanChiTiet)getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);

        // Lay cac doi tuong tren giao dien
        phongDangKy = new PhongDangKy(this);
        phongDangKy.setIdN(taiKhoanChiTiet.getIdN());
        ed_DienTich = (EditText)findViewById(R.id.dienTich);
        ed_GiaPhong = (EditText)findViewById(R.id.giaPhong);
        ed_DiaChi = (EditText)findViewById(R.id.diaChi);
        ed_BoSung = (EditText)findViewById(R.id.thongTinBoSung);
        ed_TraLoiBaoMat = (EditText)findViewById(R.id.traLoiCauHoiBaoMat);

        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        spn_KhuVuc = (Spinner)findViewById(R.id.khuVuc);
        ArrayAdapter adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phongDangKy.listKhuVuc);
        adapterKhuVuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_KhuVuc.setAdapter(adapterKhuVuc);
        spn_KhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phongDangKy.setKhuVuc(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                phongDangKy.setKhuVuc(0);
            }
        });
        spn_CauHoiBaoMat = (Spinner)findViewById(R.id.cauHoiBaoMat);
        ArrayAdapter adapterCauHoi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phongDangKy.listCauHoi);
        adapterCauHoi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_CauHoiBaoMat.setAdapter(adapterCauHoi);
        spn_CauHoiBaoMat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phongDangKy.setCauHoiBaoMat(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                phongDangKy.setCauHoiBaoMat(0);
            }
        });

        rtb_DanhGia = (RatingBar)findViewById(R.id.danhGia);
        rtb_DanhGia.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                phongDangKy.setDanhGia(rating);
            }
        });
    }
    public void onClickBtnHuyBo(View v){
        finish();
    }
    public void onClickBtnTaoPhong(View v){
        phongDangKy.setDienTich(ed_DienTich.getText().toString());
        phongDangKy.setGiaCa(ed_GiaPhong.getText().toString());
        phongDangKy.setDiaChiCuThe(ed_DiaChi.getText().toString());
        phongDangKy.setThongTinBoSung(ed_BoSung.getText().toString());
        phongDangKy.setTraLoiCauHoiBaoMat(ed_TraLoiBaoMat.getText().toString());
        if (kiemTraPhongDangKy(phongDangKy)) {
            new TaoPhongAsyncTask().execute(phongDangKy);
        }
    }
    public boolean kiemTraPhongDangKy(PhongDangKy phongDangKy){
        boolean kiemTra = true;
        StringBuilder message = new StringBuilder("Chưa có ");
        if (phongDangKy.getDienTich().equals("")) {message.append("\"Diện tích\""); kiemTra = false;}
        else if (phongDangKy.getGiaCa().equals("")) {message.append("\"Giá phòng\""); kiemTra = false;}
        else if (phongDangKy.getDiaChiCuThe().equals("")) {message.append("\"Địa chỉ\""); kiemTra = false;}
        else if (phongDangKy.getTraLoiCauHoiBaoMat().equals("")) {message.append("\"Trả lời bảo mật\""); kiemTra = false;}
        else if (phongDangKy.getDanhGia() == 0) {message.append("\"Đánh giá\""); kiemTra = false;}
        if (!kiemTra) {
            builder.setTitle("Nhắc");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage(message.toString());
            builder.show();
        }
        return kiemTra;
    }
    class TaoPhongAsyncTask extends AsyncTask<PhongDangKy, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DangKyPhong.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(PhongDangKy... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.taoPhong(params[0]));
                Node node = document.getElementsByTagName("result").item(0);
                result = node.getTextContent();
                return result;
            } catch (Exception e){
                return "Exception";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s.equals("Exception")){
                builder.setTitle("Lỗi");
                builder.setMessage("Không thể kết nối đến Server");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();
            } if (s.equals("true")){
                builder.setTitle("Chúc mừng");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("Bạn đã tạo phòng thành công! Cảm ơn!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            } else {
                builder.setTitle("Thông báo");
                builder.setMessage(s);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            }
        }
    }
}
