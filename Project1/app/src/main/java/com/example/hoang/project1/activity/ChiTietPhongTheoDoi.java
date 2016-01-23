package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.ItemPhong;
import com.example.hoang.project1.model.ItemPhongDangKy;
import com.example.hoang.project1.model.ItemPhongDeXuat;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.sqldatabase.ItemPhongHelper;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 12/6/2015.
 */
public class ChiTietPhongTheoDoi extends Activity {
    ItemPhong itemPhong;
    TaiKhoanChiTiet taiKhoanChiTiet;
    AlertDialog.Builder builder;
    TextView idP, dienTich, giaCa, khuVuc, diaChi, tinhTrang, thongTinBoSung;
    RatingBar danhGia;
    Button denO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemPhong = (ItemPhong) getIntent().getExtras().getSerializable(Variable.KEY_ITEMPHONG);
        taiKhoanChiTiet = (TaiKhoanChiTiet) getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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
            denO = (Button)findViewById(R.id.phongDangKy_button);
            denO.setText("Đến ở");
        } else {
            setContentView(R.layout.chi_tiet_phong_de_xuat);
            idP = (TextView)findViewById(R.id.phongDeXuat_idP);
            dienTich = (TextView)findViewById(R.id.phongDeXuat_dienTich);
            giaCa = (TextView)findViewById(R.id.phongDeXuat_giaCa);
            khuVuc = (TextView)findViewById(R.id.phongDeXuat_khuVuc);
            diaChi = (TextView)findViewById(R.id.phongDeXuat_diaChi);
            thongTinBoSung = (TextView)findViewById(R.id.phongDeXuat_boSung);
            denO = (Button)findViewById(R.id.phongDeXuat_button);
            denO.setVisibility(View.GONE);
        }
        new ChiTietPhongTheoDoiAsyncTask().execute(itemPhong);
    }
    class ChiTietPhongTheoDoiAsyncTask extends AsyncTask<ItemPhong, Void, Document>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChiTietPhongTheoDoi.this);
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
                    denO.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new LayCauHoiBaoMatAsyncTask().execute(itemPhong.getIdP());
                        }
                    });
                }
            }
        }
    }
    class LayCauHoiBaoMatAsyncTask extends AsyncTask<String, Void, Document>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChiTietPhongTheoDoi.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.layCauHoiBaoMat(params[0]));
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
                builder.setMessage("Không thể kết nỗi đến Server");
                builder.show();
            } else {
                Node node = document.getElementsByTagName("cauHoiBaoMat").item(0);
                if (node == null) {
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Không có dữ liệu");
                    builder.show();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietPhongTheoDoi.this);
                    String[] listCauHoi = getResources().getStringArray(R.array.cauHoiBaoMat);
                    final String cauHoiBaoMat = listCauHoi[Integer.valueOf(node.getTextContent())];
                    View cauHoi = getLayoutInflater().inflate(R.layout.cauhoibaomat, null);
                    final TextView tv_cauHoi = (TextView) cauHoi.findViewById(R.id.cauHoi);
                    tv_cauHoi.setText(cauHoiBaoMat);
                    final EditText ed_cauTL = (EditText) cauHoi.findViewById(R.id.edCauTraLoi);
                    builder1.setTitle("Câu hỏi bảo mật");
                    builder1.setIcon(android.R.drawable.ic_dialog_info);
                    builder1.setView(cauHoi);
                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DenOPhongAsyncTask().execute(itemPhong.getIdP(), taiKhoanChiTiet.getIdN(), ed_cauTL.getText().toString());
                        }
                    });
                    builder1.setNegativeButton("Cancel", null);
                    builder1.show();
                }
            }
        }
    }
    class DenOPhongAsyncTask extends AsyncTask<String, Void, Document>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ChiTietPhongTheoDoi.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.denOPhong(params[0], params[1], params[2]));
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
                builder.setMessage("Không thể kết nỗi đến Server");
                builder.show();
            }else {
                String result = document.getElementsByTagName("result").item(0).getTextContent();
                if (result.equals("true")){
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn đã vào phòng thành công");
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    builder.setTitle("Thông báo");
                    builder.setMessage(result);
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.show();
                }
            }
        }
    }
}
