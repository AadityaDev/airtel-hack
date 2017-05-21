package com.technawabs.cityservices.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.technawabs.cityservices.ProductAdapter;
import com.technawabs.cityservices.R;
import com.technawabs.cityservices.base.BaseFragment;
import com.technawabs.cityservices.concurrency.ExecutorUtils;
import com.technawabs.cityservices.constants.AppConstant;
import com.technawabs.cityservices.models.Product;
import com.technawabs.cityservices.network.Factory;
import com.technawabs.cityservices.uicomponents.ItemClickSupport;
import com.technawabs.cityservices.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductListFragment extends BaseFragment {
    private Boolean isFabOpen = false;
    private int position;
    private OnFragmentInteractionListener mListener;
    private RecyclerView products;
    private LinearLayoutManager linearLayoutManager;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private TextView searchByTitle;
    private FloatingActionButton fab,fab1,fab2,fab3,fab4;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static ProductListFragment newInstance(int position) {
        ProductListFragment fragment = new ProductListFragment();
        fragment.setPosition(position);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_product_list, container, false);
        searchByTitle=(TextView)view.findViewById(R.id.search_box);
        searchByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchScreen();
            }
        });
        products=(RecyclerView) view.findViewById(R.id.product_list);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        products.setLayoutManager(linearLayoutManager);
        products.setHasFixedSize(true);
        productList=new ArrayList<>();
        productAdapter=new ProductAdapter(getContext(),productList);
        readProductList();
        products.setAdapter(productAdapter);
        ItemClickSupport.addTo(products).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showPlayerDetail(position);
            }
        });

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        fab1=(FloatingActionButton)view.findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(productList,Product.titleComparator);
                productAdapter.notifyDataSetChanged();
                animateFAB();
            }
        });
        fab2=(FloatingActionButton)view.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(productList,Product.locationComparator);
                productAdapter.notifyDataSetChanged();
                animateFAB();
            }
        });
        fab3=(FloatingActionButton)view.findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(productList,Product.backersComparator);
                productAdapter.notifyDataSetChanged();
                animateFAB();
            }
        });
        fab4=(FloatingActionButton)view.findViewById(R.id.fab4);
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(productList,Product.fundComparator);
                productAdapter.notifyDataSetChanged();
                animateFAB();
            }
        });
        fab=(FloatingActionButton)view.findViewById(R.id.filter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

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

    void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;
        }
    }

    private void readProductList() {
        ListenableFuture<JSONArray> player = Factory.getSongService().getProducts();
        Futures.addCallback(player, new FutureCallback<JSONArray>() {
            @Override
            public void onSuccess(@NonNull JSONArray result) {
                try {
                    Log.d(getTAG(), "OnSuccess: " + result.toString());
                    for (int i = 0; i < result.length(); i++) {
                        Gson gson = new Gson();
                        Product product = gson.fromJson(result.get(i).toString(), Product.class);
                        JSONObject serial=result.getJSONObject(i);
                        if(serial!=null&&!serial.isNull("s.no")){
                            product.setSerialnumber(serial.getInt("s.no"));
                        }
                        if(serial!=null&&!serial.isNull("amt.pledged")){
                            product.setAmountPledged(serial.getInt("amt.pledged"));
                        }
                        if(serial!=null&&!serial.isNull("end.time")){
                            product.setEndTime(serial.getString("end.time"));
                        }
                        if(serial!=null&&!serial.isNull("percentage.funded")){
                            product.setPercentageFunded(serial.getInt("percentage.funded"));
                        }
//                        if(serial!=null&&!serial.isNull("num.backers")){
//                            product.setNumberOfBackers(serial.getInt("num.backers"));
//                        }
//                        if(serial!=null&&!serial.isNull("num.backers")){
//                            product.setNumberOfBackers(serial.getInt("num.backers"));
//                        }
                        productList.add(product);
                        Log.d(getTAG(), "player: " + product.toString());
                    }
                    productAdapter.notifyDataSetChanged();
                } catch (JSONException jsonException) {
                    if ((jsonException != null) && (!StringUtils.isNotEmptyOrNull(jsonException.getMessage()))) {
                        Log.d(getTAG(), jsonException.getMessage());
                    } else {
                        Log.d(getTAG(), AppConstant.EXCEPTION.JSON_EXCEPTION);
                    }
                } catch (Exception exception) {
                    if ((exception != null) && (!StringUtils.isNotEmptyOrNull(exception.getMessage()))) {
                        Log.d(getTAG(), exception.getMessage());
                    } else {
                        Log.d(getTAG(), AppConstant.EXCEPTION.EXCEPTION);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(getTAG(),t.getMessage());
            }
        }, ExecutorUtils.getUIThread());
    }

    private void showPlayerDetail(int position) {
        Bundle bundle=new Bundle();
        bundle.putParcelable("Product",productList.get(position));
        ProductDetailFragment detailFragment = ProductDetailFragment.newInstance();
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag, detailFragment)
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.linear_interpolator,android.R.anim.linear_interpolator)
                .addToBackStack(getTAG())
                .commit();
    }

    private void openSearchScreen(){
        SearchFragment searchFragment=new SearchFragment();
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag, searchFragment)
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.linear_interpolator,android.R.anim.linear_interpolator)
                .addToBackStack(getTAG())
                .commit();
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
