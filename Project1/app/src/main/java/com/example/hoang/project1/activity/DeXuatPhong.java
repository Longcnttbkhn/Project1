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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.PhongDeXuat;
import com.example.hoang.project1.model.TaiKhoan;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 11/12/2015.
 */
public class DeXuatPhong extends Activity {
    TaiKhoanChiTiet taiKhoanChiTiet;
    PhongDeXuat phongDeXuat;
    EditText ed_DienTich, ed_GiaPhong, ed_DiaChi, ed_BoSung;
    Spinner spn_KhuVuc;
    AlertDialog.Builder builder;
    Button btn_huyBo, btn_deXuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.de_xuat_phong);
        taiKhoanChiTiet = (TaiKhoanChiTiet)getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);

        // Lay cac doi tuong tren giao dien
        phongDeXuat = new PhongDeXuat(this);
        phongDeXuat.setIdN(taiKhoanChiTiet.getIdN());
        ed_DienTich = (EditText)findViewById(R.id.deXuatDienTich);
        ed_GiaPhong = (EditText)findViewById(R.id.deXuatGiaPhong);
        ed_DiaChi = (EditText)findViewById(R.id.deXuatDiaChi);
        ed_BoSung = (EditText)findViewById(R.id.deXuatBoSung);

        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        spn_KhuVuc = (Spinner)findViewById(R.id.deXuatKhuVuc);
        ArrayAdapter adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phongDeXuat.listKhuVuc);
        adapterKhuVuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_KhuVuc.setAdapter(adapterKhuVuc);
        spn_KhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phongDeXuat.setKhuVuc(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                phongDeXuat.setKhuVuc(0);
            }
        });

        btn_deXuat = (Button)findViewById(R.id.btn_deXuat);
        btn_huyBo = (Button)findViewById(R.id.btn_deXuatHuyBo);
        btn_deXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phongDeXuat.setDienTich(ed_DienTich.getText().toString());
                phongDeXuat.setGiaCa(ed_GiaPhong.getText().toString());
                phongDeXuat.setDiaChiCuThe(ed_DiaChi.getText().toString());
                phongDeXuat.setThongTinBoSung(ed_BoSung.getText().toString());
                if (kiemTraPhongDeXuat(phongDeXuat)) {
                    new DeXuatPhongAsyncTask().execute(phongDeXuat);
                }
            }

            public boolean kiemTraPhongDeXuat(PhongDeXuat phongDeXuat){
                boolean kiemTra = true;
                StringBuilder message = new StringBuilder("Chưa có ");
                if (phongDeXuat.getDienTich().equals("")) {message.append("\"Diện tích\""); kiemTra = false;}
                else if (phongDeXuat.getGiaCa().equals("")) {message.append("\"Giá phòng\""); kiemTra = false;}
                else if (phongDeXuat.getDiaChiCuThe().equals("")) {message.append("\"Địa chỉ\""); kiemTra = false;}
                if (!kiemTra) {
                    builder.setTitle("Nhắc");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage(message.toString());
                    builder.show();
                }
                return kiemTra;
            }

        });
        btn_huyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    class DeXuatPhongAsyncTask extends AsyncTask<PhongDeXuat, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DeXuatPhong.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(PhongDeXuat... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.deXuatPhong(params[0]));
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
            }
            if (s.equals("true")){
                builder.setTitle("Chúc mừng");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("Bạn đã đề xuất phòng thành công! Cảm ơn!");
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
