package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.TaiKhoan;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DangNhap extends Activity {
    Button btn_dangky, btn_dangnhap;
    EditText ed_matKhau;
    AutoCompleteTextView ed_email;
    String[] items = {"hoanglong180695@gmail.com", "hoanglong_vt95@yahoo.com"};
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);
        ed_email = (AutoCompleteTextView)findViewById(R.id.editText);
        ed_email.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        ed_matKhau = (EditText)findViewById(R.id.editText2);
        btn_dangky = (Button)findViewById(R.id.btn_dangky);
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKyEmail.class);
                startActivity(intent);
            }
        });
        btn_dangnhap = (Button)findViewById(R.id.btn_dangnhap);
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, matKhau;
                email = ed_email.getText().toString();
                matKhau = ed_matKhau.getText().toString();
                if (email.contains("@")){
                    new LoginAsyncTask().execute(email, matKhau);
                } else {
                    builder.setTitle("Nhắc");
                    builder.setMessage("Email không hơp lệ");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();
                }
            }
        });

    }

    class LoginAsyncTask extends AsyncTask<String, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DangNhap.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(Variable.login(params[0], params[1]));
                Node node = document.getElementsByTagName("result").item(0);
                String result = node.getTextContent();
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
                builder.setMessage("Không thể kết nối với Server");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();
            } else if (result.equals("false")){
                builder.setTitle("Thông báo");
                builder.setMessage("Email hoặc mật khẩu không chính xác");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            } else {
                TaiKhoanChiTiet taiKhoanChiTiet = new TaiKhoanChiTiet();
                taiKhoanChiTiet.setIdN(result);
                taiKhoanChiTiet.setEmail(ed_email.getText().toString());
                taiKhoanChiTiet.setMatKhau(ed_matKhau.getText().toString());
                ed_matKhau.setText(null);
                Intent intent = new Intent(DangNhap.this, MainActivity.class);
                intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoanChiTiet);
                startActivity(intent);
            }
        }
    }
    public void onClickQuyenMK(View v){
        Intent intent = new Intent(this, QuyenMKEmail.class);
        startActivity(intent);
    }
}
