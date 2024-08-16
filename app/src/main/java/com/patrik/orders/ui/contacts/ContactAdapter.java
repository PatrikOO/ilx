package com.patrik.orders.ui.contacts;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.patrik.orders.R;
import com.patrik.orders.databinding.ContactItemBinding;
import com.patrik.orders.ui.common.DataBoundListAdapter;
import com.patrik.orders.util.Objects;
import com.patrik.orders.vo.Contact;

import static com.patrik.orders.AppConfig.ON_CLICK_DELAY_MS;

/**
 * A RecyclerView adapter for {@link Contact} class.
 */
public class ContactAdapter extends DataBoundListAdapter<Contact, ContactItemBinding> {

    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final ContactClickCallback contactClickCallback;

    public ContactAdapter(DataBindingComponent dataBindingComponent, ContactClickCallback contactClickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.contactClickCallback = contactClickCallback;

    }

    @Override
    protected ContactItemBinding createBinding(ViewGroup parent) {
        ContactItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.contact_item, parent,
                false, dataBindingComponent);

        binding.getRoot().setOnClickListener(v -> {
            Contact contact = binding.getContact();

            if (contact != null && contactClickCallback != null) {
                new Handler().postDelayed(() -> contactClickCallback.onClick(contact), ON_CLICK_DELAY_MS);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ContactItemBinding binding, Contact item) {
        binding.setContact(item);
    }

    @Override
    protected boolean areItemsTheSame(Contact oldItem, Contact newItem) {
        return Objects.equals(oldItem.id, newItem.id);
    }

    @Override
    protected boolean areContentsTheSame(Contact oldItem, Contact newItem) {
        return Objects.equals(oldItem.id, newItem.id);
    }

    public interface ContactClickCallback {
        void onClick(Contact contact);
    }
}