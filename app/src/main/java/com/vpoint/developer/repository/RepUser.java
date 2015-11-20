package com.vpoint.developer.repository;

import android.util.Log;
import com.vpoint.developer.model.*;
import com.vpoint.developer.helper.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanto on 8/18/2015.
 */
public class RepUser {
    /*
    private ServiceHandler jParser;
    JSONObject jObj;

    public RepUser()
    {
        jParser = new ServiceHandler();
    }

    public MUser getUserByID(String NoID) {
        MUser Obj = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("NoID", NoID.toString()));
        JSONObject json = jParser.makeHttpRequest(AppConfig.IP_SERVER + "GetUserByNoID", "GET", params);
        Log.d("JSON", json.toString());
        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        try {
            // I know I said we would check if "Posts were Avail." (success==1)
            // before we tried to read the individual posts, but I lied...
            // mComments will tell us how many "posts" or comments are
            // available
            if (json.getString("Kode").toLowerCase()!="null") {
                Obj = new MUser();
                Obj.setCOLUMN_ID(json.getInt("NoID"));
                Obj.setCOLUMN_KODE(json.getString("Kode"));
                Obj.setCOLUMN_NAMA(json.getString("Nama"));
                Obj.setCOLUMN_PWD(json.getString("Pwd"));
            } else {
                Obj = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Obj;
    }

    public MUser getUserLogin(String Kode, String Pwd) {
        MUser Obj = null;
        String url = AppConfig.IP_SERVER + "GetUserLogin?Kode=" + Kode.toString() + "&Pwd=" + Pwd.toString();
        String jsonStr = jParser.makeServiceCall(url, ServiceHandler.GET);
        Log.d("Response: ", "> " + jsonStr);
        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        if (jsonStr != null) {
            try {
                // I know I said we would check if "Posts were Avail." (success==1)
                // before we tried to read the individual posts, but I lied...
                // mComments will tell us how many "posts" or comments are
                // available
                JSONArray mArray = new JSONArray(jsonStr);
                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mJsonObject = mArray.getJSONObject(i);
                    Obj = new MUser();
                    Obj.setCOLUMN_ID(mJsonObject.getInt("NoID"));
                    Obj.setCOLUMN_KODE(mJsonObject.getString("Kode"));
                    Obj.setCOLUMN_NAMA(mJsonObject.getString("Nama"));
                    Obj.setCOLUMN_PWD(mJsonObject.getString("Pwd"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Obj;
    }
    */
}
