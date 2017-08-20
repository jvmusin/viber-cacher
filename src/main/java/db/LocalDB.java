package db;

import java.util.Collection;

public class LocalDB extends ViberDB {
    public LocalDB(String dbPath) {
        super(dbPath);
    }

    private void createTables() {
        execute("CREATE TABLE IF NOT EXISTS Contact(ContactID INTEGER PRIMARY KEY, Name TEXT, Number TEXT, ClientName TEXT)");
        execute("CREATE TABLE IF NOT EXISTS Messages(EventID INTEGER PRIMARY KEY, Body TEXT)");
        execute("CREATE TABLE IF NOT EXISTS Events(EventId INTEGER PRIMARY KEY, TimeStamp INTEGER, Direction INTEGER, Type INTEGER, ChatID INTEGER, ContactID INTEGER)");
    }

    private void updateContacts(Collection<Contact> contacts) {
        String query = "INSERT OR REPLACE INTO Contact(ContactID, Name, Number, ClientName) VALUES (?, ?, ?, ?)";
        contacts.forEach(contact -> executeWithPreparedStatement(query, statement -> {
            statement.setInt(1, contact.getContactId());
            statement.setString(2, contact.getName());
            statement.setString(3, contact.getNumber());
            statement.setString(4, contact.getClientName());
        }));
    }

    private void updateMessages(Collection<Message> messages) {
        String query = "INSERT OR IGNORE INTO Messages(EventID, Body) VALUES (?, ?)";
        messages.forEach(message -> executeWithPreparedStatement(query, statement -> {
            statement.setInt(1, message.getEventId());
            statement.setString(2, message.getBody());
        }));
    }

    private void updateEvents(Collection<Event> events) {
        String query = "INSERT OR IGNORE INTO Events(EventId, TimeStamp, Direction, Type, ChatID, ContactID) VALUES (?, ?, ?, ?, ?, ?)";
        events.forEach(event -> executeWithPreparedStatement(query, statement -> {
            statement.setInt(1, event.getEventId());
            statement.setInt(2, event.getTimeStamp());
            statement.setInt(3, event.getDirection());
            statement.setInt(4, event.getType());
            statement.setInt(5, event.getChatId());
            statement.setInt(6, event.getContactId());
        }));
    }

    public void update(ViberDB viberDB) {
        createTables();
        updateContacts(viberDB.loadAllContactsFromDisc().values());
        updateMessages(viberDB.loadAllMessagesFromDisc().values());
        updateEvents(viberDB.loadAllEventsFromDisc().values());
    }
}