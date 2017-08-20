package db;

public class Contact {
    private final int contactId;
    private final String name;
    private final String number;
    private final String clientName;

    public Contact(int contactId, String name, String number, String clientName) {
        this.contactId = contactId;
        this.name = name;
        this.number = number;
        this.clientName = clientName;
    }

    public int getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return contactId == contact.contactId;
    }

    @Override
    public int hashCode() {
        return contactId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
