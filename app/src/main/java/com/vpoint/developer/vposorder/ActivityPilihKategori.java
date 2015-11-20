package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.vpoint.developer.helper.AppConfig;
import com.vpoint.developer.helper.ServiceHandler;
import com.vpoint.developer.model.MKategori;
import com.vpoint.developer.repository.KategoriAdapter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanto on 8/22/2015.
 */
public class ActivityPilihKategori extends Activity {
    ListView lvDaftar;

    ServiceHandler jParser;
    // Progress Dialog
    private ProgressDialog pDialog;
    List<MKategori> listDaftar;
    JSONArray MyArray;
    AppConfig PublicSettingan = new AppConfig();

    int IDSO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menubarang);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        lvDaftar = (ListView) findViewById(R.id.lvDaftar);
        listDaftar = new ArrayList<MKategori>();
        listDaftar.clear();
        jParser = new ServiceHandler();

        Bundle bun = this.getIntent().getExtras();
        if (bun != null) {
            IDSO = bun.getInt("NoID");
        } else {
            // Berarti User Cuma Cari aja.
            // Sementara biarkan dulu
            IDSO = -1;
        }

        SearchView txtCari = (SearchView) findViewById(R.id.txtCari);
        txtCari.setVisibility(View.GONE);

        ImageView cmdBack = (ImageView) findViewById(R.id.buttonBack);
        cmdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finale();
            }
        });

        lvDaftar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iPilihBarang = new Intent(ActivityPilihKategori.this,
                        ActivityPilihBarang.class);
                Bundle bun = new Bundle();
                iPilihBarang.setAction(Intent.ACTION_PICK);
                bun.putInt("NoID", IDSO);
                iPilihBarang.putExtras(bun);

                startActivityForResult(iPilihBarang, 0);
                return false;
            }
        });
    }

    //method yang dipanggil ketika edit data selesai
    public void finale() {
        this.finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // loading the comments via AsyncTask
        listDaftar.clear();
        new LoadDaftar().execute();
    }

    private void updateList() {
        try {
            lvDaftar.setAdapter(new KategoriAdapter(this, listDaftar));
            // to do nothing...
        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateJSONdata() {
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("Kategori", "123"));
        String json = jParser.makeServiceCall(PublicSettingan.getStringUrl() + "GetKategori"
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
                listDaftar.add(new MKategori(Table.getInt("NoID"),
                        Table.getString("Kode"),
                        Table.getString("Nama"))); // end of add
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class LoadDaftar extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActivityPilihKategori.this);
            pDialog.setMessage("Loading data Kategori, Please Wait ...");
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
                setResult(RESULT_OK);
                finale();
            } else if (resultCode == RESULT_CANCELED) {
                // loading the comments via AsyncTask
                listDaftar.clear();
                new LoadDaftar().execute();
            }
        }
    }
}
