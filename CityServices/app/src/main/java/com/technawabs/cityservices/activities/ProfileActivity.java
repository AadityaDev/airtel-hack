package com.technawabs.cityservices.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;
import com.technawabs.cityservices.preferences.UserStore;

public class ProfileActivity extends BaseAppCompat {

    private UserStore us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String n = us.getUserName(getApplicationContext());
        String m = us.getUserMobile(getApplicationContext());
        TextView tmobile = (TextView)findViewById(R.id.mobileNumber);
        TextView tname = (TextView)findViewById(R.id.name);
        tname.setText(n);
        tmobile.setText(m);
    }
}
