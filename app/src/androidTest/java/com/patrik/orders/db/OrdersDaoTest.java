package com.patrik.orders.db;

import android.support.test.runner.AndroidJUnit4;

import com.patrik.orders.util.TestUtil;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Item;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.patrik.orders.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class OrdersDaoTest extends OrdersDbTest {

    @Test
    public void insertAndLoad() throws InterruptedException {

        final Contact contact = TestUtil.createContact("1", "name1", "phone1", "url1");
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        db.ordersDao().insertContacts(contacts);

        final List<Contact> loadedContacts = getValue(db.ordersDao().getAllContacts());
        assertThat(loadedContacts.size(), is(1));

        final Contact loadedContact = loadedContacts.get(0);
        assertThat(loadedContact.id, is("1"));
        assertThat(loadedContact.name, is("name1"));
        assertThat(loadedContact.phone, is("phone1"));
        assertThat(loadedContact.pictureUrl, is("url1"));


        final Contact replacedContact = TestUtil.createContact("1", "name2", "phone2", "url2");
        List<Contact> replacedContacts = new ArrayList<>();
        replacedContacts.add(replacedContact);
        db.ordersDao().insertContacts(replacedContacts);


        final List<Contact> loadedReplacedContacts = getValue(db.ordersDao().getAllContacts());
        assertThat(loadedReplacedContacts.size(), is(1));

        final Contact loadedReplacedContact = loadedReplacedContacts.get(0);
        assertThat(loadedReplacedContact.id, is("1"));
        assertThat(loadedReplacedContact.name, is("name2"));
        assertThat(loadedReplacedContact.phone, is("phone2"));
        assertThat(loadedReplacedContact.pictureUrl, is("url2"));


        final List<Item> items = TestUtil.createItems(loadedReplacedContacts, "name", 10);
        db.ordersDao().insertItems(items);

        final List<Item> loadedItems = getValue(db.ordersDao().getItems("1"));
        assertThat(loadedItems.size(), is(10));

        for (int i = 0; i < loadedItems.size(); i++) {
            Item item = loadedItems.get(i);
            assertThat(item.contactId, is(loadedReplacedContact.id));
            assertThat(item.name, is("name"));
            assertThat(item.count, is(10));
        }
    }
}
