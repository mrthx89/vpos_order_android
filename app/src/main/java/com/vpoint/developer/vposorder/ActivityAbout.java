package com.vpoint.developer.vposorder;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Yanto on 8/16/2015.
 */
public class ActivityAbout extends Activity {
    static final int PICK_SETTING_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        final Activity activity = this;

        Button btnBack = (Button) findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                activity.finish();
            }
        });

        Button btnSetting = (Button) findViewById(R.id.buttonSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(activity, ActivitySettingUrl.class),
                        PICK_SETTING_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // do your stuff here after SecondActivity finished.
        switch (requestCode) {
            case PICK_SETTING_REQUEST:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                } else {
                    this.finish();
                }
        }
    }
}
