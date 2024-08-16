package com.patrik.orders.ui.common;

import android.support.v4.app.FragmentManager;

import com.patrik.orders.R;
import com.patrik.orders.ui.addcontact.AddContactFragment;
import com.patrik.orders.ui.contacts.ContactsFragment;
import com.patrik.orders.ui.detail.DetailFragment;
import com.patrik.orders.ui.main.MainActivity;

import javax.inject.Inject;


/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToContacts() {
        ContactsFragment contactsFragment = new ContactsFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, contactsFragment, "ContactsFragment")
                .commitAllowingStateLoss();
    }

    public void navigateToDetail(String contactId) {
        DetailFragment detailFragment = DetailFragment.create(contactId);
        fragmentManager.beginTransaction()
                .replace(containerId, detailFragment, "DetailFragment")
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToAddContact() {
        AddContactFragment addContactFragment = new AddContactFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, addContactFragment, "AddContactFragment")
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
