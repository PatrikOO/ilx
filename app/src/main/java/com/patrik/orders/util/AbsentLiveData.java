package com.patrik.orders.util;

import android.arch.lifecycle.LiveData;

/**
 * A LiveData class that has {@code null} value.
 */

@SuppressWarnings("unchecked")
public class AbsentLiveData extends LiveData {

    private AbsentLiveData() {
        postValue(null);
    }

    @SuppressWarnings("unchecked")
    public static <T> LiveData<T> create() {
        //noinspection unchecked

        return new AbsentLiveData();
    }
}
