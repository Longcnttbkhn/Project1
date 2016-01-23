package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.ItemPhong;
import com.example.hoang.project1.model.ItemPhongDangKy;
import com.example.hoang.project1.model.ItemPhongDeXuat;
import com.example.hoang.project1.model.PhongDangKy;
import com.example.hoang.project1.sqldatabase.ItemPhongHelper;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 12/3/2015.
 */
public class ChiTietPhong extends Activity {
    ItemPhong itemPhong;
    AlertDialog.Builder builder;
    TextView idP, dienTich, giaCa, khuVuc, diaChi, tinhTrang, thongTinBoSung;
    RatingBar danhGia;
    Button theoDoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemPhong = (ItemPhong) getIntent().getExtras().getSerializable(Variable.KEY_ITEMPHONG);
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        if (itemPhong.getKieuPhong().equals("dangKy")){
            setContentView(R.layout.chi_tiet_phong_dang_ky);
            idP = (TextView)findViewById(R.id.phongDangKy_idP);
            dienTich = (TextView)findViewById(R.id.phongDangKy_dienTich);
            giaCa = (TextView)findViewById(R.id.phongDangKy_giaCa);
            khuVuc = (TextView)findViewById(R.id.phongDangKy_khuVuc);
            diaChi = (TextView)findViewById(R.id.phongDangKy_diaChi);
            tinhTrang = (TextView)findViewById(R.id.phongDangKy_tinhTrang);
            thongTinBoSung = (TextView)findViewById(R.id.phongDangKy_boSung);
            danhGia = (RatingBar)findViewById(R.id.phongDangKy_danhGia);
            theoDoi = (Button)findViewById(R.id.phongDangKy_button);
        } else {
            setContentView(R.layout.chi_tiet_phong_de_xuat);
            idP = (TextView)findViewById(R.id.phongDeXuat_idP);
            dienTich = (TextView)findViewById(R.id.phongDeXuat_dienTich);
            giaCa = (TextView)findViewById(R.id.phongDeXuat_giaCa);
            khuVuc = (TextView)findViewById(R.id.phongDeXuat_khuVuc);
            diaChi = (TextView)findViewById(R.id.phongDeXuat_diaChi);
            thongTinBoSung = (TextView)findViewById(R.id.phongDeXuat_boSung);
            theoDoi = (Button)findViewById(R.id.phongDeXuat_button);
        }
        new ChiTietPhongAsyncTask().execute(itemPhong);
    }
    class ChiTietPhongAsyncTask extends AsyncTask<ItemPhong, Void, Document>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChiTietPhong.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Document doInBackground(ItemPhong... params) {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.chiTietPhong(params[0]));
                return document;
            } catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            dialog.dismiss();
            if (document == null) {
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Lỗi");
                builder.setMessage("Không thể kết nối đến Server");
                builder.show();
            } else {
                Node resuft = document.getElementsByTagName("result").item(0);
                NodeList content = resuft.getChildNodes();
                idP.setText(itemPhong.getIdP());
                dienTich.setText(itemPhong.getDienTich());
                khuVuc.setText(itemPhong.getKhuVuc());
                giaCa.setText(itemPhong.getGiaCa());

                ItemPhongDeXuat itemPhongDeXuat = new ItemPhongDeXuat();
                itemPhongDeXuat.setDiaChiCuThe(content.item(0).getTextContent());
                itemPhongDeXuat.setThongTinBoSung(content.item(1).getTextContent());

                diaChi.setText(itemPhongDeXuat.getDiaChiCuThe());
                thongTinBoSung.setText(itemPhongDeXuat.getThongTinBoSung());

                if (itemPhong.getKieuPhong().equals("dangKy")){
                    ItemPhongDangKy itemPhongDangKy = new ItemPhongDangKy();
                    itemPhongDangKy.setTinhTrang(content.item(2).getTextContent());
                    itemPhongDangKy.setDanhGia(content.item(3).getTextContent());

                    tinhTrang.setText(itemPhongDangKy.getTinhTrang());
                    danhGia.setRating(itemPhongDangKy.getDanhGia());
                    danhGia.setEnabled(false);
                }

                theoDoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemPhongHelper helper = new ItemPhongHelper(ChiTietPhong.this);
                        SQLiteDatabase database = helper.getWritableDatabase();
                        insertItemPhong(itemPhong, database);
                        helper.close();
                    }
                });
            }
        }
    }
    public void insertItemPhong(ItemPhong itemPhong, SQLiteDatabase db){
        ContentValues cV = new ContentValues();
        cV.put("idP", itemPhong.getIdP());
        cV.put("kieuPhong", itemPhong.getKieuPhong());
        cV.put("khuVuc", itemPhong.getKhuVuc());
        cV.put("dienTich", itemPhong.getDienTich());
        cV.put("giaCa", itemPhong.getGiaCa());
        String message;
        if (db.insert("itemphong", null, cV) == -1){
            message = "Phòng đã có trong danh mục \"Đang theo dõi\"";
        } else
            message = "Đã thêm vào danh mục \"Đang theo dõi\"";
        builder.setTitle("Thông báo");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(message);
        builder.show();
    }
}
