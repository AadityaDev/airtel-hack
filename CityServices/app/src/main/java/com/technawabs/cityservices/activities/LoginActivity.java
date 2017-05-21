package com.technawabs.cityservices.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;

import com.technawabs.cityservices.preferences.UserStore;
import com.technawabs.cityservices.utils.StringUtils;

import org.w3c.dom.Text;

public class LoginActivity extends BaseAppCompat {

    private UserStore userStore;
    private AutoCompleteTextView mobile;
    private AutoCompleteTextView password;
    private AutoCompleteTextView name;
    private CardView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userStore=new UserStore(LoginActivity.this);
        name = (AutoCompleteTextView)findViewById(R.id.name);
        mobile=(AutoCompleteTextView)findViewById(R.id.mobile_number);
        password=(AutoCompleteTextView)findViewById(R.id.password);

        if(StringUtils.isNotEmptyOrNull(UserStore.getUserName(LoginActivity.this))){
            Intent intent=new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }

        TextView sign = (TextView)findViewById(R.id.textView3);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

        submit=(CardView)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile==null||mobile.length()<9){
                    Toast.makeText(getApplicationContext(),"Mobile number is missing or incomplete",Toast.LENGTH_LONG).show();
                }else if(password==null||password.length()<5){
                    Toast.makeText(getApplicationContext(),"password is missing or incomplete",Toast.LENGTH_LONG).show();
                }else{
                    userStore.setUserMobile(getApplicationContext(),mobile.getText().toString());
                    userStore.setUserToken(getApplicationContext(),"1234");
                    userStore.setUserName(getApplicationContext(),name.getText().toString());
                    userStore.setUserBalance(getApplicationContext(),"120");
                    Intent intent=new Intent(getApplicationContext(),UserActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
