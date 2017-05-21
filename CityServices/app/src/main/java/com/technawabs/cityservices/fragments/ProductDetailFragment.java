package com.technawabs.cityservices.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseFragment;
import com.technawabs.cityservices.models.Product;
import com.technawabs.cityservices.utils.StringUtils;

public class ProductDetailFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private TextView title;
    private TextView blurb;
    private TextView by;
    private TextView funded;
    private TextView detail;
    private Bundle bundle;
    private Product product;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=getArguments();
        if(bundle.getParcelable("Product")!=null){
        product=bundle.getParcelable("Product");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_product_detail, container, false);
        title=(TextView)view.findViewById(R.id.title);
        blurb=(TextView)view.findViewById(R.id.blurb);
        by=(TextView)view.findViewById(R.id.by);
        funded=(TextView)view.findViewById(R.id.funded);
        detail=(TextView)view.findViewById(R.id.detail);
        if(bundle!=null){
            fillScreen(product);
        }
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void fillScreen(@NonNull Product product){
        if(StringUtils.isNotEmptyOrNull(product.getTitle()))
            title.setText(product.getTitle());
        if(StringUtils.isNotEmptyOrNull(product.getBlurb()))
            blurb.setText(product.getBlurb());
        if(StringUtils.isNotEmptyOrNull(product.getBy()))
            by.setText(product.getBy());
        funded.setText("Funds Raised: "+product.getPercentageFunded());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
