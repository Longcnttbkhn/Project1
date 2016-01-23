package com.example.hoang.project1.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.activity.ChiTietPhong;
import com.example.hoang.project1.adapter.PhongAdapter;
import com.example.hoang.project1.model.ItemPhong;
import com.example.hoang.project1.util.Variable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hoang on 12/4/2015.
 */
public class MainContent extends Content {
    ListView listView;
    String link;
    AlertDialog.Builder builder;
    ArrayList<ItemPhong> itemPhongArrayList;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        new LayPhongAsyncTask().execute();
        return view;
    }

    class LayPhongAsyncTask extends AsyncTask<Void, Void, Document> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Đang tải dữ liệu ...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Document doInBackground(Void... params) {
            try {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = db.parse(link);
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
                NodeList listItemPhong = document.getElementsByTagName("phong");
                if (listItemPhong == null){
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Không có dữ liệu");
                    builder.show();
                } else {
                    String[] listKhuVuc = getResources().getStringArray(R.array.khuVuc);
                    itemPhongArrayList = new ArrayList<>();
                    for (int i = 0; i < listItemPhong.getLength(); i++) {
                        Node phong = listItemPhong.item(i);
                        ItemPhong itemPhong = new ItemPhong();
                        NodeList thongTinPhong = phong.getChildNodes();
                        itemPhong.setIdP(thongTinPhong.item(0).getTextContent());
                        itemPhong.setKieuPhong(thongTinPhong.item(1).getTextContent());
                        itemPhong.setKhuVuc(listKhuVuc[Integer.valueOf(thongTinPhong.item(2).getTextContent())]);
                        itemPhong.setDienTich(thongTinPhong.item(3).getTextContent());
                        itemPhong.setGiaCa(thongTinPhong.item(4).getTextContent());
                        itemPhongArrayList.add(itemPhong);
                    }
                    listView.setAdapter(new PhongAdapter(getActivity(), R.layout.item_content, itemPhongArrayList));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), ChiTietPhong.class);
                            intent.putExtra(Variable.KEY_ITEMPHONG, itemPhongArrayList.get(position));
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    public void setLink(String link) {
        this.link = link;
    }
}
