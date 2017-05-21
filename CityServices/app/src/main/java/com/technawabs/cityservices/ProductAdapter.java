package com.technawabs.cityservices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technawabs.cityservices.models.Product;
import com.technawabs.cityservices.utils.StringUtils;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHelper>{

    private Context context;
    private List<Product> productList;

    public ProductAdapter(@NonNull Context context,@NonNull List<Product> productList){
        this.context=context;
        this.productList=productList;
    }

    @Override
    public ProductViewHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ProductViewHelper(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHelper holder, int position) {
        Product product=productList.get(position);
        if(product!=null){
            if(StringUtils.isNotEmptyOrNull(product.getTitle())){
                holder.title.setText(product.getTitle());
            }
            if(StringUtils.isNotEmptyOrNull(product.getBlurb())){
                holder.blurb.setText(product.getBlurb());
            }
            if(StringUtils.isNotEmptyOrNull(product.getBy())){
                holder.by.setText(product.getBy());
            }
            holder.funded.setText("Funds Received: "+product.getPercentageFunded());

        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHelper extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView blurb;
        private TextView by;
        private TextView funded;
        private RelativeLayout detail;

        public ProductViewHelper(@NonNull View item){
            super(item);
            title=(TextView)item.findViewById(R.id.title);
            blurb=(TextView)item.findViewById(R.id.blurb);
            by=(TextView)item.findViewById(R.id.by);
            funded=(TextView)item.findViewById(R.id.funded);
            detail=(RelativeLayout)item.findViewById(R.id.product_detail);
        }

    }

}
