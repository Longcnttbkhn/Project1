package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 11/23/2015.
 */
public class DoiMatKhau extends Activity {
    TaiKhoanChiTiet taiKhoanChiTiet;
    EditText ed_matKhauCu, ed_matKhauMoi, ed_nhapLaiMatKhau;
    Button btn_doiMatKhau;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doi_mat_khau);
        taiKhoanChiTiet = (TaiKhoanChiTiet)getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);
        ed_matKhauCu = (EditText)findViewById(R.id.matKhauCu);
        ed_matKhauMoi = (EditText)findViewById(R.id.matKhauMoi);
        ed_nhapLaiMatKhau = (EditText)findViewById(R.id.nhapLaiMatKhau);
        btn_doiMatKhau = (Button)findViewById(R.id.btn_doiMatKhau);
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        btn_doiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhauCu, matKhauMoi, nhapLaiMatKhau;
                matKhauCu = ed_matKhauCu.getText().toString();
                matKhauMoi = ed_matKhauMoi.getText().toString();
                nhapLaiMatKhau = ed_nhapLaiMatKhau.getText().toString();

                if (matKhauCu.equals(taiKhoanChiTiet.getMatKhau())){
                    if (matKhauMoi.equals(nhapLaiMatKhau)){
                        new DoiMatKhauAsyncTask().execute(taiKhoanChiTiet.getIdN(), matKhauCu, matKhauMoi);
                    } else {
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setTitle("Lỗi");
                        builder.setMessage("Mật khẩu nhập lại không đúng");
                        builder.show();
                    }
                } else {
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setTitle("Lỗi");
                    builder.setMessage("Mật khẩu cũ không đúng");
                    builder.show();
                }
            }
        });
    }
    class DoiMatKhauAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DoiMatKhau.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.doiMatKhau(params[0], params[1], params[2]));
                Node node = document.getElementsByTagName("result").item(0);
                result = node.getTextContent();
                return result;
            } catch (Exception e) {
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
            } if (s.equals("true")) {
                builder.setTitle("Thông báo");
                builder.setMessage("Thay đổi thành công");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
