package com.patrik.orders.db;


import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

abstract public class OrdersDbTest {
    protected OrdersDb db;

    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), OrdersDb.class).build();
    }

    @After
    public void closeDb() {
        db.close();
    }
}
