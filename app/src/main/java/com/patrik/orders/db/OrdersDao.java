package com.patrik.orders.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Item;

import java.util.List;

/**
 * Abstract class for database access on Contact, Item related operations.
 */
@Dao
public abstract class OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertContacts(List<Contact> contacts);

    @Query("SELECT * FROM Contact")
    public abstract LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM Contact WHERE id= :id")
    public abstract LiveData<Contact> getContact(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertItems(List<Item> items);

    @Query("SELECT * FROM Item WHERE contactId= :contactId")
    public abstract LiveData<List<Item>> getItems(String contactId);


}

