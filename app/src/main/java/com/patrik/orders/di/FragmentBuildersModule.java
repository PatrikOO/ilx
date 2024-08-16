package com.patrik.orders.di;

import com.patrik.orders.ui.addcontact.AddContactFragment;
import com.patrik.orders.ui.contacts.ContactsFragment;
import com.patrik.orders.ui.detail.DetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ContactsFragment contributeContactsFragment();

    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();

    @ContributesAndroidInjector
    abstract AddContactFragment contributeAddContactFragment();

}
