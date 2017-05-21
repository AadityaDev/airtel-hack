package com.technawabs.cityservices.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;
import com.technawabs.cityservices.preferences.UserStore;

public class UserActivity extends BaseAppCompat {

    private UserStore userStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        String name= userStore.getUserName(getApplicationContext());

        ImageView iv = (ImageView)findViewById(R.id.userpic);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ProfileActivity.class );
                startActivity(i);
            }
        });

        TextView bal = (TextView)findViewById(R.id.textView5);
        bal.setText(" Hi " + name);

        ImageView icon = new ImageView(this);
        icon.setImageResource(R.mipmap.ic_center);

        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(icon).build();
        actionButton.setPosition(FloatingActionButton.POSITION_CENTER, p);
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView item1 = new ImageView(this);
        item1.setImageResource(R.mipmap.ic_recharge);

        ImageView item2 = new ImageView(this);
        item2.setImageResource(R.mipmap.ic_share);

        ImageView item3 = new ImageView(this);
        item3.setImageResource(R.mipmap.ic_bill);

        ImageView item4 = new ImageView(this);
        item4.setImageResource(R.mipmap.ic_money);

        ImageView item5 = new ImageView(this);
        item5.setImageResource(R.mipmap.ic_usage);

        ImageView item6 = new ImageView(this);
        item6.setImageResource(R.mipmap.ic_game);

        ImageView item7 = new ImageView(this);
        item7.setImageResource(R.mipmap.ic_tv);

        ImageView item8 = new ImageView(this);
        item8.setImageResource(R.mipmap.ic_wynk);


        SubActionButton button1 = itemBuilder.setContentView(item1).build();
        SubActionButton button2 = itemBuilder.setContentView(item2).build();
        SubActionButton button3 = itemBuilder.setContentView(item3).build();
        SubActionButton button4 = itemBuilder.setContentView(item4).build();
        SubActionButton button5 = itemBuilder.setContentView(item5).build();
        SubActionButton button6 = itemBuilder.setContentView(item6).build();
        SubActionButton button7 = itemBuilder.setContentView(item7).build();
        SubActionButton button8 = itemBuilder.setContentView(item8).build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Recharge.class);
                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Share.class);
                startActivity(i);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),BillActivity.class);
                startActivity(i);

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(UserActivity.this,Money.class);
                startActivity(i);

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Usage.class);
                startActivity(i);

            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Games.class);
                startActivity(i);

            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Tv.class);
                startActivity(i);

            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),Wynk.class);
                startActivity(i);

            }
        });

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1).setStartAngle(0)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .addSubActionView(button5)
                .addSubActionView(button6)
                .addSubActionView(button7)
                .addSubActionView(button8)
                .setEndAngle(360)
                .attachTo(actionButton).build();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
