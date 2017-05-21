package com.technawabs.cityservices.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;
import com.technawabs.cityservices.fragments.ProductDetailFragment;
import com.technawabs.cityservices.fragments.ProductListFragment;
import com.technawabs.cityservices.fragments.SearchFragment;
import com.technawabs.cityservices.models.Product;
import com.technawabs.cityservices.uicomponents.PagerTabWidget;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseAppCompat implements ProductListFragment.OnFragmentInteractionListener,
        ProductDetailFragment.OnFragmentInteractionListener,SearchFragment.OnFragmentInteractionListener{

    private PagerTabWidget pagerTabWidget;
    private ViewPager viewPager;
    private PagerTabAdapter pagerTabAdapter;
    private EditText searchByTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initTabWidget();
    }

    private void initTabWidget() {
        pagerTabWidget = (PagerTabWidget) findViewById(R.id.tabWidget);
        pagerTabWidget.setDividerInvisible();
        pagerTabWidget.addTab(LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_bar_contact, null));
        pagerTabWidget.addTab(LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_bar_engage, null));
        pagerTabWidget.addTab(LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_bar_job, null));

        viewPager = (ViewPager) findViewById(R.id.tabViewPager);
        pagerTabAdapter = new PagerTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerTabAdapter);

        pagerTabWidget.setmViewPager(viewPager);
        pagerTabWidget.setmOnTabSelectedListener(new PagerTabWidget.OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                Toast.makeText(getApplicationContext(), "Here: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
class PagerTabAdapter extends FragmentPagerAdapter {

    public PagerTabAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==1){
            return SearchFragment.newInstance(position);
        }else {
        return ProductListFragment.newInstance(position);}
    }

    @Override
    public int getCount() {
        return 3;
    }

}
