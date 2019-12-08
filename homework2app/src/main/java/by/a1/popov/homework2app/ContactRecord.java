package by.a1.popov.homework2app;

/** Represents a contact data.
 * name - Name of contact
 * contact - Phone number or Email
 * icon - ResID of icon
 */

public class ContactRecord {

    private String name;
    private String contact;
    private int icon;

        public  ContactRecord(String name, String contact, int icon){
        setName(name);
        setIcon(icon);
        setContact(contact);
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getContact() {
        return contact;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setIcon(int icon) {
        this.icon = icon;
    }

    private void setContact(String contact) {
        this.contact = contact;
    }
}
