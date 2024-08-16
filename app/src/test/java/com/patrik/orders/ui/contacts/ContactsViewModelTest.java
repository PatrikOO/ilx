package com.patrik.orders.ui.contacts;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.patrik.orders.repository.OrdersRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class ContactsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private OrdersRepository repository;
    private ContactsViewModel contactsViewModel;

    @Before
    public void setup() {
        repository = mock(OrdersRepository.class);
        contactsViewModel = new ContactsViewModel(repository);
    }

    @Test
    public void testNull() {
        assertThat(contactsViewModel.getContacts(), notNullValue());
    }
}