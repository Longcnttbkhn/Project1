package com.example.hoang.project1.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.activity.DangKyPhong;
import com.example.hoang.project1.activity.DeXuatPhong;
import com.example.hoang.project1.activity.QuanLyPhong;
import com.example.hoang.project1.activity.ThongTinCaNhan;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

/**
 * Created by hoang on 12/4/2015.
 */
public class MainMenu extends Fragment {
    private ListView listView;
    private String[] listMenu;
    private DrawerLayout drawerLayout;

    private TaiKhoanChiTiet taiKhoanChiTiet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMenu = getResources().getStringArray(R.array.menu);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Long", "MainMenu.onCreatView");
        View view = inflater.inflate(R.layout.list_view, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listMenu));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    FragmentManager fM = getActivity().getSupportFragmentManager();
                    FragmentTransaction fT = fM.beginTransaction();
                    MainContent mainContent = new MainContent();
                    mainContent.setTitle(listMenu[position]);
                    mainContent.setLink(Variable.layPhong());
                    fT.replace(R.id.content, mainContent);
                    fT.commit();
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                if (position == 1) {
                    FragmentManager fM = getActivity().getSupportFragmentManager();
                    FragmentTransaction fT = fM.beginTransaction();
                    PhongTheoDoi phongTheoDoi = new PhongTheoDoi();
                    phongTheoDoi.setTitle(listMenu[position]);
                    fT.replace(R.id.content, phongTheoDoi);
                    fT.commit();
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else if (position == 4) {
                    Intent intent = new Intent(getActivity(), DangKyPhong.class);
                    intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoanChiTiet);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(getActivity(), DeXuatPhong.class);
                    intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoanChiTiet);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(getActivity(), QuanLyPhong.class);
                    intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoanChiTiet);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), ThongTinCaNhan.class);
                    intent.putExtra(Variable.KEY_TAIKHOAN, taiKhoanChiTiet);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public void setTaiKhoanChiTiet(TaiKhoanChiTiet taiKhoanChiTiet) {
        this.taiKhoanChiTiet = taiKhoanChiTiet;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout){
        this.drawerLayout = drawerLayout;
    }
}
