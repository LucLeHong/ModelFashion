package com.example.modelfashion.Activity;

import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_ID;
import static com.example.modelfashion.Utility.Constants.KEY_PRODUCT_NAME;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.modelfashion.Activity.SignIn.SignInActivity;
import com.example.modelfashion.Adapter.ViewPagerDetailProductAdapter;
import com.example.modelfashion.Interface.ApiRetrofit;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.my_product.Sizes;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.example.modelfashion.network.Repository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView img_back, img_cart, img_prev, img_next, img_product,
            img_size_s, img_size_m, img_size_l, img_size_xl;
    private TextView tv_price, tv_product_name, tv_product_category, tv_product_availability,
            btn_mua_ngay, btn_them_vao_gio_hang;
    private ViewPager2 viewPager;
    private ViewPagerDetailProductAdapter adapter;
    private Repository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ProgressBar progressBar;

    ArrayList<Sizes> arr_size = new ArrayList<>();
    String size_id, price;
    String user_id = "";
    String productId = "";
    String productName = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        productName = intent.getStringExtra(KEY_PRODUCT_NAME);
        productId = intent.getStringExtra(KEY_PRODUCT_ID);
        Log.e("t1", user_id+" ");
        // TODO use id to call detail product api
        ApiRetrofit.apiRetrofit.GetProductsSize(productName).enqueue(new Callback<ArrayList<Sizes>>() {
            @Override
            public void onResponse(Call<ArrayList<Sizes>> call, Response<ArrayList<Sizes>> response) {
                arr_size = response.body();
                size_id = arr_size.get(0).getId();
            }

            @Override
            public void onFailure(Call<ArrayList<Sizes>> call, Throwable t) {

            }
        });
        repository = new Repository(getApplicationContext());

        initView();
        initData();
        initListener();
    }

    private void initData() {
        disposable.add(
                repository.getProductById(productId, productName).doOnSubscribe(disposable -> {
                    progressBar.setVisibility(View.VISIBLE);
                }).subscribe(myProduct -> {
                    progressBar.setVisibility(View.GONE);
                    setData(myProduct);

                }, throwable -> {

                }));
    }

    private void setData(MyProduct myProduct) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        adapter.setArrItem(myProduct.getPhotos());
        price = myProduct.getPrice();
        tv_product_name.setText(myProduct.getProduct_name());
        tv_price.setText(format.format(Integer.parseInt(myProduct.getPrice()))+" VNĐ");
        tv_product_category.setText("Loại sản phẩm: " + myProduct.getSubtype());

        Glide.with(this).load(myProduct.getPhotos().get(0)).placeholder(R.drawable.test_img2).into(img_product);
    }

    private int currentCoverImage = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initListener() {
        img_back.setOnClickListener(view -> {
            finish();
        });
        img_cart.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            intent.putExtra("use_id",user_id);
            startActivity(intent);
            // TODO CART
        });
        img_prev.setOnClickListener(view -> {
            // TODO PREV
            if (viewPager.getCurrentItem() == 0) {
                currentCoverImage = adapter.getItemCount() - 1;
            } else {
                currentCoverImage = currentCoverImage - 1;
            }
            viewPager.setCurrentItem(currentCoverImage);
        });
        img_next.setOnClickListener(view -> {
            // TODO NEXT
            if (viewPager.getCurrentItem() == (adapter.getItemCount() - 1)) {
                currentCoverImage = 0;
            } else {
                currentCoverImage = currentCoverImage + 1;
            }
            viewPager.setCurrentItem(currentCoverImage);
        });
        loadImageUrl("https://cf.shopee.vn/file/7624d506af9460c8f4e8e5a80c30b514", img_product);
        img_size_s.setOnClickListener(view -> {
            // TODO SIZE S
            size_id = arr_size.get(0).getId();
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s_red).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);
           // Toast.makeText(this, "Đã chọn size " + arr_size.get(0).getSize(), Toast.LENGTH_SHORT).show();
        });
        img_size_m.setOnClickListener(view -> {
            // TODO SIZE M
            size_id = arr_size.get(1).getId();
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m_red).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);
            //Toast.makeText(this, "Đã chọn size " + arr_size.get(1).getSize(), Toast.LENGTH_SHORT).show();
        });
        img_size_l.setOnClickListener(view -> {
            // TODO SIZE L
            size_id = arr_size.get(2).getId();
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l_red).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl).into(img_size_xl);
            //Toast.makeText(this, "Đã chọn size " + arr_size.get(2).getSize(), Toast.LENGTH_SHORT).show();
        });
        img_size_xl.setOnClickListener(view -> {
            // TODO SIZE XL
            size_id = arr_size.get(3).getId();
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_s).into(img_size_s);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_m).into(img_size_m);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_l).into(img_size_l);
            Glide.with(ProductDetailActivity.this).load(R.drawable.ic_size_xl_z).into(img_size_xl);
            //Toast.makeText(this, "Đã chọn size " + arr_size.get(3).getSize(), Toast.LENGTH_SHORT).show();
        });

        tv_price.setText("650,000 VND");
        tv_product_name.setText("MARU BLAZER (WHITE)");
        tv_product_category.setText("Loại sản phẩm: MARU BLAZER");
        tv_product_availability.setText("Tình trạng: còn hàng");

        btn_mua_ngay.setOnClickListener(view -> {
            // TODO BUY
            if(user_id.equalsIgnoreCase("null")){
                Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                String date = LocalDate.now().toString();
                ApiRetrofit.apiRetrofit.CheckSizeLeft(size_id, "1").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("ok")) {
                            ApiRetrofit.apiRetrofit.InsertBillBuyNow(user_id, date, price, size_id).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(ProductDetailActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        } else if (response.body().equals("fail")) {
                            Toast.makeText(ProductDetailActivity.this, "Size này đã hết hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Lỗi db", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        btn_them_vao_gio_hang.setOnClickListener(view -> {
            // TODO ADD ON CART
            if(user_id.equalsIgnoreCase("null")){
                showDialogLogIn();
               // Toast.makeText(this, "Bạn chưa thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            } else {
                ApiRetrofit.apiRetrofit.CheckSizeLeft(size_id, "1").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("ok")) {
                            ApiRetrofit.apiRetrofit.InsertCart(user_id, size_id, productName, "1").enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if (response.body().equals("duplicated")) {
                                        Toast.makeText(ProductDetailActivity.this, "Sản phẩm đã nằm trong giỏ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (response.body().equals("ok")) {
                                            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                                            intent.putExtra("use_id",user_id);
                                            startActivity(intent);
                                            Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ProductDetailActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                            Log.e("err", response.body());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {

                                }
                            });
                        } else if (response.body().equals("fail")) {
                            Toast.makeText(ProductDetailActivity.this, "Size này đã hết hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Lỗi db", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void initView() {
        progressBar = findViewById(R.id.progress_bar);
        viewPager = findViewById(R.id.view_pager);
        img_back = findViewById(R.id.img_back);
        img_cart = findViewById(R.id.img_cart);
        img_prev = findViewById(R.id.img_prev);
        img_next = findViewById(R.id.img_next);
        img_product = findViewById(R.id.img_product);
        img_size_s = findViewById(R.id.img_size_s);
        img_size_m = findViewById(R.id.img_size_m);
        img_size_l = findViewById(R.id.img_size_l);
        img_size_xl = findViewById(R.id.img_size_xl);

        tv_price = findViewById(R.id.tv_price);
        tv_product_name = findViewById(R.id.tv_product_name);
        tv_product_category = findViewById(R.id.tv_product_category);
        tv_product_availability = findViewById(R.id.tv_product_availability);
        btn_mua_ngay = findViewById(R.id.btn_mua_ngay);
        btn_them_vao_gio_hang = findViewById(R.id.btn_them_vao_gio_hang);

        adapter = new ViewPagerDetailProductAdapter();

        viewPager.setAdapter(adapter);
    }

//    private ArrayList<String> listDetailImage() {
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("https://zunezx.com/upload/image/cache/data/banner/Tee/42BA878D-053A-4FA1-9444-1865E38690C4-a69-crop-550-550.jpeg");
//        arrayList.add("https://zunezx.com/upload/image/cache/data/banner/Tee/CBF4903A-0C16-4F81-AEC5-4000A1D11120-470-crop-550-550.jpeg");
//        arrayList.add("https://zunezx.com/upload/image/cache/data/banner/Tee/33DC813B-A4BF-4F91-8ADA-F2B0C85A225A-1e8-crop-550-550.jpeg");
//        arrayList.add("https://zunezx.com/upload/image/data/banner/Tee/05D3F876-B270-4916-9FF0-FE48332E1DB3-da9.jpeg");
//        return arrayList;
//    }

    private void loadImageUrl(String url, ImageView img) {
        Glide.with(this).load(url).placeholder(R.drawable.ic_logo).into(img);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void showDialogLogIn(){
        Dialog dialog = new Dialog(ProductDetailActivity.this);
        dialog.setContentView(R.layout.dialog_quest_login);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_yes_login,tv_no_login;
        tv_yes_login = dialog.findViewById(R.id.tv_yes_login);
        tv_no_login = dialog.findViewById(R.id.tv_no_login);
        tv_no_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_yes_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ProductDetailActivity.this, SignInActivity.class));
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}