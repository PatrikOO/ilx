package com.patrik.orders.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.patrik.orders.util.LiveDataCallAdapterFactory;
import com.patrik.orders.vo.Contact;
import com.patrik.orders.vo.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.patrik.orders.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ApiServiceTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ApiService apiService;

    private MockWebServer mockWebServer;


    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        apiService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ApiService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getContacts() throws IOException, InterruptedException {
        enqueueResponse("contacts.json");

        List<Contact> contacts = getValue(apiService.getContacts()).body.items;
        assertThat(contacts.size(), is(511));

        Contact contact = contacts.get(0);
        assertThat(contact.id, is("4808601562513408"));
        assertThat(contact.name, is("adasdd"));
        assertThat(contact.phone, is("12323"));
        assertThat(contact.pictureUrl, is("profile6.jpg"));

    }

    @Test
    public void getDetail() throws IOException, InterruptedException {
        enqueueResponse("detail.json");
        List<Item> items = getValue(apiService.getDetail("foo")).body.items;
        assertThat(items.size(), is(8));

        Item item = items.get(0);
        assertThat(item.getId(), is("5629499534213120"));
        assertThat(item.name, is("Notebook"));
        assertThat(item.count, is(19));
    }


    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }
}
