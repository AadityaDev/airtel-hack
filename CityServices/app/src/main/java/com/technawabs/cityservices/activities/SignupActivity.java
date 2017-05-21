package com.technawabs.cityservices.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;
import com.technawabs.cityservices.preferences.UserStore;

import org.w3c.dom.Text;

import java.net.URISyntaxException;

public class SignupActivity extends BaseAppCompat {

    private UserStore userStore;
    private AutoCompleteTextView nameTextView;
    private AutoCompleteTextView mobileTextView;
    private AutoCompleteTextView passwordTextView;
    private CardView aadharCard;
    private TextView aadhar;
    private CardView signUp;
    private boolean isCardAttached;
    private boolean areDetailsEntered=false;
    private static final int FILE_SELECT_CODE = 0;
    private final static String TAG="SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameTextView = (AutoCompleteTextView) findViewById(R.id.name);
        mobileTextView = (AutoCompleteTextView) findViewById(R.id.mobile_number);
        passwordTextView = (AutoCompleteTextView) findViewById(R.id.password);

        TextView login = (TextView)findViewById(R.id.textView4);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        aadharCard = (CardView) findViewById(R.id.aadhar_section);
        aadhar=(TextView)findViewById(R.id.aadhar);
        signUp = (CardView) findViewById(R.id.submit);

        userStore=new UserStore(getApplicationContext());
        aadharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((nameTextView.getText() == null) || (nameTextView.getText().toString().length() < 3)) {
                    areDetailsEntered=false;
                    Toast.makeText(getApplicationContext(), "Name is missing", Toast.LENGTH_SHORT).show();
                } else if ((mobileTextView.getText() == null) || (mobileTextView.getText().toString().length() < 9)) {
                    areDetailsEntered=false;
                    Toast.makeText(getApplicationContext(), "Mobile is missing", Toast.LENGTH_SHORT).show();
                } else if ((passwordTextView.getText() == null) || (mobileTextView.getText().toString().length() < 5)) {
                    areDetailsEntered=false;
                    Toast.makeText(getApplicationContext(), "Password is week or missing", Toast.LENGTH_SHORT).show();
                }
                else
//                    iAditya
                    {
                        userStore.setUserName(getApplicationContext(),nameTextView.getText().toString());
                        userStore.setUserMobile(getApplicationContext(),mobileTextView.getText().toString());
                        userStore.setUserToken(getApplicationContext(),"1234");
                    Toast.makeText(getApplicationContext(), "Details are submitted!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = getPath(this, uri);
                        aadhar.setText(path);
                        isCardAttached=true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the UserActivity to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
