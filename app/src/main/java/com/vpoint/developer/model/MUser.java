package com.vpoint.developer.model;

/**
 * Created by Yanto on 8/16/2015.
 */
public class MUser {
    private int COLUMN_ID;
    private String COLUMN_KODE;
    private String COLUMN_PWD;
    private String COLUMN_NAMA;
    private Boolean COLUMN_STATUS;

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

    public String getCOLUMN_PWD() {
        return COLUMN_PWD;
    }
    public void setCOLUMN_PWD(String pwd) {
        this.COLUMN_PWD = pwd;
    }

    public String getCOLUMN_NAMA() {
        return COLUMN_NAMA;
    }
    public void setCOLUMN_NAMA(String nama) {
        this.COLUMN_NAMA = nama;
    }

    public Boolean getCOLUMN_STATUS() {
        return COLUMN_STATUS;
    }
    public void setCOLUMN_STATUS(Boolean status) {
        this.COLUMN_STATUS = status;
    }

    @Override
    public String toString() {
        String status;
        //DecimalFormat df = new DecimalFormat("#,###.##");
        if (COLUMN_STATUS) {
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
