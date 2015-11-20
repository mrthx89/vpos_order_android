package com.vpoint.developer.model;

import java.util.Date;

/**
 * Created by Yanto on 8/22/2015.
 */
public class MKategori {
    private int COLUMN_ID;
    private String COLUMN_KODE;
    private String COLUMN_NAMA;

    public MKategori(int ID, String Kode, String Nama) {
        COLUMN_ID = ID;
        COLUMN_KODE = Kode;
        COLUMN_NAMA = Nama;
    }

    public int getCOLUMN_ID() {
        return COLUMN_ID;
    }
    public void setCOLUMN_ID(int id) {
        this.COLUMN_ID = id;
    }

    public String getCOLUMN_NAMA() {
        return COLUMN_NAMA;
    }
    public void setCOLUMN_NAMA(String nama) {
        this.COLUMN_NAMA = nama;
    }

    public String getCOLUMN_KODE() {
        return COLUMN_KODE;
    }
    public void setCOLUMN_KODE(String kode) {
        this.COLUMN_KODE = kode;
    }

    @Override
    public String toString() {
        String status;
        //DecimalFormat df = new DecimalFormat("#,###.##");
        status = "NoID   : "+ String.valueOf(COLUMN_ID) +"\n"+
                 "Kode   : "+ COLUMN_KODE +"\n"+
                 "Nama   : "+ COLUMN_NAMA;
        return status;
    }
}
