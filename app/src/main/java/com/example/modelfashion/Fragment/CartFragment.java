package com.example.modelfashion.Fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.modelfashion.Adapter.cart.CartAdapter;
import com.example.modelfashion.Model.response.my_product.CartProduct;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.example.modelfashion.R;
import com.example.modelfashion.database.AppDatabase;
import com.example.modelfashion.database.MyProductCart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CartFragment extends Fragment {
    private View initView;
    private RecyclerView recyclerView;
    private ArrayList<MyProduct> arrProduct = new ArrayList<>();
    private ArrayList<CartProduct> arrCart = new ArrayList<>();
    private ArrayList<Sizes> arrSize = new ArrayList<>();
    private ArrayList<String> arr_size_id = new ArrayList<>();
    private ArrayList<String> arr_product_name = new ArrayList<>();
    private String user_id, total_money;
    private TextView tvTotal;
    private Button btn_payment;
    private SwipeRefreshLayout refreshLayout;

    private Boolean check_load_successful = false;
    private CompositeDisposable disposable = new CompositeDisposable();

    CartAdapter adapter = new CartAdapter();


    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = initView.findViewById(R.id.list_product_cart);
        tvTotal = initView.findViewById(R.id.total_money);
        btn_payment = initView.findViewById(R.id.btn_payment);
        refreshLayout = initView.findViewById(R.id.refresh_layout);

        setAdapter();
        getProductInCart();

        btn_payment.setOnClickListener(v -> {

        });

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            getProductInCart();
            tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
        });
        return initView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getProductInCart() {
        Single<List<MyProductCart>> list = AppDatabase.getInstance(requireContext()).cartDao().getAllProductInCart();
        disposable.add(list
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                }).subscribe(myProductCarts -> {
                    adapter.setListData(myProductCarts);
                    tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
                }, throwable -> {
                    Log.d("ahuhu", "getProductInCart: error" + throwable.toString());
                }));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAdapter() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(new CartAdapter.CartOnClick() {
            @Override
            public void OnClickDelete(int position, MyProductCart myProductCart) {
                deleteProductFromCart(position, myProductCart);
                tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
            }

            @Override
            public void OnClickIncreaseQuantity(int position, MyProductCart myProductCart) {
                adapter.increaseAmount(position);
                tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
            }

            @Override
            public void OnClickDecreaseQuantity(int position, MyProductCart myProductCart) {
                adapter.decreaseAmount(position);
                tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteProductFromCart(int position, MyProductCart myProductCart) {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                .create();
        alertDialog.setTitle("Thông báo");
        alertDialog.setCancelable(false);

        alertDialog.setMessage("Xóa khỏi giỏ hàng");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Xóa", (dialogInterface, i) -> {
            disposable.add(AppDatabase.getInstance(requireContext()).cartDao().removeProductFromCart(myProductCart)
                    .doOnSubscribe(disposable1 -> {})
                    .doFinally(() -> {})
                    .subscribe(() -> {
                        tvTotal.setText("Tổng tiền: " + moneyFormat(adapter.getTotal()));
                        adapter.removeProduct(position);
                    },throwable -> {}));
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy", (dialogInterface, i) -> {

                });

        alertDialog.show();
    }


    private String moneyFormat(Long amount){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("VND"));
        return format.format(amount);
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}