package by.a1.popov.homework7910app.repo.DBSrcs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactsDAO {

    @Insert
    void insert(Contacts contact);

    @Delete
    void delete(Contacts contact);

    @Update
    void update(Contacts contact);

    @Query("SELECT * FROM contacts")
    List<Contacts> getAllContacts();

    @Query("SELECT * FROM contacts where id = :id")
    Contacts getContactById(long id);


}
