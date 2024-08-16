package com.patrik.orders.ui.detail;

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

import com.patrik.orders.R;
import com.patrik.orders.binding.FragmentDataBindingComponent;
import com.patrik.orders.databinding.DetailFragmentBinding;
import com.patrik.orders.di.Injectable;
import com.patrik.orders.ui.common.NavigationController;
import com.patrik.orders.ui.main.MainActivity;
import com.patrik.orders.util.AutoClearedValue;

import javax.inject.Inject;

/**
 * The UI Controller for displaying detail about contact and list of available items with counts
 */
public class DetailFragment extends Fragment implements Injectable {
    private static final String CONTACT_ID_KEY = "ContactId";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController navigationController;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<DetailFragmentBinding> binding;

    AutoClearedValue<DetailAdapter> adapter;

    private DetailViewModel detailViewModel;
    private ActionBar toolbar;

    public static DetailFragment create(String contactId) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTACT_ID_KEY, contactId);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DetailFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);
        
        Bundle args = getArguments();
        if (args != null && args.containsKey(CONTACT_ID_KEY))
            detailViewModel.setContactIdStore(args.getString(CONTACT_ID_KEY));
        else
            return;

        setupToolbar();

        binding.get().setContact(detailViewModel.getContact().getValue());

        detailViewModel.getContact().observe(this, contact -> {
            binding.get().setContact(contact);
            if (toolbar != null && contact != null)
                toolbar.setTitle(contact.name);

            binding.get().executePendingBindings();
        });

        loadItems();
        DetailAdapter contactListAdapter = new DetailAdapter(dataBindingComponent);
        binding.get().itemsList.setAdapter(contactListAdapter);
        adapter = new AutoClearedValue<>(this, contactListAdapter);

        binding.get().executePendingBindings();
    }

    @Override
    public void onResume() {
        super.onResume();
        detailViewModel.refresh();
    }

    private void loadItems() {

        detailViewModel.getItems().observe(this, result -> {
            binding.get().setDetailResource(result);
            adapter.get().replace(result == null ? null : result.data);
            binding.get().executePendingBindings();
        });
    }

    private void setupToolbar() {
        if (getActivity() != null) {
            toolbar = ((MainActivity) getActivity()).getSupportActionBar();
            if (toolbar != null) {
                toolbar.setDisplayHomeAsUpEnabled(true);
                toolbar.setDisplayShowHomeEnabled(true);
            }
        }
    }
}