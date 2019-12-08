package by.a1.popov.homework2app;

import java.util.ArrayList;

public class SingletoneObserve {


    interface ContactsListener{
        void onContactsChange(ArrayList<ContactRecord> contactRecs);
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

    public void notifyContactsChange(final ArrayList<ContactRecord> contactRecs){
        if (!listeners.isEmpty()) {
            for (ContactsListener listener : listeners) {
                listener.onContactsChange(contactRecs);
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
