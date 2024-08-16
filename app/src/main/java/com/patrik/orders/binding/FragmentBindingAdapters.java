package com.patrik.orders.binding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.patrik.orders.AppConfig;
import com.patrik.orders.R;

import javax.inject.Inject;

/**
 * Binding adapters that work with a fragment instance.
 */
public class FragmentBindingAdapters {
    final private Fragment fragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.photo_placeholder);
        requestOptions.error(R.drawable.photo_placeholder);

        Glide.with(fragment)
                .load(absoluteUrl(url))
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * Makes absolute url from relative url
     *
     * @param url relative or absolute
     * @return  absolute url
     */

    private String absoluteUrl(String url) {
        String result;

        if (url == null)
            result = "";
        else {
            if (url.startsWith("http:") || url.startsWith("https:"))
                result = url;
            else
                result = AppConfig.BASE_URL + url;
        }
        return result;
    }
}
