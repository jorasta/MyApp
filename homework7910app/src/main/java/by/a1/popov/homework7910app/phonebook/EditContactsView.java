package by.a1.popov.homework7910app.phonebook;

import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;

public interface EditContactsView {
    void showContacts();
    void showContactData(Contacts contact);
}
