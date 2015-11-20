package com.vpoint.developer.helper;

import android.widget.Toast;

import com.vpoint.developer.model.MSO;
import com.vpoint.developer.model.MUser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Yanto on 8/17/2015.
 */
public class AppConfig {
    //public static final String IP_SERVER = "http://192.168.43.138/vpos_service/api/vpos/";
    public static MUser UserLogin = null;
    public static ServiceHandler JSONParser = new ServiceHandler();
    public final static String STORETEXT = "/sdcard/settingurlvpos.txt";

    public String getStringUrl() {
        return this.readFileInEditor();
    }

    public void setStringUrl(String StringUrl) {
        Toast.makeText(null, "Belum disetting, hubungi developer.", Toast.LENGTH_LONG).show();
    }

    private String readFileInEditor() {
        String stringUrl;
        try {
            File MyFile = new File(STORETEXT);
            if (!MyFile.exists()) {
                MyFile.createNewFile();
                MyFile.mkdirs();
            }
            FileInputStream in = new FileInputStream(STORETEXT);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(in));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow + "\n";
            }
            myReader.close();
            stringUrl = aBuffer.toString();
        }
        catch (Throwable t) {
            Toast.makeText(null, "Error Read File : " + t.toString(), Toast.LENGTH_LONG).show();
            stringUrl = "http://localhost/api/vpos/";
        }
        return stringUrl.replace("\n","");
    }
}
