package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.TaiKhoan;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 10/29/2015.
 */
public class DangKyPassword extends Activity {
    Button btn_next;
    TaiKhoan taiKhoan;
    EditText ed_MatKhau;
    EditText ed_NhapLaiMatKhau;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky3);
        taiKhoan = (TaiKhoan) getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);
        ed_MatKhau = (EditText)findViewById(R.id.editText5);
        ed_NhapLaiMatKhau = (EditText)findViewById(R.id.editText6);
        btn_next = (Button)findViewById(R.id.btn_next3);
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhau = ed_MatKhau.getText().toString();
                String nhapLaiMatKhau = ed_NhapLaiMatKhau.getText().toString();
//                Toast.makeText(DangKyPassword.this, "matkhau: " + matKhau + "\n" + "nhap lai mat khau: " + nhapLaiMatKhau, Toast.LENGTH_SHORT).show();
                if (matKhau.equals(nhapLaiMatKhau)){
                    taiKhoan.setMatKhau(matKhau);
                    new TaoTaiKhoanAsyncTask().execute(taiKhoan);
                } else {
                    builder.setTitle("Nhắc");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("Mật khẩu và nhập lại mật khẩu phải trùng nhau");
                    builder.show();
                }
            }
        });
    }

    class TaoTaiKhoanAsyncTask extends AsyncTask<TaiKhoan, Void, String> {
        ProgressDialog dialog = new ProgressDialog(DangKyPassword.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Xin Chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(TaiKhoan... taiKhoans) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(Variable.taoTaiKhoan(taiKhoans[0]));
                Node node = document.getElementsByTagName("result").item(0);
                String result  = node.getTextContent();
                return result;
            } catch (Exception e){
                return "Exception";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals("Exception")){
                builder.setTitle("Lỗi");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("Không thể kết nối đến Server");
                builder.show();
            }
            if (result.equals("true")) {
                Intent intent = new Intent(DangKyPassword.this, DangKyHoanTat.class);
                startActivity(intent);
            } else {
                builder.setTitle("Thông báo");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage(result);
                builder.show();
            }
        }
    }
}
