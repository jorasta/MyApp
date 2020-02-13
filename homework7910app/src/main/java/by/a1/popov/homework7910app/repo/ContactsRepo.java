package by.a1.popov.homework7910app.repo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import by.a1.popov.homework7910app.App;
import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;
import by.a1.popov.homework7910app.repo.DBSrcs.ContactsDAO;

public class ContactsRepo {

    private ContactsDAO contactsDAO;

    public ContactsRepo() {
        contactsDAO = App.getInstance().getDatabase().contactsDAO();
    }

    public CompletableFuture<List<Contacts>> getAllContacts() {
        return CompletableFuture.supplyAsync(() -> contactsDAO.getAllContacts());
    }

    public CompletableFuture<Contacts> getContactById(long id) {
        return CompletableFuture.supplyAsync(() -> contactsDAO.getContactById(id));
    }

    public CompletableFuture<Void> deleteContact(Contacts contact) {
        return CompletableFuture.supplyAsync(() -> {
            contactsDAO.delete(contact);
            return null;
        });
    }

    public CompletableFuture<Void> insertContact(Contacts contact) {
        return CompletableFuture.supplyAsync(() -> {
            contactsDAO.insert(contact);
            return null;
        });
    }

    public CompletableFuture<Void> updateContact(Contacts contact) {
        return CompletableFuture.supplyAsync(() -> {
            contactsDAO.update(contact);
            return null;
        });
    }
}
