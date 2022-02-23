package com.example.modelfashion.History.ApdapterHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.modelfashion.History.MHistory.ProductHistory;
import com.example.modelfashion.R;

import java.util.List;

public class DetailHistoryAdapter extends BaseAdapter {
    Context context;
    List<ProductHistory> productHistoryList;

    public DetailHistoryAdapter(Context context, List<ProductHistory> productHistoryList) {
        this.context = context;
        this.productHistoryList = productHistoryList;
    }

    @Override
    public int getCount() {
        return productHistoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_subproducts,viewGroup,false);
        ImageView img_subproduct = view.findViewById(R.id.img_subproduct);
        TextView tv_name_subproduct = view.findViewById(R.id.tv_name_subproduct);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_size_subproduct = view.findViewById(R.id.tv_size_subproduct);
        TextView tv_sumproduct = view.findViewById(R.id.tv_sumproduct);
        ProductHistory productHistory = productHistoryList.get(i);
        tv_name_subproduct.setText(productHistory.getmNameProduct());
        tv_price.setText(productHistory.getmPriceProduct()+"đ");
        tv_size_subproduct.setText(productHistory.getmSizeProduct());
        tv_sumproduct.setText(productHistory.getmSumProduct());
        Glide.with(context).load(productHistory.getmImgeProduct()).into(img_subproduct);
        return view;
    }
}
