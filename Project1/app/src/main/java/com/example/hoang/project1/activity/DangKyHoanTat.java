package com.example.hoang.project1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hoang.project1.R;

/**
 * Created by hoang on 10/29/2015.
 */
public class DangKyHoanTat extends Activity {
    Button btn_HoanTat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky5);
        btn_HoanTat = (Button)findViewById(R.id.button3);
        btn_HoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyHoanTat.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }
}
