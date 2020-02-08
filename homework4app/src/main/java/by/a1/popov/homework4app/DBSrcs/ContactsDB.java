package by.a1.popov.homework4app.DBSrcs;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contacts.class}, version = 1)
public abstract class ContactsDB extends RoomDatabase {
    public abstract ContactsDAO contactsDAO();
}