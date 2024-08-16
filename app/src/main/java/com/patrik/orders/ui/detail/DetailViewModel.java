package com.patrik.orders.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.patrik.orders.db.OrdersDb;
import com.patrik.orders.repository.OrdersRepository;
import com.patrik.orders.util.AbsentLiveData;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Detail;
import com.patrik.orders.vo.Item;
import com.patrik.orders.vo.Resource;

import java.util.List;

import javax.inject.Inject;


public class DetailViewModel extends ViewModel {

    @Inject
    OrdersRepository ordersRepository;

    private OrdersDb db;

    @VisibleForTesting
    private MutableLiveData<String> contactId = new MutableLiveData<>();
    private String contactIdStore;

    private LiveData<Resource<Detail>> detail;

    private LiveData<Contact> contact;


    @SuppressWarnings("unchecked")
    @Inject
    public DetailViewModel(OrdersDb ordersDb) {
        db = ordersDb;

        detail = Transformations.switchMap(contactId, contactId -> {
            if (contactId == null) {
                return AbsentLiveData.create();
            } else {
                return ordersRepository.loadDetail(contactId);
            }
        });

        contact = Transformations.switchMap(contactId, contactId -> {
            if (contactId == null) {
                return AbsentLiveData.create();
            } else {
                return db.ordersDao().getContact(contactId);
            }
        });
    }


    @VisibleForTesting
    public void setContactIdStore(String contactIdStore) {
        this.contactIdStore=contactIdStore;
    }

    @VisibleForTesting
    public LiveData<Resource<Detail>> getDetail() {
        return this.detail;
    }

    @VisibleForTesting
    public LiveData<Contact> getContact() {
        return this.contact;
    }


    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public LiveData<Resource<List<Item>>> getItems() {

        return Transformations.map(this.detail,
                detail -> ((Resource<List<Item>>) new Resource(detail.status, detail.data == null ? null : detail.data.items, detail.message)));
    }

    public void refresh() {
        if (contactId != null && contactIdStore != null)
            contactId.postValue(contactIdStore);
    }
}