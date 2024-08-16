package com.patrik.orders.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.patrik.orders.api.ApiResponse;
import com.patrik.orders.api.ApiService;
import com.patrik.orders.vo.AddResponse;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Resource;

import java.io.IOException;

import retrofit2.Response;
import timber.log.Timber;

/**
 * A task which adds new contact to backend and check if it was succesfull
 */
public class AddContactTask implements Runnable {
    private final MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
    private final Contact contact;
    private final ApiService apiService;

    public AddContactTask(Contact contact, ApiService apiService) {
        this.contact = contact;
        this.apiService = apiService;
    }

    @Override
    public void run() {

        try {
            Response<AddResponse> response = apiService.addContact(contact).execute();
            ApiResponse<AddResponse> apiResponse = new ApiResponse<>(response);

            if (apiResponse.isSuccessful()) {
                liveData.postValue(Resource.success(true));
            } else {
                liveData.postValue(Resource.error(apiResponse.errorMessage, false));
                Timber.e(apiResponse.errorMessage);
            }
        } catch (IOException e) {
            liveData.postValue(Resource.error(e.getMessage(), false));
        }
    }

    public LiveData<Resource<Boolean>> getLiveData() {
        return liveData;
    }
}
