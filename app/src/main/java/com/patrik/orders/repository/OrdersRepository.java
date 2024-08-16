package com.patrik.orders.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.patrik.orders.api.ApiResponse;
import com.patrik.orders.api.ApiService;
import com.patrik.orders.db.OrdersDao;
import com.patrik.orders.util.AppExecutors;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Contacts;
import com.patrik.orders.vo.Detail;
import com.patrik.orders.vo.Item;
import com.patrik.orders.vo.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository which handles Contact and Item objects
 */
@Singleton
public class OrdersRepository {
    private final OrdersDao ordersDao;
    private final ApiService apiService;
    private final AppExecutors appExecutors;

    @Inject
    OrdersRepository(AppExecutors appExecutors, OrdersDao ordersDao, ApiService apiService) {
        this.ordersDao = ordersDao;
        this.apiService = apiService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Contacts>> loadContacts() {
        return new NetworkBoundResource<Contacts, Contacts>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Contacts contacts) {
                ordersDao.insertContacts(contacts.items);
            }

            @Override
            protected boolean shouldFetch(@Nullable Contacts contacts) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Contacts> loadFromDb() {

                return Transformations.map(ordersDao.getAllContacts(),
                        contactList -> (new Contacts(contactList)));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Contacts>> createCall() {
                return apiService.getContacts();
            }
        }.asLiveData();
    }


    public LiveData<Resource<Detail>> loadDetail(final String contactId) {
        return new NetworkBoundResource<Detail, Detail>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Detail detail) {
                for (Item item : detail.items) {
                    item.contactId = contactId;
                    item.index = item.id.id;
                }

                ordersDao.insertItems(detail.items);
            }

            @Override
            protected boolean shouldFetch(@Nullable Detail detail) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Detail> loadFromDb() {

                return Transformations.map(ordersDao.getItems(contactId),
                        itemsList -> (new Detail(itemsList)));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Detail>> createCall() {

                return apiService.getDetail(contactId);
            }
        }.asLiveData();
    }


    public LiveData<Resource<Boolean>> addContact(Contact contact) {
        AddContactTask addContactTask = new AddContactTask(contact, apiService);
        appExecutors.networkIO().execute(addContactTask);
        return addContactTask.getLiveData();
    }


}
