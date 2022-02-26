package com.example.modelfashion.Fragment.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modelfashion.Adapter.category.CategoryAdapter;
import com.example.modelfashion.Adapter.category.ClothesAdapter;
import com.example.modelfashion.R;
import com.example.modelfashion.customview.SpacesItemDecoration;
import com.example.modelfashion.model.Category;
import com.example.modelfashion.model.Product;

import java.util.ArrayList;
import java.util.List;


public class CategoryMenFragment extends Fragment {
    private CategoryAdapter categoryAdapter;
    private ClothesAdapter clothesAdapter;
    private RecyclerView rcvCategory, rcvClothes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_men, container, false);

        initView(view);
        initData();
        initListener();

        return view;
    }

    private void initData() {
    }

    private void initListener() {
        categoryAdapter.setClickListener((view, position) -> {
            // TODO category
        });

        clothesAdapter.setClickListener((view, position) -> {
            // TODO clothes
        });
    }

    private void initView(View view) {
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setListCategory(listCategory1());
        rcvCategory = view.findViewById(R.id.rcv_category);
        rcvCategory.setAdapter(categoryAdapter);

        rcvClothes = view.findViewById(R.id.rcv_clothes);
        clothesAdapter = new ClothesAdapter();
        clothesAdapter.setListProduct(listProduct());
        rcvClothes.setAdapter(clothesAdapter);
        rcvClothes.addItemDecoration(new SpacesItemDecoration(8));

    }

    private List<Category> listCategory1() {
        ArrayList<Category> list = new ArrayList();
        list.add(new Category(1, "Ba lô"));
        list.add(new Category(2, "Quần"));
        list.add(new Category(3, "Áo"));
        list.add(new Category(4, "Giày"));
        list.add(new Category(5, "Đồ bộ"));
        return list;
    }

    private List<Category> listCategory2() {
        ArrayList<Category> list = new ArrayList();
        list.add(new Category(1, "Ba lô"));
        list.add(new Category(2, "Quần"));
        list.add(new Category(3, "Áo"));
        list.add(new Category(4, "Giày"));
        list.add(new Category(5, "Đồ bộ"));
        return list;
    }

    private List<Product> listProduct() {
        ArrayList<Product> list = new ArrayList();
        list.add(new Product(1, "GD - BLACK", "", "450,000đ", ""));
        list.add(new Product(2, "GD - WHITE", "", "450,000đ", ""));
        list.add(new Product(3, "GD - BLACK", "", "420,000đ", ""));
        list.add(new Product(4, "GD - BLACK", "", "690,000đ", ""));
        list.add(new Product(5, "GD - BLACK", "", "450,000đ", ""));
        list.add(new Product(6, "GD - BLACK", "", "690,000đ", ""));
        list.add(new Product(7, "GD - BLACK", "", "690,000đ", ""));
        list.add(new Product(8, "GD - BLACK", "", "690,000đ", ""));
        list.add(new Product(9, "GD - BLACK", "", "690,000đ", ""));
        return list;
    }

}