package com.patrik.orders.ui.addcontact;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.patrik.orders.R;
import com.patrik.orders.binding.FragmentDataBindingComponent;
import com.patrik.orders.databinding.AddContactFragmentBinding;
import com.patrik.orders.di.Injectable;
import com.patrik.orders.ui.common.NavigationController;
import com.patrik.orders.ui.main.MainActivity;
import com.patrik.orders.util.AutoClearedValue;
import com.patrik.orders.vo.Contact;

import javax.inject.Inject;

/**
 * The UI Controller for adding new contact.
 */
public class AddContactFragment extends Fragment implements Injectable, AddCallback {

    @Inject
    Application application;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<AddContactFragmentBinding> binding;

    private AddContactViewModel addContactViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AddContactFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.add_contact_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addContactViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddContactViewModel.class);

        setupToolbar();

        binding.get().setAddCallback(this);
        binding.get().executePendingBindings();
    }


    @Override
    public void addClick(String name, String phone) {
        String errorText = addContactViewModel.getErrorText(getActivity(), name, phone);
        binding.get().setErrorText(errorText);

        if (errorText.isEmpty()) {

            addContactViewModel.addContact(new Contact(name, phone));

            addContactViewModel.getAddContactResponse().observe(this, result -> {
                if (result != null && getActivity() != null)
                    if (result.data != null && result.data) {
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(), result.message, Toast.LENGTH_LONG).show();
                    }
            });
        }
    }

    private void setupToolbar() {
        if (getActivity() != null) {
            ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
            if (toolbar != null) {
                toolbar.setDisplayHomeAsUpEnabled(true);
                toolbar.setDisplayShowHomeEnabled(true);
                toolbar.setTitle(application.getString(R.string.add_contact_title));
            }
        }
    }
}