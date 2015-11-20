package com.vpoint.developer.repository;

/**
 * Created by Yanto on 8/19/2015.
 */
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vpoint.developer.model.MTable;
import com.vpoint.developer.vposorder.R;

public class MejaAdapter extends BaseAdapter {
    private Context mContext;
    private List<MTable> mlistinfo;

    public MejaAdapter(Context c, List<MTable> l) {
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
        MTable event = mlistinfo.get(pos);
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
            MTable event = mlistinfo.get(pos);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.lv_meja, null);
                TextView txtNoID = (TextView) grid.findViewById(R.id.txtNoID);
                TextView txtNama = (TextView) grid.findViewById(R.id.txtNamaMeja);

                txtNoID.setText(String.valueOf(event.getCOLUMN_ID()));
                txtNama.setText(event.getCOLUMN_NAMA() + "\n" + event.getCOLUMN_STATUS());
                if (event.getCOLUMN_STATUS().equalsIgnoreCase("isi")) {
                    txtNama.setBackgroundResource(R.drawable.windows_style_red);
                }else {
                    txtNama.setBackgroundResource(R.drawable.windows_style_deeporange);
                };
            } else {
                grid = (View) convertView;
            }
        } catch (Exception e) {
            Toast.makeText(null, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        return grid;
    }
}

