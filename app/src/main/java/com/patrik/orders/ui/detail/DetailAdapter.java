package com.patrik.orders.ui.detail;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.patrik.orders.R;
import com.patrik.orders.databinding.DetailItemBinding;
import com.patrik.orders.ui.common.DataBoundListAdapter;
import com.patrik.orders.util.Objects;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Item;

/**
 * A RecyclerView adapter for {@link Item} class.
 */
public class DetailAdapter extends DataBoundListAdapter<Item, DetailItemBinding> {

    private final android.databinding.DataBindingComponent dataBindingComponent;

    public DetailAdapter(android.databinding.DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;

    }

    @Override
    protected DetailItemBinding createBinding(ViewGroup parent) {

        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.detail_item, parent,false, dataBindingComponent);
    }

    @Override
    protected void bind(DetailItemBinding binding, Item item) {
        binding.setItem(item);
    }

    @Override
    protected boolean areItemsTheSame(Item oldItem, Item newItem) {
        return Objects.equals(oldItem.index, newItem.index);
    }

    @Override
    protected boolean areContentsTheSame(Item oldItem, Item newItem) {
        return Objects.equals(oldItem.index, newItem.index);
    }
}