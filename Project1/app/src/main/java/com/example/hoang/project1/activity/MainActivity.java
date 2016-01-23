package com.example.hoang.project1.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.fragment.Content;
import com.example.hoang.project1.fragment.MainContent;
import com.example.hoang.project1.fragment.MainMenu;
import com.example.hoang.project1.fragment.TimPhong;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

/**
 * Created by hoang on 10/30/2015.
 */
public class MainActivity extends FragmentActivity {
    private TextView title;
    private TaiKhoanChiTiet taiKhoanChiTiet;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Long", "onCreat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        taiKhoanChiTiet = (TaiKhoanChiTiet)getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);
//        Toast.makeText(this, taiKhoan.getIdN(), Toast.LENGTH_SHORT).show();
//        lấy dữ liệu cho menu
//        Lấy về đối tượng
        title = (TextView)findViewById(R.id.tv_title);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                    title.setText("Tìm nhà trọ sinh viên");
                else title.setText("Tìm phòng");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Content content = (Content) getSupportFragmentManager().findFragmentById(R.id.content);
                title.setText(content.getTitle());
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fT = fM.beginTransaction();
            MainMenu mainMenu = new MainMenu();
            mainMenu.setTaiKhoanChiTiet(taiKhoanChiTiet);
            mainMenu.setDrawerLayout(drawerLayout);
            fT.replace(R.id.menu, mainMenu);

            MainContent mainContent = new MainContent();
            mainContent.setLink(Variable.layPhong());
            mainContent.setTitle("Phòng mới");
            fT.replace(R.id.content, mainContent);

            TimPhong timPhong = new TimPhong();
            timPhong.setTitle("Tìm kiếm");
            timPhong.setDrawerLayout(drawerLayout);
            fT.replace(R.id.timKiem, timPhong);

            fT.commit();
        title.setText("Phòng mới");
    }
    public void onClickMenuButton(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public TaiKhoanChiTiet getTaiKhoanChiTiet() {
        return taiKhoanChiTiet;
    }

    public void onClickSearchButton(View v){
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Long", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Long", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Long", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Long", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Long", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Long", "onStart");
    }
}
