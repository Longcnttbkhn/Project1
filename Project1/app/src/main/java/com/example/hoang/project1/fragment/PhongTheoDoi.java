package com.example.hoang.project1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoang.project1.R;
import com.example.hoang.project1.activity.ChiTietPhongTheoDoi;
import com.example.hoang.project1.activity.MainActivity;
import com.example.hoang.project1.adapter.PhongAdapter;
import com.example.hoang.project1.model.ItemPhong;
import com.example.hoang.project1.model.Phong;
import com.example.hoang.project1.sqldatabase.ItemPhongHelper;
import com.example.hoang.project1.util.Variable;

import java.util.ArrayList;

/**
 * Created by hoang on 12/5/2015.
 */
public class PhongTheoDoi extends Content {
    private ArrayList<ItemPhong> itemPhongArrayList;
    private ListView listView;
    private AlertDialog.Builder builder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        ItemPhongHelper helper = new ItemPhongHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from itemphong";
        Cursor cursor = db.rawQuery(sql, null);
        itemPhongArrayList = new ArrayList<>();
        cursor.moveToFirst();
        int count = cursor.getCount();
        for (int i = 0; i < count; i++){
            ItemPhong itemPhong = new ItemPhong();
            itemPhong.setIdP(cursor.getString(0));
            itemPhong.setKieuPhong(cursor.getString(1));
            itemPhong.setKhuVuc(cursor.getString(2));
            itemPhong.setDienTich(cursor.getString(3));
            itemPhong.setGiaCa(cursor.getString(4));
            itemPhongArrayList.add(itemPhong);
            cursor.moveToNext();
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new PhongAdapter(getActivity(), R.layout.item_content, itemPhongArrayList));
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietPhongTheoDoi.class);
                MainActivity main = (MainActivity) getActivity();
                intent.putExtra(Variable.KEY_ITEMPHONG, itemPhongArrayList.get(position));
                intent.putExtra(Variable.KEY_TAIKHOAN, main.getTaiKhoanChiTiet());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            PhongAdapter phongAdapter = (PhongAdapter) listView.getAdapter();
            if (phongAdapter == null){
                builder.setTitle("Thông báo");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("Không có dữ liệu");
                builder.show();
            } else {
                ItemPhongHelper helper = new ItemPhongHelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("delete from itemphong;");
                helper.close();
                phongAdapter.clear();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mI = getActivity().getMenuInflater();
        mI.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        PhongAdapter phongAdapter = (PhongAdapter) listView.getAdapter();
        if (item.getItemId() == R.id.delete_item){
            ItemPhong itemPhong = phongAdapter.getItem(info.position);
            ItemPhongHelper helper = new ItemPhongHelper(getActivity());
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("delete from itemphong where idP = " + itemPhong.getIdP() + ";");
            phongAdapter.remove(itemPhong);
            helper.close();
        }
        return super.onContextItemSelected(item);

    }
}