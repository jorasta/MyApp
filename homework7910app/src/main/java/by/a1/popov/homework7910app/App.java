package by.a1.popov.homework7910app;

import android.app.Application;

import androidx.room.Room;

import by.a1.popov.homework7910app.repo.DBSrcs.ContactsDB;

public class App extends Application {

    public static App instance;

    public static App getInstance() {
        return instance;
    }

    private ContactsDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, ContactsDB.class, "contacts.db")
//                .allowMainThreadQueries()
                .build();
    }

    public ContactsDB getDatabase() {
        return database;
    }
}