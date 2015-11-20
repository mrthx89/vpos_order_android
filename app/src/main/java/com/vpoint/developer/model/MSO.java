package com.vpoint.developer.model;

import java.util.Date;

/**
 * Created by Yanto on 8/16/2015.
 */
public class MSO {
    private int COLUMN_ID;
    private String COLUMN_KODE;
    private Date COLUMN_TANGGAL;
    private MTable COLUMN_TABLE;
    private int COLUMN_IDSALESMAN;
    private int COLUMN_JMLPENGUNJUNG;
    private String COLUMN_CATATAN;

    public int getCOLUMN_ID() {
        return COLUMN_ID;
    }
    public void setCOLUMN_ID(int id) {
        this.COLUMN_ID = id;
    }

    public Date getCOLUMN_TANGGAL() {
        return COLUMN_TANGGAL;
    }
    public void setCOLUMN_TANGGAL(Date tanggal) {
        this.COLUMN_TANGGAL = tanggal;
    }

    public MTable getCOLUMN_TABLE() {
        return COLUMN_TABLE;
    }
    public void setCOLUMN_TABLE(MTable table) {
        this.COLUMN_TABLE = table;
    }

    public String getCOLUMN_KODE() {
        return COLUMN_KODE;
    }
    public void setCOLUMN_KODE(String kode) {
        this.COLUMN_KODE = kode;
    }

    public String getCOLUMN_CATATAN() {
        return COLUMN_CATATAN;
    }
    public void setCOLUMN_CATATAN(String catatan) {
        this.COLUMN_CATATAN = catatan;
    }

    public int getCOLUMN_IDSALESMAN() {
        return COLUMN_IDSALESMAN;
    }
    public void setCOLUMN_IDSALESMAN(int idsalesman) {
        this.COLUMN_IDSALESMAN = idsalesman;
    }

    public int getCOLUMN_JMLPENGUNJUNG() {
        return COLUMN_JMLPENGUNJUNG;
    }
    public void setCOLUMN_JMLPENGUNJUNG(int jmlpengunjung) {
        this.COLUMN_JMLPENGUNJUNG = jmlpengunjung;
    }

    @Override
    public String toString() {
        String status;
        //DecimalFormat df = new DecimalFormat("#,###.##");
        status = "NoID      : "+ String.valueOf(COLUMN_ID) +"\n"+
                 "Kode      : "+ COLUMN_KODE +"\n"+
                 "Tanggal   : "+ String.valueOf(COLUMN_TANGGAL.toString()) +"\n"+
                 "Table     : "+ String.valueOf(COLUMN_TABLE.getCOLUMN_KODE());
        return status;
    }
}
