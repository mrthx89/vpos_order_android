package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.vpoint.developer.helper.AppConfig;
import com.vpoint.developer.helper.ServiceHandler;

public class ActivityMain extends Activity implements View.OnClickListener {
    int kode_aksi_login = 1;
    int kode_aksi_produk = 2;
    int kode_aksi_order = 3;

    TextView LabelUser;
    int IDUserLogin;
    String KodeUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        LabelUser = (TextView) findViewById(R.id.textViewNamaUser);

        findViewById(R.id.buttonOrder).setOnClickListener(this);
        findViewById(R.id.buttonProduk).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);

        Bundle bun = this.getIntent().getExtras();
        if (bun != null) {
            IDUserLogin = bun.getInt("IDUser");
            KodeUserLogin = AppConfig.UserLogin.getCOLUMN_KODE();
            LabelUser.setText(AppConfig.UserLogin.getCOLUMN_NAMA().toUpperCase());
        } else {
            //Aksi menu login
            buttonloginclick();
        }

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

    private void buttonloginclick() {
        Intent iLogin = new Intent(this, ActivityLogin.class);
        startActivityForResult(iLogin, kode_aksi_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOrder :
                Intent iPilihMeja = new Intent(this, ActivityPilihMeja.class);
                startActivityForResult(iPilihMeja, kode_aksi_order);
                break;
            case R.id.buttonProduk :
                Intent iProduk = new Intent(this, ActivityPilihMeja.class);
                startActivityForResult(iProduk, kode_aksi_produk);
                break;
            case R.id.buttonExit :
                try {
                    finale();
                    break;
                } catch (Exception e) {
                    Toast.makeText(this, "Error : " + e.toString(),Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == kode_aksi_login) {
            if (resultCode == RESULT_OK) {
                IDUserLogin = Integer.parseInt(data.getData().toString());
                if (IDUserLogin>=1 && AppConfig.UserLogin != null) {
                    KodeUserLogin = AppConfig.UserLogin.getCOLUMN_KODE();
                    LabelUser.setText(AppConfig.UserLogin.getCOLUMN_NAMA().toUpperCase());
                } else {
                    finale();
                }
            } else {
                finale();
            }
        }
    }

    //method yang dipanggil ketika edit data selesai
    public void finale() {
        try {
            this.finish();
        } catch (Exception e) {
            Toast.makeText(this,"Error : " + e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
