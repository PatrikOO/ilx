package com.patrik.orders.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Item;

/**
 * Database description.
 */
@Database(entities = {Item.class, Contact.class}, version = 2)
public abstract class OrdersDb extends RoomDatabase {

    abstract public OrdersDao ordersDao();

}

