package com.patrik.orders.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Contact {

    @NonNull
    @PrimaryKey
    public String id;
    public String name;
    public String phone;
    public String pictureUrl;

    @Ignore
    public Contact(String id, String name, String phone, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.pictureUrl = pictureUrl;
    }


    public Contact(String name, String phone, String pictureUrl) {
            this(null, name, phone, pictureUrl);
    }

    @Ignore
    public Contact(String name, String phone) {
        this(name, phone, null);
    }

    @Ignore
    public Contact() {
    }
}
