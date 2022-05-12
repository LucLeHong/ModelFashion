package com.example.modelfashion.network;

import com.example.modelfashion.Model.request.LoginRequest;
import com.example.modelfashion.Model.response.LoginResponse;
import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.Model.response.category.CategoryResponse;
import com.example.modelfashion.Model.response.my_product.MyProduct;
import com.example.modelfashion.Model.response.product.ProductResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Single;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Single<CategoryResponse> getCategory(@Url String url);

    @GET
    Single<ProductResponse> getProductByCategory(@Url String url);

    @GET("get_all_products.php")
    Single<ArrayList<MyProduct>> getAllProduct();

    @FormUrlEncoded
    @POST("insert_user.php")
    Call<String> insertUser(@Field("taikhoan") String taikhoan,
                            @Field("matkhau") String matkhau);

    @FormUrlEncoded
    @POST("check_login_user.php")
    Call<User> checkLogin(@Field("taikhoan") String taikhoan,
                          @Field("matkhau") String matkhau);

    @GET("get_product_by_id.php")
    Single<MyProduct> getProductById(
            @Query("product_id") String productId,
            @Query("product_name") String productName
    );

    @GET("get_all_products_by_type.php")
    Single<ArrayList<MyProduct>> getProductByType(
            @Query("type") String type
    );

    @POST("/user/login")
    Single<LoginResponse> login(@Body LoginRequest request);

    @FormUrlEncoded
    @POST("update_user_info.php")
    Call<String> UpdateUser(@Field("user") JSONObject user);

}
