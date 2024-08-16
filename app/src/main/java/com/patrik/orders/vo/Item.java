package com.patrik.orders.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(indices = {@Index("contactId")},
        primaryKeys = {"contactId", "index"},
        foreignKeys = @ForeignKey(entity = Contact.class,
                parentColumns = "id",
                childColumns = "contactId",
                onUpdate = CASCADE,
                deferred = true))
public class Item {

    @NonNull
    public String index;
    @NonNull
    public String contactId;
    public String name;
    public int count;

    @Ignore
    public Id id;

    public String getId(){
        return id == null ? null : id.id;
    }

    public class Id {
        public String id;

        public Id(String id) {
            this.id = id;
        }
    }

    public Item() {
    }

    @Ignore
    public Item(@NonNull String index, @NonNull String contactId, String name, int count) {
        this.index = index;
        this.contactId = contactId;
        this.name = name;
        this.count = count;
        this.id = new Id(index);
    }
}