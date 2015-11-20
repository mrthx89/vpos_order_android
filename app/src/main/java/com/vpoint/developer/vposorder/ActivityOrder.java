package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vpoint.developer.helper.AppConfig;
import com.vpoint.developer.helper.ServiceHandler;
import com.vpoint.developer.model.MSO;
import com.vpoint.developer.model.MTable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanto on 8/22/2015.
 */
public class ActivityOrder extends Activity {
    ListView lvOrder;
    Button btnOrder;
    Button btnMenu;
    Button btnCancel;

    TextView txtTanggal;
    TextView txtPengunjung;
    TextView txtPelayan;
    TextView txtMeja;

    private AppConfig publicSettingan = new AppConfig();
    private ServiceHandler jParser = new ServiceHandler();

    MSO ObjSO = new MSO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        txtTanggal = (TextView) findViewById(R.id.txtTanggal);
        txtMeja = (TextView) findViewById(R.id.txtMeja);
        txtPelayan = (TextView) findViewById(R.id.txtSalesman);
        txtPengunjung = (TextView) findViewById(R.id.txtPengunjung);
        lvOrder = (ListView) findViewById(R.id.lvDaftar);
        btnMenu = (Button) findViewById(R.id.button_baru);
        btnOrder = (Button) findViewById(R.id.button_order);
        btnCancel = (Button) findViewById(R.id.button_cancel);

        final Activity activity = this;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Bundle bun = this.getIntent().getExtras();
        if (bun != null) {
            String url_details = publicSettingan.getStringUrl() + "GetOrderByNoID";
            try {
                // Building Parameters
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("IDOrder", String.valueOf(bun.getInt("NoID"))));

                // getting event details by making HTTP request
                // Note that event details url will use GET request
                JSONObject json = AppConfig.JSONParser.makeHttpRequest(url_details, "GET", params1);

                // check your log for json response
                Log.d("Sales Order", json.toString());

                if (json.length() > 0) {
                    ObjSO.setCOLUMN_ID(json.getInt("NoID"));
                    ObjSO.setCOLUMN_KODE(json.getString("Kode"));
                    ObjSO.setCOLUMN_TANGGAL(java.sql.Date.valueOf(json.getString("Tanggal").substring(0, 10)));
                    ObjSO.setCOLUMN_CATATAN(json.getString("Catatan"));
                    ObjSO.setCOLUMN_JMLPENGUNJUNG(json.getInt("JmlPengunjung"));
                    ObjSO.setCOLUMN_IDSALESMAN(AppConfig.UserLogin.getCOLUMN_ID());
                    ObjSO.setCOLUMN_TABLE(new
                            MTable(json.getInt("Table"), json.getString("Table"),
                            json.getString("Table"), json.getString("Table"), true));

                    txtMeja.setText("Meja : " + ObjSO.getCOLUMN_TABLE().getCOLUMN_KODE());
                    txtPengunjung.setText("Pengunjung : " + String.valueOf(ObjSO.getCOLUMN_JMLPENGUNJUNG()));
                    txtPelayan.setText("Pelayan : " + AppConfig.UserLogin.getCOLUMN_KODE());
                    txtTanggal.setText("Tanggal : " + new SimpleDateFormat("dd-MM-yyyy").format(ObjSO.getCOLUMN_TANGGAL()));
                } else {
                    // data not found
                    txtMeja.setText("Meja : (none)");
                    txtPengunjung.setText("Pengunjung : (none)");
                    txtPelayan.setText("Pelayan : (none)");
                    txtTanggal.setText("Tanggal : (none)");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Berarti User Cuma Cari aja.
            // Sementara biarkan dulu
            txtMeja.setText("Meja : (none)");
            txtPengunjung.setText("Pengunjung : (none)");
            txtPelayan.setText("Pelayan : (none)");
            txtTanggal.setText("Tanggal : (none)");
        }

        Button btnBack = (Button) findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finale();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(ActivityOrder.this);
                dlgAlert.setMessage("Apakah transaksi order ini akan dibatalkan?");
                dlgAlert.setTitle(String.valueOf(R.string.app_name));
                dlgAlert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url_details = publicSettingan.getStringUrl() + "GetOrderCancel";
                                try {
                                    // Building Parameters
                                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                                    params1.add(new BasicNameValuePair("IDOrder", String.valueOf(ObjSO.getCOLUMN_ID())));

                                    // getting event details by making HTTP request
                                    // Note that event details url will use GET request
                                    JSONObject json = AppConfig.JSONParser.makeHttpRequest(url_details, "GET", params1);

                                    // check your log for json response
                                    Log.d("Sales Order", json.toString());

                                    if (json.length() > 0) {
                                        if (json.getBoolean("Status") == true) {
                                            setResult(RESULT_CANCELED);
                                            finale();
                                        }
                                    } else {
                                        // data not found
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                );
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().

                    show();
                }
            });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //method yang dipanggil ketika edit data selesai
    public void finale() {
        this.finish();
    }
}
