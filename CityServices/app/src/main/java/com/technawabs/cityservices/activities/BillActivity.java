package com.technawabs.cityservices.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.technawabs.cityservices.BillServiceAdapter;
import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseAppCompat;
import com.technawabs.cityservices.models.BillService;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends BaseAppCompat {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private BillServiceAdapter billServiceAdapter;
    private List<BillService> billServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        recyclerView=(RecyclerView)findViewById(R.id.bill_services);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        billServices=new ArrayList<>();
        billServiceAdapter=new BillServiceAdapter(getApplicationContext(),billServices);
        readBillServices();
        recyclerView.setAdapter(billServiceAdapter);
    }

    public void readBillServices(){
        BillService mobile=new BillService();
        mobile.setName("Mobile");
        mobile.setImageId(R.mipmap.ic_money);
        billServices.add(mobile);
        BillService landline=new BillService();
        landline.setName("Landline");
        landline.setImageId(R.mipmap.ic_money);
        billServices.add(landline);
        BillService dataCard=new BillService();
        dataCard.setName("Data Card");
        dataCard.setImageId(R.mipmap.ic_money);
        billServices.add(dataCard);
        BillService electricity=new BillService();
        electricity.setName("Electricity");
        electricity.setImageId(R.mipmap.ic_money);
        billServices.add(electricity);
        BillService insurance=new BillService();
        insurance.setName("Insurance");
        insurance.setImageId(R.mipmap.ic_money);
        billServices.add(insurance);
        BillService gas=new BillService();
        gas.setName("Gas");
        gas.setImageId(R.mipmap.ic_money);
        billServices.add(gas);
        BillService payShop=new BillService();
        payShop.setName("Pay Shop");
        payShop.setImageId(R.mipmap.ic_money);
        billServices.add(payShop);
        billServiceAdapter.notifyDataSetChanged();
    }

}
