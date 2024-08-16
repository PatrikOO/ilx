package com.patrik.orders.ui.addcontact;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.Context;

import com.patrik.orders.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddContactViewModelTest {
    private static final String ERROR_NAME = "Name must be at least 5 characters in length!";
    private static final String ERROR_PHONE = "Phone must be at least 5 characters in length!";

    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();
    private AddContactViewModel addContactViewModel;

    @Mock
    private Context mockContext;

    @Before
    public void setup() {
        addContactViewModel = new AddContactViewModel();

        when(mockContext.getString(R.string.error_name)).thenReturn(ERROR_NAME);
        when(mockContext.getString(R.string.error_phone)).thenReturn(ERROR_PHONE);
    }


    @Test
    public void errorText() {

        assertThat(addContactViewModel.getErrorText(mockContext, null, null),
                is(mockContext.getString(R.string.error_name) + "\n" + mockContext.getString(R.string.error_phone)));

        assertThat(addContactViewModel.getErrorText(mockContext, "", ""),
                is(mockContext.getString(R.string.error_name) + "\n" + mockContext.getString(R.string.error_phone)));

        assertThat(addContactViewModel.getErrorText(mockContext, "1234", "1234"),
                is(mockContext.getString(R.string.error_name) + "\n" + mockContext.getString(R.string.error_phone)));

        assertThat(addContactViewModel.getErrorText(mockContext, "12345", "12345"),
                is(""));

        assertThat(addContactViewModel.getErrorText(mockContext, "1234", "12345"),
                is(mockContext.getString(R.string.error_name)));

        assertThat(addContactViewModel.getErrorText(mockContext, "12345", "1234"),
                is(mockContext.getString(R.string.error_phone)));
    }

}