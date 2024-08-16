package com.patrik.orders.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.patrik.orders.api.ApiResponse;
import com.patrik.orders.api.ApiService;
import com.patrik.orders.db.OrdersDao;
import com.patrik.orders.util.ApiUtil;
import com.patrik.orders.util.InstantAppExecutors;
import com.patrik.orders.util.TestUtil;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Contacts;
import com.patrik.orders.vo.Detail;
import com.patrik.orders.vo.Item;
import com.patrik.orders.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class OrdersRepositoryTest {
    private OrdersDao ordersDao;
    private ApiService apiService;
    private OrdersRepository ordersRepository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        ordersDao = mock(OrdersDao.class);
        apiService = mock(ApiService.class);
        ordersRepository = new OrdersRepository(new InstantAppExecutors(), ordersDao, apiService);
    }

    @Test
    public void loadContacts() {
        ordersRepository.loadContacts();
        verify(ordersDao).getAllContacts();
    }

    @Test
    public void loadDetail() {
        ordersRepository.loadDetail("1");
        verify(ordersDao).getItems("1");
    }

    @Test
    public void loadContactsFromNetwork() {
        MutableLiveData<List<Contact>> dbData = new MutableLiveData<>();
        when(ordersDao.getAllContacts()).thenReturn(dbData);

        Contact contact = TestUtil.createContact("1", "name", "phone", "url");
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);
        Contacts contacts = new Contacts(contactList);

        LiveData<ApiResponse<Contacts>> call = ApiUtil.successCall(contacts);
        when(apiService.getContacts()).thenReturn(call);
        Observer<Resource<Contacts>> observer = mock(Observer.class);

        ordersRepository.loadContacts().observeForever(observer);
        verify(apiService, never()).getContacts();
        MutableLiveData<List<Contact>> updatedDbData = new MutableLiveData<>();
        when(ordersDao.getAllContacts()).thenReturn(updatedDbData);
        dbData.setValue(null);
        verify(apiService).getContacts();
    }



    @Test
    public void loadItemsFromNetwork() {
        MutableLiveData<List<Item>> dbData = new MutableLiveData<>();
        when(ordersDao.getItems("1")).thenReturn(dbData);

        Contact contact = TestUtil.createContact("1", "name", "phone", "url");
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);
        List<Item> items = TestUtil.createItems(contactList, "name", 1);
        Detail detail = new Detail(items);

        LiveData<ApiResponse<Detail>> call = ApiUtil.successCall(detail);
        when(apiService.getDetail("1")).thenReturn(call);
        Observer<Resource<Detail>> observer = mock(Observer.class);

        ordersRepository.loadDetail("1").observeForever(observer);
        verify(apiService, never()).getDetail("1");
        MutableLiveData<List<Item>> updatedDbData = new MutableLiveData<>();
        when(ordersDao.getItems("1")).thenReturn(updatedDbData);
        dbData.setValue(null);
        verify(apiService).getDetail("1");
    }
}