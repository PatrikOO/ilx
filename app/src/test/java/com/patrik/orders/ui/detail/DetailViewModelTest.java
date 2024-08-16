package com.patrik.orders.ui.detail;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.patrik.orders.db.OrdersDb;
import com.patrik.orders.repository.OrdersRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class DetailViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private DetailViewModel detailViewModel;
    private OrdersDb ordersDb;

    private OrdersRepository ordersRepository;

    @Before
    public void setup() {
        ordersDb = mock(OrdersDb.class);
        ordersRepository = mock(OrdersRepository.class);
        detailViewModel = new DetailViewModel(ordersDb);
    }

    @Test
    public void testNull() {
        assertThat(detailViewModel.getDetail(), notNullValue());
        assertThat(detailViewModel.getContact(), notNullValue());
        verify(ordersRepository, never()).loadDetail(anyString());
    }

}