package by.a1.popov.homework7910app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Room;

import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;
import by.a1.popov.homework7910app.repo.DBSrcs.ContactsDAO;
import by.a1.popov.homework7910app.repo.DBSrcs.ContactsDB;

public class ContactsContentProvider extends ContentProvider {

    private static final String AUTHORITY = "by.a1.popov.homework7910.contentprovider";
    private static final String TABLE_CONTACTS = "contacts";

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private ContactsDAO contactsDAO;
    private static final int CODE_CONTACTS_DIR = 100;
    private static final int CODE_CONTACTS_ITEM = 101;

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_CONTACTS, CODE_CONTACTS_DIR);
        uriMatcher.addURI(AUTHORITY, TABLE_CONTACTS + "/*", CODE_CONTACTS_ITEM);
    }

    public ContactsContentProvider() {
    }

    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            contactsDAO = Room.databaseBuilder(
                    getContext().getApplicationContext(),
                    ContactsDB.class,
                    "contacts.db")
                    .build()
                    .contactsDAO();
            return true;
        }
        return false;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CODE_CONTACTS_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_CONTACTS;
            case CODE_CONTACTS_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_CONTACTS;
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == CODE_CONTACTS_DIR || uriMatcher.match(uri) == CODE_CONTACTS_ITEM) {
            if (uriMatcher.match(uri) == CODE_CONTACTS_DIR) {
                return contactsDAO.getAllContactsCursor();
            } else {
                return contactsDAO.getContactCursorById(ContentUris.parseId(uri));
            }
        } else {
            throw new IllegalArgumentException("Unknown URI:" + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case CODE_CONTACTS_DIR:
                long id = contactsDAO.insertByContentProvider(
                        new Contacts(values.getAsString(Contacts.CONTACT_NAME),
                                values.getAsString(Contacts.CONTACT_DATA),
                                values.getAsInteger(Contacts.CONTACT_TYPE)));
                if (id != -1) {
                    return ContentUris.withAppendedId(uri, id);
                } else {
                    return null;
                }
            case CODE_CONTACTS_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CODE_CONTACTS_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_CONTACTS_ITEM:
                return contactsDAO.updateByContactProvider(
                        new Contacts(values.getAsString(Contacts.CONTACT_NAME),
                                values.getAsString(Contacts.CONTACT_DATA),
                                values.getAsInteger(Contacts.CONTACT_TYPE)));
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CODE_CONTACTS_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot delete without ID");
            case CODE_CONTACTS_ITEM:
                return contactsDAO.delContactById(ContentUris.parseId(uri));
            default:
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
    }
}
