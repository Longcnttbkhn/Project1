package com.example.hoang.project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.TaiKhoan;
import com.example.hoang.project1.util.Variable;

/**
 * Created by hoang on 10/29/2015.
 */
public class DangKyTen extends Activity {
    Button btn_next;
    TaiKhoan taiKhoan;
    EditText tenHienThi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky2);
        // Nhan doi tuong taiKhoan tu Activity truoc do
        taiKhoan = (TaiKhoan) getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);
        // Lay cac doi tuong tu giao dien
        btn_next = (Button)findViewById(R.id.btn_next2);
        tenHienThi = (EditText)findViewById(R.id.editText4);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taiKhoan.setTenHienThi(tenHienThi.getText().toString());
                Intent intent = new Intent(DangKyTen.this, DangKyPassword.class);
                intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoan);
                startActivity(intent);
            }
        });
    }
}
