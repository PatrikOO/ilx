package com.patrik.orders.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.patrik.orders.ui.addcontact.AddContactViewModel;
import com.patrik.orders.ui.contacts.ContactsViewModel;
import com.patrik.orders.ui.detail.DetailViewModel;
import com.patrik.orders.ui.main.MainActivityViewModel;
import com.patrik.orders.util.OrdersViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel.class)
    abstract ViewModel bindContactsViewModel(ContactsViewModel contactsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddContactViewModel.class)
    abstract ViewModel bindAddContactViewModel(AddContactViewModel addContactViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel detailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(OrdersViewModelFactory factory);
}

