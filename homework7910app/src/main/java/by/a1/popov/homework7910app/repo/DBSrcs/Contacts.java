package by.a1.popov.homework7910app.repo.DBSrcs;

import android.content.ContentValues;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.File;

@Entity
@TypeConverters(Converters.class)
public class Contacts {

    public static final String CONTACT_ID = "id";
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_DATA = "contact";
    public static final String CONTACT_TYPE = "typeOfContact";

    @PrimaryKey (autoGenerate = true)
    private long id;
    private String name;
    private String contact;
    private int typeOfContact;
    private File photoFile;

    public Contacts(String name, String contact, int typeOfContact) {
        this.name = name;
        this.contact = contact;
        this.typeOfContact = typeOfContact;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(File photoFile) {
        this.photoFile = photoFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getTypeOfContact() {
        return typeOfContact;
    }

    public void setTypeOfContact(int typeOfContact) {
        this.typeOfContact = typeOfContact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
