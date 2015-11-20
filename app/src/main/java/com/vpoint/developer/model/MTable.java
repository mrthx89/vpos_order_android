package com.vpoint.developer.model;

import android.graphics.Bitmap;

/**
 * Created by Yanto on 8/16/2015.
 */
public class MTable {
    private int COLUMN_ID;
    private String COLUMN_KODE;
    private String COLUMN_NAMA;
    private String COLUMN_STATUS;
    private Boolean COLUMN_AKTIF;

    public MTable(int ID, String Kode, String Nama, String Status, Boolean Aktif) {
        COLUMN_ID = ID;
        COLUMN_KODE = Kode;
        COLUMN_NAMA = Nama;
        COLUMN_STATUS = Status;
        COLUMN_AKTIF = Aktif;
    }

    public int getCOLUMN_ID() {
        return COLUMN_ID;
    }
    public void setCOLUMN_ID(int id) {
        this.COLUMN_ID = id;
    }

    public String getCOLUMN_KODE() {
        return COLUMN_KODE;
    }
    public void setCOLUMN_KODE(String kode) {
        this.COLUMN_KODE = kode;
    }

    public String getCOLUMN_STATUS() {
        return COLUMN_STATUS;
    }
    public void setCOLUMN_STATUS(String status) {
        this.COLUMN_STATUS = status;
    }

    public String getCOLUMN_NAMA() {
        return COLUMN_NAMA;
    }
    public void setCOLUMN_NAMA(String nama) {
        this.COLUMN_NAMA = nama;
    }

    public Boolean getCOLUMN_AKTIF() {
        return COLUMN_AKTIF;
    }
    public void setCOLUMN_AKTIF(Boolean aktif) {
        this.COLUMN_AKTIF = aktif;
    }

    @Override
    public String toString() {
        String status;
        //DecimalFormat df = new DecimalFormat("#,###.##");
        if (COLUMN_AKTIF) {
            status = "User ID : "+ COLUMN_KODE +"\n"+
                    "Nama    : "+ COLUMN_NAMA +"\n"+
                    "Status  : Aktif";
        } else {
            status = "User ID : "+ COLUMN_KODE +"\n"+
                    "Nama    : "+ COLUMN_NAMA +"\n"+
                    "Status  : Tidak Aktif";
        }
        return status;
    }
}
