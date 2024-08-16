package com.patrik.orders.di;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.patrik.orders.AppConfig;
import com.patrik.orders.api.ApiService;
import com.patrik.orders.db.OrdersDao;
import com.patrik.orders.db.OrdersDb;
import com.patrik.orders.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    ApiService provideApiService() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ApiService.class);
    }

    @Singleton
    @Provides
    OrdersDb provideDb(Application app) {
        return Room.databaseBuilder(app, OrdersDb.class, "orders.db")
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    @Singleton
    @Provides
    OrdersDao provideOrdersDao(OrdersDb db) {
        return db.ordersDao();
    }
}