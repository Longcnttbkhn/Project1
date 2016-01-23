package com.example.hoang.project1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.PhongTimKiem;
import com.example.hoang.project1.util.Variable;

import java.security.cert.Certificate;

/**
 * Created by hoang on 12/7/2015.
 */
public class TimPhong extends Content {
    DrawerLayout drawerLayout;

    PhongTimKiem phongTimKiem;
    EditText idP, dienTichMax, dienTichMin, giaMax, giaMin;
    Spinner khuVuc;
    CheckBox phongDK, phongDX, phongTrong, canTimThem;
    Button btn_timKiem;
    AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        phongTimKiem = new PhongTimKiem(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.tim_phong, container, false);
        idP = (EditText) view.findViewById(R.id.timPhong_idP);
        dienTichMax = (EditText) view.findViewById(R.id.timPhong_dienTichMax);
        dienTichMin = (EditText) view.findViewById(R.id.timPhong_dienTichMin);
        giaMax = (EditText) view.findViewById(R.id.timPhong_giaMax);
        giaMin = (EditText) view.findViewById(R.id.timPhong_giaMin);

        phongDK = (CheckBox) view.findViewById(R.id.timPhong_DK);
        phongDX = (CheckBox) view.findViewById(R.id.timPhong_DX);
        phongTrong = (CheckBox) view.findViewById(R.id.timPhong_trong);
        canTimThem = (CheckBox) view.findViewById(R.id.timPhong_canTimThem);

        btn_timKiem = (Button) view.findViewById(R.id.timPhong_btnTimKiem);

        khuVuc = (Spinner) view.findViewById(R.id.timPhong_khuVuc);
        ArrayAdapter adapterKhuVuc = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, phongTimKiem.listKhuVuc);
        adapterKhuVuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        khuVuc.setAdapter(adapterKhuVuc);
        khuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phongTimKiem.setKhuVuc(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                phongTimKiem.setKhuVuc(0);
            }
        });

        btn_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phongTimKiem.setIdP(idP.getText().toString());
                phongTimKiem.setDientTichMin(dienTichMin.getText().toString());
                phongTimKiem.setDienTichMax(dienTichMax.getText().toString());
                phongTimKiem.setGiaMin(giaMin.getText().toString());
                phongTimKiem.setGiaMax(giaMax.getText().toString());

                if (phongDK.isChecked()) {
                    if (phongDX.isChecked()) {
                        phongTimKiem.setKieuPhong("tatCa");
                    } else phongTimKiem.setKieuPhong("dangKy");
                } else if (phongDX.isChecked()) {
                    phongTimKiem.setKieuPhong("deXuat");
                } else phongTimKiem.setKieuPhong("");

                if (phongTrong.isChecked()) {
                    if (canTimThem.isChecked()) {
                        phongTimKiem.setTinhTrangPhong("tatCa");
                    } else phongTimKiem.setTinhTrangPhong("phongTrong");
                } else if (canTimThem.isChecked()) {
                    phongTimKiem.setTinhTrangPhong("canTimThem");
                } else phongTimKiem.setTinhTrangPhong("");

                if (kiemTraPhongTimKiem(phongTimKiem)){
                    FragmentManager fM = getActivity().getSupportFragmentManager();
                    FragmentTransaction fT = fM.beginTransaction();
                    MainContent mainContent = new MainContent();
                    mainContent.setTitle("Kết quả tìm kiếm");
                    mainContent.setLink(Variable.timPhong(phongTimKiem));
                    fT.replace(R.id.content, mainContent);
                    fT.commit();
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        return view;
    }
    public boolean kiemTraPhongTimKiem(PhongTimKiem phongTimKiem){
        boolean kiemTra = true;
        StringBuilder message = new StringBuilder("Lỗi ");
        if (!phongTimKiem.kiemTraDienTichPhong()) {kiemTra = false; message.append("\"Diện tích phòng\"");}
        else if (!phongTimKiem.kiemTraGiaPhong()) {kiemTra = false; message.append("\"Giá phòng\"");}
        else if (phongTimKiem.getKieuPhong().equals("")) {kiemTra = false; message.append("\"Kiểu phòng\" (phải chọn ít nhất 1)");}
        else if (phongTimKiem.getTinhTrangPhong().equals("")) {kiemTra = false; message.append("\"Tình trạng phòng\" (phải chọn ít nhất 1)");}
        if (!kiemTra) {
            builder.setTitle("Nhắc");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage(message.toString());
            builder.show();
        }
        return kiemTra;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }
}
