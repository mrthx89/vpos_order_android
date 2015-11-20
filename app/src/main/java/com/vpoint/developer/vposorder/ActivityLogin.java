package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.vpoint.developer.helper.AppConfig;
import com.vpoint.developer.helper.ServiceHandler;
import com.vpoint.developer.model.MUser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogin extends Activity implements View.OnClickListener {
    Button btnLogin;
    Button btnCancel;
    EditText txtUserName;
    EditText txtPassword;

    private AppConfig publicSettingan = new AppConfig();
    private MUser ObjUser = new MUser();
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        btnCancel = (Button) findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsValidasi()) {
                    Toast.makeText(ActivityLogin.this,
                            "Selamat datang "+ txtUserName.getText().toString() +"!",
                            Toast.LENGTH_SHORT).show();
                    AppConfig.UserLogin = ObjUser;
                    Intent data = new Intent();
                    data.setData(Uri.parse(String.valueOf(ObjUser.getCOLUMN_ID())));
                    setResult(RESULT_OK, data);
                    finale();
                }
            }
        });

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMenuAbout();
            }
        });
        findViewById(R.id.imageViewInformasi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMenuAbout();
            }
        });
    }

    private void onClickMenuAbout() {
        Intent iUserMenu = new Intent(this, ActivityAbout.class);
        startActivityForResult(iUserMenu, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel :
                setResult(RESULT_CANCELED);
                finale();
        }
    }

    public Boolean IsValidasi() {
        Boolean hasil = true;
        if (hasil) {
            if (txtUserName.getText().length() == 0) {
                Toast.makeText(this, "User Name harus diisi.",
                        Toast.LENGTH_SHORT).show();
                txtUserName.setFocusable(true);
                hasil = false;
            }
        }
        if (hasil) {
            if (txtPassword.getText().length() == 0) {
                Toast.makeText(this, "Password harus diisi.",
                        Toast.LENGTH_SHORT).show();
                txtPassword.setFocusable(true);
                hasil = false;
            }
        }
        if (hasil) {
            new GetEventDetail().execute();
            hasil = false;
        }
        return hasil;
    }

    //method yang dipanggil ketika edit data selesai
    public void finale() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Background Async Task to Get complete event details
     * */
    class GetEventDetail extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActivityLogin.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting event details in background thread
         * */
        protected String doInBackground(String... params) {
            String url_details = publicSettingan.getStringUrl() + "GetUserLogin";
            try {
                // Building Parameters
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("Kode", txtUserName.getText().toString()));
                params1.add(new BasicNameValuePair("Pwd", txtPassword.getText().toString()));

                // getting event details by making HTTP request
                // Note that event details url will use GET request
                JSONObject json = AppConfig.JSONParser.makeHttpRequest(url_details, "GET", params1);

                // check your log for json response
                Log.d("Single event Details", json.toString());

                if (json.length() > 0) {
                        ObjUser.setCOLUMN_ID(json.getInt("NoID"));
                        ObjUser.setCOLUMN_KODE(json.getString("Kode"));
                        ObjUser.setCOLUMN_PWD(json.getString("Pwd"));
                        ObjUser.setCOLUMN_NAMA(json.getString("Nama"));
                        ObjUser.setCOLUMN_STATUS(true);
                } else {
                    // data not found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String filename) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            if (ObjUser.getCOLUMN_ID()>=1) {
                Toast.makeText(ActivityLogin.this,
                        "Selamat datang "+ txtUserName.getText().toString() +"!",
                        Toast.LENGTH_SHORT).show();
                AppConfig.UserLogin = ObjUser;
                Intent data = new Intent();
                data.setData(Uri.parse(String.valueOf(ObjUser.getCOLUMN_ID())));
                setResult(RESULT_OK, data);
                finale();
            } else {
                Toast.makeText(ActivityLogin.this,
                        "User name atau password yang anda masukkan salah.",
                        Toast.LENGTH_SHORT).show();
                txtUserName.setFocusable(true);
            }
        }
    }
}
