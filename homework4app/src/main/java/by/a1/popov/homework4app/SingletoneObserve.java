package by.a1.popov.homework4app;

import java.util.ArrayList;

import by.a1.popov.homework4app.DBSrcs.Contacts;

public class SingletoneObserve {

    interface ContactsListener {
        void onContactsChange(Contacts contactRec, int position);
        void onContactAdd(Contacts contactRec);
        void onContactDel(Contacts contactRec, int position);
    }

    private ArrayList<ContactsListener> listeners = new ArrayList<ContactsListener>();

    private static SingletoneObserve instance;

    private SingletoneObserve() {
    }

    public static SingletoneObserve getInstance() {
        if (instance == null) {
            instance = new SingletoneObserve();
        }
        return instance;
    }

    public void notifyContactAdd(final Contacts contactRec) {
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactAdd(contactRec);
            }
        }
    }

    public void notifyContactsChange(final Contacts contactRec, final int position) {
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactsChange(contactRec, position);
            }
        }
    }

    public void notifyContactDel(final Contacts contactRec, final int position) {
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactDel(contactRec, position);
            }
        }
    }

    public void subscribe(ContactsListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(ContactsListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }


}
