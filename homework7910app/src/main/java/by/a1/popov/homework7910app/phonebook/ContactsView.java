package by.a1.popov.homework7910app.phonebook;

import java.util.List;

import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;

public interface ContactsView {
    void showContacts(List<Contacts> contactsList);
    void getContacts();
}
