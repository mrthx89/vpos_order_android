package com.vpoint.developer.repository;

/**
 * Created by Yanto on 8/19/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vpoint.developer.model.MKategori;
import com.vpoint.developer.vposorder.R;

import java.util.List;

public class KategoriAdapter extends BaseAdapter {
    private Context mContext;
    private List<MKategori> mlistinfo;

    public KategoriAdapter(Context c, List<MKategori> l) {
        mContext = c;
        mlistinfo = l;
    }

    public int getCount() {
        return mlistinfo.size();
    }

    public Object getItem(int pos) {
        return mlistinfo.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        /*
        MKategori event = mlistinfo.get(pos);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.lv_meja, null);
        }

        // set avatar
        ImageView ivAvatar = (ImageView) convertView
                .findViewById(R.id.imV);
        if (event.getCOLUMN_STATUS().toLowerCase()=="isi") {
            ivAvatar.setImageResource(R.drawable.windows_style_deeporange);
        } else {
            ivAvatar.setImageResource(R.drawable.windows_style_red);
        }

        // set noid
        TextView tvNoID = (TextView) convertView.findViewById(R.id.txtNoID);
        tvNoID.setText(event.getCOLUMN_ID());

        // set noid
        TextView tvNama = (TextView) convertView.findViewById(R.id.txtNamaMeja);
        tvNama.setText(event.getCOLUMN_NAMA());

        return convertView;
        */

        View grid = null;

        try {
            MKategori event = mlistinfo.get(pos);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.lv_kategori, null);
                TextView txtNoID = (TextView) grid.findViewById(R.id.txtNoID);
                TextView txtKode = (TextView) grid.findViewById(R.id.txtKode);
                TextView txtNama = (TextView) grid.findViewById(R.id.txtNama);

                txtNoID.setText(String.valueOf(event.getCOLUMN_ID()));
                txtKode.setText(String.valueOf(event.getCOLUMN_KODE()));
                txtNama.setText(event.getCOLUMN_NAMA());
            } else {
                grid = (View) convertView;
            }
        } catch (Exception e) {
            Toast.makeText(null, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        return grid;
    }
}

