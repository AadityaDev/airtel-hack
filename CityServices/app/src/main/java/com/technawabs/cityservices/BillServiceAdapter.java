package com.technawabs.cityservices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technawabs.cityservices.models.BillService;
import com.technawabs.cityservices.utils.StringUtils;

import java.util.List;

public class BillServiceAdapter extends RecyclerView.Adapter<BillServiceAdapter.BillServiceViewHelper>{

    private Context context;
    private List<BillService> billServices;

    public BillServiceAdapter(@NonNull Context context,@NonNull List<BillService> billServices){
        this.context=context;
        this.billServices=billServices;
    }

    @Override
    public BillServiceViewHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new BillServiceViewHelper(view);
    }

    @Override
    public void onBindViewHolder(BillServiceViewHelper holder, int position) {
        final BillService billService=billServices.get(position);
        if(billService!=null){
            if(StringUtils.isNotEmptyOrNull(billService.getName())){
                holder.title.setText(billService.getName());
                holder.imageView.setImageResource(billService.getImageId());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,billService.getName()+" will be launched in next update",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return billServices.size();
    }

    public static class BillServiceViewHelper extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView imageView;
        private CardView cardView;

        public BillServiceViewHelper(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.service_title);
            imageView=(ImageView)itemView.findViewById(R.id.service_image);
            cardView=(CardView)itemView.findViewById(R.id.parent);
        }
    }
}
