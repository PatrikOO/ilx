package com.patrik.orders.ui.addcontact;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.patrik.orders.R;
import com.patrik.orders.api.ApiService;
import com.patrik.orders.repository.OrdersRepository;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Resource;

import javax.inject.Inject;


public class AddContactViewModel extends ViewModel {

    private static final int MIN_NAME_LENGTH = 5;
    private static final int MIN_PHONE_LENGTH = 5;

    @Inject
    Application application;

    @Inject
    ApiService apiService;

    @Inject
    OrdersRepository ordersRepository;

    private LiveData<Resource<Boolean>> addContactResponse = new LiveData<Resource<Boolean>>() {};

    @SuppressWarnings("unchecked")
    @Inject
    public AddContactViewModel() {

    }


    public void addContact(final Contact contact) {
        addContactResponse = ordersRepository.addContact(contact);
    }

    @NonNull
    public LiveData<Resource<Boolean>> getAddContactResponse() {
        return addContactResponse;
    }

    public String getErrorText(Context context, String name, String phone) {
        String result = "";

        if (name == null || name.length() < MIN_NAME_LENGTH)
            result = context.getString(R.string.error_name);

        if (phone == null || phone.length() < MIN_PHONE_LENGTH)
            result = result + (result.isEmpty() ? "" : "\n") + context.getString(R.string.error_phone);

        return result;
    }
}