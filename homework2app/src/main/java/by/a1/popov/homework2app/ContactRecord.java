package by.a1.popov.homework2app;

import android.os.Parcel;
import android.os.Parcelable;

/** Represents a contact data.
 * name - Name of contact
 * contact - Phone number or Email
 * icon - ResID of icon
 */

public class ContactRecord implements Parcelable {

    private String name;
    private String contact;
    private int icon;

        public  ContactRecord(String name, String contact, int icon){
        setName(name);
        setIcon(icon);
        setContact(contact);
    }

    protected ContactRecord(Parcel in) {
        name = in.readString();
        contact = in.readString();
        icon = in.readInt();
    }

    public static final Creator<ContactRecord> CREATOR = new Creator<ContactRecord>() {
        @Override
        public ContactRecord createFromParcel(Parcel in) {
            return new ContactRecord(in);
        }

        @Override
        public ContactRecord[] newArray(int size) {
            return new ContactRecord[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(contact);
        out.writeInt(icon);
    }
}
