package com.reaver.testlab;

import android.app.Application;

import androidx.room.Room;

import com.reaver.testlab.room.account.AccountDatabase;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

///
/// Данный класс предотвратит пересоздание базы данных при запуске приложения.
///

public class App extends Application {

    public static App instance;

    private AccountDatabase database;

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AccountDatabase.class, "account")
                .allowMainThreadQueries()
                .build();

    }


    public static App getInstance() {
        return instance;
    }

    public AccountDatabase getDatabase() {
        return database;
    }
}