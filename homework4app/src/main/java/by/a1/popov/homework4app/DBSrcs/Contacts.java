package by.a1.popov.homework4app.DBSrcs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contacts {

    @PrimaryKey (autoGenerate = true)
    private long id;
    private String name;
    private String contact;
    private int typeOfContact;

    public Contacts(String name, String contact, int typeOfContact) {
        this.name = name;
        this.contact = contact;
        this.typeOfContact = typeOfContact;
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
