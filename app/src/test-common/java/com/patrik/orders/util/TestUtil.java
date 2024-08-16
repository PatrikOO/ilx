package com.patrik.orders.util;

import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Item;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static Contact createContact(String id, String name, String phone, String pictureUrl) {
        return new Contact(id, name, phone, pictureUrl);
    }


    public static List<Item> createItems(List<Contact> contacts, String name, int count) {
        List<Item> items = new ArrayList<>();
        String id;
        for (int i = 0; i < contacts.size(); i++) {
            for (int j = 0; j < count; j++) {
                id = Integer.toString(i * contacts.size() + j);
                items.add(new Item(id, contacts.get(i).id, name, count));
            }
        }

        return items;
    }

}
