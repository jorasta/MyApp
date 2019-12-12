package by.a1.popov.homework2app;

import java.util.ArrayList;

public class SingletoneObserve {


    interface ContactsListener{
        void onContactsChange(ContactRecord contactRec, int position);
        void onContactAdd(ContactRecord contactRec);
        void onContactDel(int position);
    }

    private ArrayList<ContactsListener> listeners = new ArrayList<ContactsListener>();

    private static SingletoneObserve instance;

    private SingletoneObserve() {}


    public static  SingletoneObserve getInstance(){
        if (instance == null) {
            instance = new SingletoneObserve();
        }
        return instance;
    }

    public void notifyContactAdd(final ContactRecord contactRec){
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactAdd(contactRec);
            }
        }
    }

    public void notifyContactsChange(final ContactRecord contactRec, final int position){
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactsChange(contactRec,position );
            }
        }
    }

    public void notifyContactDel(final int position){
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactDel(position );
            }
        }
    }

    public void subscribe(ContactsListener listener){
        if (listener != null ){
            listeners.add(listener);
        }
    }

    public void unsubscribe(ContactsListener listener){
        if (listener != null ){
            listeners.remove(listener);
        }
    }


}
