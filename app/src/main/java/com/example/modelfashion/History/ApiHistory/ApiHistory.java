package com.example.modelfashion.History.ApiHistory;

import com.example.modelfashion.Model.response.bill.Address;
import com.example.modelfashion.Model.response.bill.Bill;

import com.example.modelfashion.Model.response.bill.BillDetail;
import com.example.modelfashion.Model.response.bill.CancelBill;
import com.example.modelfashion.Model.response.bill.FeedbackBill;
import com.example.modelfashion.Model.response.bill.RefundOfOrder;
import com.example.modelfashion.Model.response.bill.UpdateAdress;
import com.example.modelfashion.Model.response.bill.UserID;
import com.example.modelfashion.Model.sale.SaleModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHistory {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiHistory  API_HISTORY = new Retrofit.Builder()
            .baseUrl("http://103.179.189.107:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiHistory.class);
    @GET("bill/getListBill/{id}")
    Call<ArrayList<Bill>> getBill(@Path("id") String id);

    @GET("bill/getBillDetail/{id}")
    Call<BillDetail> getBillDetail(@Path("id") String id);

    @PUT("bill/cancelOrder")
    Call<CancelBill> cancelBill(@Body CancelBill cancelBill);
    @PUT("bill/returnRequest")
    Call<RefundOfOrder> refundOfOrder(@Body RefundOfOrder refundOfOrder);
    @PUT("bill/feedback")
    Call<FeedbackBill> feedbackBill(@Body FeedbackBill feedbackBill);

    @GET("user/getAddress/{id}")
    Call<Address> getAddress(@Path("id") String id);

    @PUT("user/updateAddress")
    Call<UpdateAdress> updateAddress(@Body UpdateAdress updateAdress);

    @GET("product/getProductDiscount")
    Call<SaleModel> getListSale();


}
