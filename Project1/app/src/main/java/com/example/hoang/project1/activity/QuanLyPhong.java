package com.example.hoang.project1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.PhongQuanLy;
import com.example.hoang.project1.model.TaiKhoanChiTiet;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 11/13/2015.
 */
public class QuanLyPhong extends Activity {
    TaiKhoanChiTiet taiKhoanChiTiet;
    AlertDialog.Builder builder;
    PhongQuanLy phongQuanLy;

    TextView tv_idP, tv_soNguoi, tv_listUser, tv_dienTich, tv_diaChi, tv_khuVuc;
    EditText ed_giaPhong, ed_boSung, ed_traLoi;
    Spinner spn_tinhTrang, spn_cauHoi;
    RatingBar rb_danhGia;
    Button btn_thayDoi, btn_xoaPhong, btn_roiPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quan_ly_phong);
        // Lay doi tuong tai khoan tu Activity truoc do
        taiKhoanChiTiet = (TaiKhoanChiTiet)getIntent().getExtras().getSerializable(Variable.KEY_TAIKHOAN);

        // Xuat thong bao
        builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Load thong tin phong dang o
        new ChiTietPhongDangOAsyncTask().execute(taiKhoanChiTiet.getIdN());

    }
    public void changeAble(){
        ed_boSung.setEnabled(true);
        ed_giaPhong.setEnabled(true);
        ed_traLoi.setEnabled(true);

        spn_tinhTrang.setEnabled(true);
        spn_cauHoi.setEnabled(true);

        rb_danhGia.setEnabled(true);

        btn_roiPhong.setEnabled(false);
        btn_xoaPhong.setEnabled(false);
    }

    public void disChangeAble(){
        ed_boSung.setEnabled(false);
        ed_giaPhong.setEnabled(false);
        ed_traLoi.setEnabled(false);

        spn_tinhTrang.setEnabled(false);
        spn_cauHoi.setEnabled(false);

        rb_danhGia.setEnabled(false);

        btn_roiPhong.setEnabled(true);
        btn_xoaPhong.setEnabled(true);
    }
    class ChiTietPhongDangOAsyncTask extends AsyncTask<String, Void, Document> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(QuanLyPhong.this);
            dialog.setMessage("Đang tải dữ liệu ...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(Variable.chiTietPhongDangO(params[0]));
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
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
            } else {
                Node result = document.getElementsByTagName("result").item(0);
                String textResult = result.getTextContent();
                if (textResult.equals("false")){
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn chưa có phòng đang ở");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    phongQuanLy = new PhongQuanLy(QuanLyPhong.this);
                    phongQuanLy.setIdN(taiKhoanChiTiet.getIdN());
                    NodeList chiTietPhong = result.getChildNodes();
                    phongQuanLy.setIdP(chiTietPhong.item(0).getTextContent());
                    phongQuanLy.setDienTich(chiTietPhong.item(1).getTextContent());
                    phongQuanLy.setGiaCa(chiTietPhong.item(2).getTextContent());
                    phongQuanLy.setKhuVuc(chiTietPhong.item(3).getTextContent());
                    phongQuanLy.setDiaChiCuThe(chiTietPhong.item(4).getTextContent());
                    phongQuanLy.setThongTinBoSung(chiTietPhong.item(5).getTextContent());
                    phongQuanLy.setTinhTrang(chiTietPhong.item(6).getTextContent());
                    phongQuanLy.setSoNguoi(chiTietPhong.item(7).getTextContent());
                    phongQuanLy.setCauHoiBaoMat(chiTietPhong.item(9).getTextContent());
                    phongQuanLy.setTraLoiCauHoiBaoMat(chiTietPhong.item(10).getTextContent());
                    phongQuanLy.setDanhGia(chiTietPhong.item(11).getTextContent());
                    NodeList tenHienThi = chiTietPhong.item(8).getChildNodes();
                    for (int i = 0 ; i < tenHienThi.getLength(); i++){
                        phongQuanLy.addNguoiDangO(tenHienThi.item(i).getTextContent());
                    }

                    tv_idP = (TextView)findViewById(R.id.qlp_idP);
                    tv_listUser = (TextView)findViewById(R.id.qlp_listUser);
                    tv_soNguoi = (TextView)findViewById(R.id.qlp_soNguoi);
                    tv_khuVuc = (TextView)findViewById(R.id.qlp_khuVuc);
                    tv_diaChi = (TextView)findViewById(R.id.qlp_diaChi);
                    tv_dienTich = (TextView)findViewById(R.id.qlp_dienTich);

                    tv_idP.setText(phongQuanLy.getIdP());
                    tv_listUser.setText(phongQuanLy.getNguoiDangO());
                    tv_soNguoi.setText(phongQuanLy.getSoNguoi());
                    tv_diaChi.setText(phongQuanLy.getDiaChiCuThe());
                    tv_dienTich.setText(phongQuanLy.getDienTich());
                    tv_khuVuc.setText(phongQuanLy.listKhuVuc[phongQuanLy.getKhuVuc()]);

                    ed_boSung = (EditText)findViewById(R.id.qlp_boSung);
                    ed_giaPhong = (EditText)findViewById(R.id.qlp_gia);
                    ed_traLoi = (EditText)findViewById(R.id.qlp_traLoi);

                    ed_traLoi.setText(phongQuanLy.getTraLoiCauHoiBaoMat());
                    ed_giaPhong.setText(phongQuanLy.getGiaCa());
                    ed_boSung.setText(phongQuanLy.getThongTinBoSung());

                    spn_tinhTrang = (Spinner)findViewById(R.id.qlp_tinhTrang);
                    ArrayAdapter adapter1 = new ArrayAdapter<String>(QuanLyPhong.this, android.R.layout.simple_spinner_item, phongQuanLy.listTinhTrang);
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_tinhTrang.setAdapter(adapter1);
                    spn_tinhTrang.setSelection(phongQuanLy.getTinhTrang());
                    spn_tinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            phongQuanLy.setTinhTrang(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            phongQuanLy.setTinhTrang(0);
                        }
                    });

                    spn_cauHoi = (Spinner)findViewById(R.id.qlp_cauHoi);
                    ArrayAdapter adapter2 = new ArrayAdapter<String>(QuanLyPhong.this, android.R.layout.simple_spinner_item, phongQuanLy.listCauHoi);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_cauHoi.setAdapter(adapter2);
                    spn_cauHoi.setSelection(phongQuanLy.getCauHoiBaoMat());
                    spn_cauHoi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            phongQuanLy.setCauHoiBaoMat(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            phongQuanLy.setCauHoiBaoMat(0);
                        }
                    });
                    rb_danhGia = (RatingBar)findViewById(R.id.qlp_danhGia);

                    rb_danhGia.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            phongQuanLy.setDanhGia(rating);
                        }
                    });
                    rb_danhGia.setRating(phongQuanLy.getDanhGia());
                    btn_roiPhong = (Button)findViewById(R.id.qlp_roiPhong);
                    btn_roiPhong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyPhong.this);
                            builder1.setIcon(android.R.drawable.ic_dialog_info);
                            builder1.setTitle("Nhắc");
                            builder1.setMessage("Bạn có chắc chắn muốn rời phòng?");
                            builder1.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new RoiPhongAsyncTask().execute(taiKhoanChiTiet.getIdN(), phongQuanLy.getIdP());
                                }
                            });
                            builder1.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder1.show();
                        }
                    });
                    btn_xoaPhong = (Button)findViewById(R.id.qlp_xoa);
                    btn_xoaPhong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyPhong.this);
                            builder1.setIcon(android.R.drawable.ic_dialog_info);
                            builder1.setTitle("Nhắc");
                            builder1.setMessage("Bạn có chắc chắn muốn xóa phòng?");
                            builder1.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new XoaPhongAsyncTask().execute(taiKhoanChiTiet.getIdN(), phongQuanLy.getIdP());
                                }
                            });
                            builder1.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder1.show();
                        }
                    });

                    disChangeAble();

                    btn_thayDoi = (Button)findViewById(R.id.qlp_thayDoi);
                    btn_thayDoi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (btn_thayDoi.getText().toString().equals("Thay đổi")){
                                btn_thayDoi.setText("Lưu");
                                changeAble();
                            } else {
                                btn_thayDoi.setText("Thay đổi");
                                phongQuanLy.setGiaCa(ed_giaPhong.getText().toString());
                                phongQuanLy.setThongTinBoSung(ed_boSung.getText().toString());
                                phongQuanLy.setTraLoiCauHoiBaoMat(ed_traLoi.getText().toString());

                                if (kiemTraPhongQuanLy(phongQuanLy)){
                                    new UpdatePhongDangOAsyncTask().execute(phongQuanLy);
                                }
                                disChangeAble();
                            }
                        }
                    });
                }
            }
        }
    }
    public boolean kiemTraPhongQuanLy(PhongQuanLy phongQuanLy) {
        boolean kiemTra = true;
        StringBuilder message = new StringBuilder("Chưa có ");
        if (phongQuanLy.getGiaCa().equals("")) {
            message.append("\"Giá phòng\"");
            kiemTra = false;
        }else if (phongQuanLy.getTraLoiCauHoiBaoMat().equals("")) {
            message.append("\"Trả lời bảo mật\"");
            kiemTra = false;
        }
        if (!kiemTra) {
            builder.setTitle("Nhắc");
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage(message.toString());
            builder.show();
        }
        return kiemTra;
    }
    class UpdatePhongDangOAsyncTask extends AsyncTask<PhongQuanLy, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(QuanLyPhong.this);
            dialog.setMessage("Xin chờ");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(PhongQuanLy... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.updatePhongDangO(params[0]));
                Node node = document.getElementsByTagName("result").item(0);
                result = node.getTextContent();
                return result;
            } catch (Exception e){
                return "Exception";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (s.equals("Exception")){
                builder.setTitle("Lỗi");
                builder.setMessage("Không thể kết nối đến Server");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();
            } if (s.equals("true")){
                builder.setTitle("Thông báo");
                builder.setMessage("Thay đổi thành công");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            } else {
                builder.setTitle("Thông báo");
                builder.setMessage(s);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            }
        }
    }
    class RoiPhongAsyncTask extends AsyncTask<String, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(QuanLyPhong.this);
            dialog.setMessage("Đang cập nhật thay đổi");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.roiPhong(params[0], params[1]));
                Node node = document.getElementsByTagName("result").item(0);
                result = node.getTextContent();
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
                builder.setMessage("Không thể kết nối đến Server");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();
            } if (s.equals("true")){
                builder.setTitle("Thông báo");
                builder.setMessage("Rời phòng thành công");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            } else {
                builder.setTitle("Thông báo");
                builder.setMessage(s);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            }
        }
    }
    class XoaPhongAsyncTask extends AsyncTask<String, Void, String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(QuanLyPhong.this);
            dialog.setMessage("Đang cập nhật thay đổi");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String result;
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = documentBuilder.parse(Variable.xoaPhong(params[0], params[1]));
                Node node = document.getElementsByTagName("result").item(0);
                result = node.getTextContent();
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
                builder.setMessage("Không thể kết nối đến Server");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.show();
            }
            if (s.equals("true")){
                builder.setTitle("Thông báo");
                builder.setMessage("Xóa phòng thành công");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            } else {
                builder.setTitle("Thông báo");
                builder.setMessage(s);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.show();
            }
        }
    }
}
