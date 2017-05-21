package com.technawabs.cityservices.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class SearchFragment extends BaseFragment {

    private int position;
    private SearchFragment.OnFragmentInteractionListener mListener;
    private RecyclerView products;
    private LinearLayoutManager linearLayoutManager;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private EditText searchByTitle;

    public SearchFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static SearchFragment newInstance(int position) {
        SearchFragment fragment = new SearchFragment();
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
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        searchByTitle=(EditText)view.findViewById(R.id.search_box);
//        searchByTitle.setOnFocusChangeListener(this);
        products=(RecyclerView) view.findViewById(R.id.product_list);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        products.setLayoutManager(linearLayoutManager);
        products.setHasFixedSize(true);
        productList=new ArrayList<>();
        productAdapter=new ProductAdapter(getContext(),productList);
        readProductList();
        Collections.sort(productList,Product.fundComparator);
        products.setAdapter(productAdapter);
        ItemClickSupport.addTo(products).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showPlayerDetail(position);
            }
        });
        addTextListener();
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
                        if(serial!=null&&!serial.isNull("num.backers")){
                            product.setNumberOfBackers(serial.getInt("num.backers"));
                        }
                        if(serial!=null&&!serial.isNull("num.backers")){
                            product.setNumberOfBackers(serial.getInt("num.backers"));
                        }
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

    public void addTextListener() {

        searchByTitle.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<Product> filteredList = new ArrayList<>();
                for (int i = 0; i < productList.size(); i++) {
                    final String text = productList.get(i).getTitle().toLowerCase();
                    if (text.contains(query)) {
                        filteredList.add(productList.get(i));
                    }
                }
                products.setLayoutManager(new LinearLayoutManager(getContext()));
                if (filteredList.size() > 0) {
                    productAdapter = new ProductAdapter(getContext(), filteredList);
                    products.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();  // data set changed
                }
                searchByTitle.setImeActionLabel(query, KeyEvent.KEYCODE_ENTER);
                if (productList.size() <= 0) {
                    productList = new ArrayList<>();
                    productAdapter = new ProductAdapter(getContext(), productList);
                    readProductList();
                    products.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void showPlayerDetail(int position) {
        Bundle bundle=new Bundle();
        bundle.putParcelable("Product",productList.get(position));
        ProductDetailFragment passwordKeyboard = ProductDetailFragment.newInstance();
        passwordKeyboard.setArguments(bundle);
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag, passwordKeyboard)
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.linear_interpolator,android.R.anim.linear_interpolator)
                .addToBackStack(getTAG())
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
