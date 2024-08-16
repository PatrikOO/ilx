package com.patrik.orders.ui.contacts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

import com.patrik.orders.repository.OrdersRepository;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Resource;

import java.util.List;

import javax.inject.Inject;


public class ContactsViewModel extends ViewModel {

    private Parcelable contactsState;

    private OrdersRepository ordersRepository;

    @SuppressWarnings("unchecked")
    @Inject
    public ContactsViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }


    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public LiveData<Resource<List<Contact>>> getContacts() {
        return Transformations.map(ordersRepository.loadContacts(),
                contacts -> ((Resource<List<Contact>>) new Resource(contacts.status,
                        contacts.data == null ? null : contacts.data.items, contacts.message)));
    }

    public Parcelable getContactsState() {
        return contactsState;
    }

    public void setContactsState(Parcelable contactsState) {
        this.contactsState = contactsState;
    }
}
