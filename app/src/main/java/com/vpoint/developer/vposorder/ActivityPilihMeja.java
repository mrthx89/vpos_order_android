package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.vpoint.developer.helper.AppConfig;
import com.vpoint.developer.helper.ServiceHandler;
import com.vpoint.developer.model.MSO;
import com.vpoint.developer.model.MTable;
import com.vpoint.developer.repository.MejaAdapter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanto on 8/19/2015.
 */
public class ActivityPilihMeja extends Activity {
    GridView gvDaftar;

    // Progress Dialog
    private ProgressDialog pDialog;
    List<MTable> listDaftar;
    JSONArray MyArray;
    AppConfig PublicSettingan = new AppConfig();
    private MSO ObjSO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pilihmeja);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        gvDaftar = (GridView) findViewById(R.id.gridView1);
        listDaftar = new ArrayList<MTable>();
        listDaftar.clear();

        ImageView cmdBack = (ImageView) findViewById(R.id.buttonBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finale();
            }
        });

        //Do Grid View Action
        gvDaftar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Disini Untuk Imput Pesanan
                ObjSO = null;
                final int pos = position;
                final String url_details = PublicSettingan.getStringUrl() + "GetTableOrder";

                //tampilkan alert dialog
                final Dialog dialog = new Dialog(ActivityPilihMeja.this);
                dialog.setContentView(R.layout.activity_jmlpengunjung);
                dialog.setTitle("Jumlah Pengunjung");
                dialog.show();

                final NumberPicker txtNumber = (NumberPicker) dialog.findViewById(R.id.txtNumber);
                Button OkButton = (Button) dialog.findViewById(R.id.button_ok);
                OkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            // Building Parameters
                            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                            params1.add(new BasicNameValuePair("IDTabel", String.valueOf(listDaftar.get(pos).getCOLUMN_ID())));
                            params1.add(new BasicNameValuePair("IDSalesman", String.valueOf(AppConfig.UserLogin.getCOLUMN_ID())));
                            params1.add(new BasicNameValuePair("JmlPengunjung", String.valueOf(txtNumber.getValue())));

                            // getting event details by making HTTP request
                            // Note that event details url will use GET request
                            String json = AppConfig.JSONParser.makeServiceCall(url_details, ServiceHandler.GET, params1);

                            // check your log for json response
                            Log.d("Order Meja", json.toString());

                            JSONObject ObjJSON = new JSONObject(json);

                            if (ObjJSON.length() > 0) {
                                ObjSO = new MSO();
                                ObjSO.setCOLUMN_ID(ObjJSON.getInt("NoID"));
                                ObjSO.setCOLUMN_KODE(ObjJSON.getString("Kode"));
                                ObjSO.setCOLUMN_TANGGAL(java.sql.Date.valueOf(ObjJSON.getString("Tanggal").substring(0,10)));
                                ObjSO.setCOLUMN_TABLE(new MTable(ObjJSON.getJSONObject("Table").getInt("NoID"),
                                        ObjJSON.getJSONObject("Table").getString("Kode"),
                                        ObjJSON.getJSONObject("Table").getString("Nama"),
                                        ObjJSON.getJSONObject("Table").getString("Status"),
                                        ObjJSON.getJSONObject("Table").getBoolean("Aktif")));
                                ObjSO.setCOLUMN_IDSALESMAN(ObjJSON.getInt("IDSalesman"));
                                ObjSO.setCOLUMN_JMLPENGUNJUNG(ObjJSON.getInt("JmlPengunjung"));
                                ObjSO.setCOLUMN_CATATAN(ObjJSON.getString("Catatan"));

                                dialog.dismiss();
                                if (ObjSO != null) {
                                    Intent iPilihOrder = new Intent(ActivityPilihMeja.this,
                                            ActivityOrder.class);
                                    Bundle bun = new Bundle();
                                    iPilihOrder.setAction(Intent.ACTION_PICK);
                                    bun.putInt("NoID", ObjSO.getCOLUMN_ID());
                                    iPilihOrder.putExtras(bun);
                                    startActivityForResult(iPilihOrder, 0);
                                }
                            } else {
                                // data not found
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                    }
                });
                return false;
            }
        });

        // loading the comments via AsyncTask
        listDaftar.clear();
        new LoadDaftar().execute();
    }

    //method yang dipanggil ketika edit data selesai
    public void finale() {
        this.finish();
    }

    private void updateList() {
        try {
            gvDaftar.setAdapter(new MejaAdapter(this, listDaftar));
            // to do nothing...
        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateJSONdata() {
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("Meja", "123"));
        String json = AppConfig.JSONParser.makeServiceCall(PublicSettingan.getStringUrl() + "GetDaftarMeja"
                , ServiceHandler.GET, params1);
        Log.d("JSON", json.toString());
        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        try {

            // I know I said we would check if "Posts were Avail." (success==1)
            // before we tried to read the individual posts, but I lied...
            // mComments will tell us how many "posts" or comments are
            // available
            MyArray =  new JSONArray(json);

            // looping through all posts according to the json object returned
            for (int i = 0; i < MyArray.length(); i++) {
                JSONObject Table = MyArray.getJSONObject(i);
                listDaftar.add(new MTable(Table.getInt("NoID"),
                        Table.getString("Kode"),
                        Table.getString("Nama"),
                        Table.getString("Status"),
                        Table.getBoolean("Aktif"))); // end of add
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    public class LoadDaftar extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActivityPilihMeja.this);
            pDialog.setMessage("Loading data Meja, Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            pDialog.dismiss();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_CANCELED);
                finale();
            } else if (resultCode == RESULT_CANCELED) {
                // loading the comments via AsyncTask
                listDaftar.clear();
                new LoadDaftar().execute();
            }
        }
    }
}
