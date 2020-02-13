package by.a1.popov.contentresolver;

public class Contacts {

    static final String AUTHORITY = "by.a1.popov.homework7910.contentprovider";
    static final String TABLE_CONTACTS = "contacts";

    static final String CONTACT_ID = "id";
    static final String CONTACT_NAME = "name";
    static final String CONTACT_DATA = "contact";
    static final String CONTACT_TYPE = "typeOfContact";


    private long id;
    private String name;
    private String contact;
    private int typeOfContact;

    Contacts(long id, String name, String contact, int typeOfContact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.typeOfContact = typeOfContact;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    int getTypeOfContact() {
        return typeOfContact;
    }

    public void setTypeOfContact(int typeOfContact) {
        this.typeOfContact = typeOfContact;
    }

    long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
