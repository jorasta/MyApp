package by.a1.popov.homework7910app.phonebook;

import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;

public interface ContactsPresenter {

    void getAllContacts();
    void getContactById(long id);
    void addContact(Contacts contact);
    void deleteContact(Contacts contact);
    void updateContact(Contacts contact);
    void detachView(ContactsView view);
    void detachView(EditContactsView view);
    void detachView(AddContactsView view);
}
