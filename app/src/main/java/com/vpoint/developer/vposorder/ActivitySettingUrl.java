package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.vpoint.developer.helper.AppConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Yanto on 8/19/2015.
 */
public class ActivitySettingUrl extends Activity implements View.OnClickListener {
    private AppConfig publicSettingan = new AppConfig();
    private EditText txtSettingUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settingurl);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        findViewById(R.id.UrlSetCancel).setOnClickListener(this);
        findViewById(R.id.saveGo).setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           File myFile = new File(publicSettingan.STORETEXT);
                                           try {
                                               if (!myFile.exists()) {
                                                   myFile.createNewFile();
                                                   myFile.mkdirs();
                                               }
                                               try {
                                                   FileOutputStream fos;
                                                   fos = new FileOutputStream(myFile);
                                                   byte[] data = txtSettingUrl.getText().
                                                           toString().getBytes();
                                                   fos.write(data);
                                                   fos.flush();
                                                   fos.close();

                                                   //Bener di close
                                                   setResult(RESULT_OK);
                                                   ActivitySettingUrl.this.finish();
                                               } catch (IOException e) {
                                                   Toast.makeText(getBaseContext(),
                                                           "Error Read File : " + e.toString(),
                                                           Toast.LENGTH_LONG).show();
                                               }
                                           } catch (FileNotFoundException e) {
                                               Toast.makeText(getBaseContext(),
                                                       "Error Read File : " + e.toString(),
                                                       Toast.LENGTH_LONG).show();
                                           } catch (Throwable t) {
                                               Toast.makeText(getBaseContext(),
                                                       "Error Read File : " + t.toString(),
                                                       Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   }
                );
        txtSettingUrl= (EditText) findViewById(R.id.editText);
        txtSettingUrl.setText(publicSettingan.getStringUrl());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.UrlSetCancel :
                setResult(RESULT_CANCELED);
                this.finish();
        }
    }
}
