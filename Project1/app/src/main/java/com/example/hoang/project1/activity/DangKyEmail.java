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
public class DangKyEmail extends Activity {
    Button btn_next;
    EditText ed_Email;
    String email;
    TaiKhoan taiKhoan;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky1);

        btn_next = (Button)findViewById(R.id.btn_next1);
        ed_Email = (EditText)findViewById(R.id.editText3);
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
                // Lay email tu EditText
                email = ed_Email.getText().toString();
                // Kiem tra dinh dang email
                if (email.contains("@")) {
                    new KiemTraEmailAsyncTask().execute(email);
                }
                else {
                    builder.setTitle("Nhắc");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("Email không hợp lệ");
                    builder.create().show();
                }
            }
        });
    }
    // Kiem tra email chay o Background
    class KiemTraEmailAsyncTask extends AsyncTask<String, Void, String>{
        ProgressDialog dialog = new ProgressDialog(DangKyEmail.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang kiểm tra Email");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // Doc Xml gui ve
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(Variable.kiemTraEmail(params[0]));
                Node node = document.getElementsByTagName("result").item(0);
                String result = node.getTextContent();
                // Kiem tra gia tri tra ve
                return result;
            } catch (Exception e){
                return "Exception";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals("false")) {
                // Tao mot doi tuong taiKhoan de luu tru thong tin
                taiKhoan = new TaiKhoan();
                taiKhoan.setEmail(email);

                // dong goi va gui doi tuong taiKhoan den Activity tiep theo
                Intent intent = new Intent(DangKyEmail.this, DangKyTen.class);
                intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoan);
                startActivity(intent);
            } else if (result.equals("true")){
                builder.setTitle("Thông báo");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("Email đã có trên hệ thống");
                builder.show();
            } else {
                builder.setTitle("Lỗi");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("Không thể kết nối đến Server");
                builder.show();
            }
        }
    }

}
