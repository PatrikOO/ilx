package com.patrik.orders.ui.contacts;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.patrik.orders.R;
import com.patrik.orders.binding.FragmentDataBindingComponent;
import com.patrik.orders.databinding.ContactsFragmentBinding;
import com.patrik.orders.di.Injectable;
import com.patrik.orders.ui.common.NavigationController;
import com.patrik.orders.ui.main.MainActivity;
import com.patrik.orders.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * The UI Controller for displaying list of contacts with avatar, name and phone.
 */
public class ContactsFragment extends Fragment implements Injectable {

    @Inject
    Application application;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<ContactsFragmentBinding> binding;

    AutoClearedValue<ContactAdapter> adapter;

    private ContactsViewModel contactsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ContactsFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.contacts_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactsViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel.class);

        setupToolbar();

        ContactAdapter contactListAdapter = new ContactAdapter(dataBindingComponent,
                contact -> navigationController.navigateToDetail(contact.id));

        binding.get().contactList.setAdapter(contactListAdapter);
        adapter = new AutoClearedValue<>(this, contactListAdapter);
    }


    public static ContactsFragment create() {
        return new ContactsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initContacts();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            navigationController.navigateToAddContact();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initContacts() {
        contactsViewModel.setContactsState(binding.get().contactList.getLayoutManager().onSaveInstanceState());

        contactsViewModel.getContacts().observe(this, result -> {
            binding.get().setContactResource(result);
            adapter.get().replace(result == null ? null : result.data);

            binding.get().contactList.getLayoutManager().onRestoreInstanceState(contactsViewModel.getContactsState());

            binding.get().executePendingBindings();
        });
    }

    private void setupToolbar() {
        if (getActivity() != null) {
            ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
            if (toolbar != null) {
                toolbar.setDisplayHomeAsUpEnabled(false);
                toolbar.setDisplayShowHomeEnabled(false);
                toolbar.setTitle(application.getString(R.string.app_name));
            }
        }
    }
}
