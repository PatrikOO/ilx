package com.patrik.orders.api;

import android.arch.lifecycle.LiveData;

import com.patrik.orders.vo.AddResponse;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Contacts;
import com.patrik.orders.vo.Detail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * REST API access points
 */
public interface ApiService {

    @Headers({"content-type: application/json"})
    @GET("_ah/api/contactendpoint/v1/contact")
    LiveData<ApiResponse<Contacts>> getContacts();

    @Headers({"content-type: application/json"})
    @GET("_ah/api/orderendpoint/v1/order/{id}")
    LiveData<ApiResponse<Detail>> getDetail(@Path("id") String id);

    @Headers({"content-type: application/json"})
    @POST("_ah/api/contactendpoint/v1/contact")
    Call<AddResponse> addContact(@Body Contact contact);
}
