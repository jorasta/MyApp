package by.a1.popov.homework7910app.phonebook;

import com.google.android.gms.tasks.TaskExecutors;

import by.a1.popov.homework7910app.repo.ContactsRepo;
import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;

public class ContactsPresenterImpl implements ContactsPresenter {

    private ContactsView contactsView;
    private AddContactsView addContactsView;
    private EditContactsView editContactsView;
    private ContactsRepo contactsRepo = new ContactsRepo();

    ContactsPresenterImpl(ContactsView view) {
        this.contactsView = view;
    }
    ContactsPresenterImpl(AddContactsView view) {
        this.addContactsView = view;
    }
    ContactsPresenterImpl(EditContactsView view) {
        this.editContactsView = view;
    }

    @Override
    public void getAllContacts() {
        contactsRepo.getAllContacts()
                .thenAcceptAsync(contactsList -> contactsView.showContacts(contactsList), TaskExecutors.MAIN_THREAD);
    }

    @Override
    public void getContactById(long id) {
        contactsRepo.getContactById(id)
                .thenAcceptAsync(contacts -> editContactsView.showContactData(contacts),TaskExecutors.MAIN_THREAD);
    }

    @Override
    public void addContact(Contacts contact) {
        contactsRepo.insertContact(contact)
                .thenAcceptAsync(aVoid -> addContactsView.showContacts(),TaskExecutors.MAIN_THREAD);
    }

    @Override
    public void deleteContact(Contacts contact) {
        contactsRepo.deleteContact(contact)
                .thenAcceptAsync(aVoid -> editContactsView.showContacts(),TaskExecutors.MAIN_THREAD);
    }

    @Override
    public void updateContact(Contacts contact) {
        contactsRepo.updateContact(contact)
                .thenAcceptAsync(aVoid -> editContactsView.showContacts(),TaskExecutors.MAIN_THREAD);
    }
}
