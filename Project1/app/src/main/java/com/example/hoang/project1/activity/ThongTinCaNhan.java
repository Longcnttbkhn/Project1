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
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 11/13/2015.
 */
public class ThongTinCaNhan extends Activity {
    TaiKhoanChiTiet taiKhoanChiTiet;
    AlertDialog.Builder builder;
    EditText ed_tenHienThi;
    Spinner spn_tinhTrang;
    TextView tv_email, tv_idPhong;
    Button btn_doiMK, btn_doiTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_tin_ca_nhan);

        taiKhoanChiTiet = (TaiKhoanChiTiet)getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        new ChiTietNguoiDungAsyncTask().execute(taiKhoanChiTiet.getIdN());
    }

    public void disChangeAble(){
        ed_tenHienThi.setEnabled(false);
        spn_tinhTrang.setEnabled(false);
        btn_doiMK.setEnabled(true);
    }

    public void changeAble(){
        ed_tenHienThi.setEnabled(true);
        if (taiKhoanChiTiet.getTinhTrang() != 2){
            spn_tinhTrang.setEnabled(true);
        }
        btn_doiMK.setEnabled(false);
    }
    class ChiTietNguoiDungAsyncTask extends AsyncTask<String, Void, Document>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ThongTinCaNhan.this);
            dialog.setMessage("Đang tải dữ liệu ...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(Variable.chiTietNguoiDung(params[0]));
                return document;
            } catch (Exception e){
                return null;
            }

        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            dialog.dismiss();
            if (document == null) {
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Lỗi");
                builder.setMessage("Không thể kết nỗi đến Server");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            } else {
                Node result = document.getElementsByTagName("result").item(0);
                String textResult = result.getTextContent();
                if (textResult.equals("false")) {
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Đã có lỗi xảy ra");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    NodeList thongTinCaNhan = result.getChildNodes();
                    taiKhoanChiTiet.setTenHienThi(thongTinCaNhan.item(0).getTextContent());
                    taiKhoanChiTiet.setEmail(thongTinCaNhan.item(1).getTextContent());
                    taiKhoanChiTiet.setTinhTrang(thongTinCaNhan.item(2).getTextContent());
                    taiKhoanChiTiet.setIdP(thongTinCaNhan.item(3).getTextContent());

                    tv_email = (TextView)findViewById(R.id.ttcn_email);
                    tv_idPhong = (TextView)findViewById(R.id.ttcn_idPhong);
                    ed_tenHienThi = (EditText)findViewById(R.id.ttcn_tenHienThi);

                    tv_email.setText(taiKhoanChiTiet.getEmail());
                    tv_idPhong.setText(taiKhoanChiTiet.getIdP());
                    ed_tenHienThi.setText(taiKhoanChiTiet.getTenHienThi());

                    spn_tinhTrang = (Spinner)findViewById(R.id.ttcn_tinhTrang);
                    ArrayAdapter adapter = new ArrayAdapter(ThongTinCaNhan.this, android.R.layout.simple_spinner_item, taiKhoanChiTiet.getTINH_TRANG());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_tinhTrang.setAdapter(adapter);
                    spn_tinhTrang.setSelection(taiKhoanChiTiet.getTinhTrang());
                    spn_tinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            taiKhoanChiTiet.setTinhTrang(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    btn_doiMK = (Button)findViewById(R.id.ttcn_doiMatKhau);
                    btn_doiTT = (Button)findViewById(R.id.ttcn_doiThongTin);

                    disChangeAble();

                    btn_doiTT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (btn_doiTT.getText().equals("Đổi thông tin")) {
                                changeAble();
                                btn_doiTT.setText("Lưu");
                            } else {
                                if (ed_tenHienThi.getText().toString().equals("")) {
                                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                                    builder.setMessage("Chưa có \"Tên hiển thị\"");
                                    builder.setTitle("Lỗi");
                                    builder.show();
                                } else {
                                    taiKhoanChiTiet.setTenHienThi(ed_tenHienThi.getText().toString());
                                    new UpdateThongTinCaNhanAsyncTask().execute(taiKhoanChiTiet);
                                }
                                disChangeAble();
                                btn_doiTT.setText("Đổi thông tin");
                            }
                        }
                    });
                    btn_doiMK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ThongTinCaNhan.this, DoiMatKhau.class);
                            intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoanChiTiet);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
    class UpdateThongTinCaNhanAsyncTask extends AsyncTask<TaiKhoanChiTiet, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ThongTinCaNhan.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(TaiKhoanChiTiet... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.updateThongTinCaNhan(params[0]));
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
                builder.setTitle("Thông báo");
                builder.setMessage("Thay đổi thành công");
                builder.setIcon(android.R.drawable.ic_dialog_info);
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
