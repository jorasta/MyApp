package by.a1.popov.homework4app;

import android.app.Application;
import androidx.room.Room;
import by.a1.popov.homework4app.DBSrcs.ContactsDB;

public class App extends Application {

    public static App instance;

    private ContactsDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, ContactsDB.class, "contacts.db")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public ContactsDB getDatabase() {
        return database;
    }
}