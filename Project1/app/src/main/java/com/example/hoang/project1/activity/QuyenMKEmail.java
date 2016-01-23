package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.project1.R;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 10/29/2015.
 */
public class QuyenMKEmail extends Activity {
    TextView textView;
    EditText editText;
    Button btn_next;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky1);
        textView = (TextView)findViewById(R.id.textView3);
        textView.setText("Nhập email muốn tìm mật khẩu");
        editText = (EditText)findViewById(R.id.editText3);
        btn_next = (Button)findViewById(R.id.btn_next1);
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
                String email = editText.getText().toString();
                if (email.contains("@")) {
                    new QuyenMKAsyncTask().execute(email);
                } else {
                    builder.setTitle("Nhắc");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("Email của bạn không đúng");
                    builder.create().show();
                }
            }
        });
    }
    class QuyenMKAsyncTask extends AsyncTask<String, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(QuyenMKEmail.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(Variable.timMatKhau(params[0]));
                Node node = document.getElementsByTagName("result").item(0);
                String result = node.getTextContent();
                return result;
            }catch (Exception e){
                return "Exception";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s.equals("Exception")){
                builder.setTitle("Lỗi");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setMessage("Không thể kết nối đên server");
                builder.show();
            }
            if (s.equals("true")){
                builder.setTitle("Thông báo");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("Mật khẩu đã được gửi về email của bạn!");
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
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage(s);
                builder.show();
            }
        }
    }
}
