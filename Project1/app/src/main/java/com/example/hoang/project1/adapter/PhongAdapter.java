package com.example.hoang.project1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.model.ItemPhong;
import com.example.hoang.project1.model.Phong;

import java.util.ArrayList;

/**
 * Created by hoang on 12/2/2015.
 */
public class PhongAdapter extends ArrayAdapter<ItemPhong> {
    private Context context;
    private int resource;
    private ArrayList<ItemPhong> itemPhongArrayList;

    public PhongAdapter(Context context, int resource, ArrayList<ItemPhong> itemPhongArrayList) {
        super(context, resource, itemPhongArrayList);
        this.context = context;
        this.resource = resource;
        this.itemPhongArrayList = itemPhongArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhongHolder phongHolder;
        if (convertView != null) {
            phongHolder = (PhongHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
            phongHolder = new PhongHolder(convertView);
            convertView.setTag(phongHolder);
        }
        phongHolder.popularItem(itemPhongArrayList.get(position));
        return convertView;

    }
}
class PhongHolder {
    ImageView imageView;
    TextView idP, dienTich, giaCa, khuVuc;

    public PhongHolder(View convertView){
        imageView = (ImageView) convertView.findViewById(R.id.itemPhong_image);
        idP = (TextView) convertView.findViewById(R.id.itemPhong_idP);
        dienTich = (TextView) convertView.findViewById(R.id.itemPhong_dienTich);
        giaCa = (TextView) convertView.findViewById(R.id.itemPhong_giaCa);
        khuVuc = (TextView) convertView.findViewById(R.id.itemPhong_khuVuc);
    }

    public void popularItem(ItemPhong itemPhong){
        imageView.setImageResource(itemPhong.getIcon());
        idP.setText(itemPhong.getIdP());
        dienTich.setText(itemPhong.getDienTich());
        giaCa.setText(itemPhong.getGiaCa());
        khuVuc.setText(itemPhong.getKhuVuc());
    }
}
