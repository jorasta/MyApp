package by.a1.popov.homework7910app.repo.DBSrcs;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contacts.class}, version = 1, exportSchema = false)
public abstract class ContactsDB extends RoomDatabase {
    public abstract ContactsDAO contactsDAO();
}