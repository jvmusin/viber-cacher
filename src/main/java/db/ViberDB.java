package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViberDB extends BaseDB {
    public ViberDB(String dbPath) {
        super(dbPath);
    }

    private final Map<Integer, Message> messages = loadAllMessagesFromDisc();
    private final Map<Integer, Event> events = loadAllEventsFromDisc();
    private final Map<Integer, Contact> contacts = loadAllContactsFromDisc();

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    Map<Integer, Message> loadAllMessagesFromDisc() {
        String query = "SELECT EventID, Body FROM Messages";
        return loadAllFromDisc(query, rs -> {
            int eventID = rs.getInt("EventID");
            String body = rs.getString("Body");
            return new Message(eventID, body);
        }, Message::getEventId);
    }

    Map<Integer, Event> loadAllEventsFromDisc() {
        String query = "SELECT EventId, TimeStamp, Direction, Type, ChatID, ContactID FROM Events";
        return loadAllFromDisc(query, rs -> {
            int eventID = rs.getInt("EventID");
            int timeStamp = rs.getInt("TimeStamp");
            int direction = rs.getInt("Direction");
            int type = rs.getInt("Type");
            int chatId = rs.getInt("ChatID");
            int contactID = rs.getInt("ContactID");
            return new Event(eventID, timeStamp, direction, type, chatId, contactID);
        }, Event::getEventId);
    }

    Map<Integer, Contact> loadAllContactsFromDisc() {
        String query = "SELECT ContactID, Name, Number, ClientName FROM Contact";
        return loadAllFromDisc(query, rs -> {
            int contactID = rs.getInt("ContactID");
            String name = rs.getString("Name");
            String number = rs.getString("Number");
            String clientName = rs.getString("ClientName");
            return new Contact(contactID, name, number, clientName);
        }, Contact::getContactId);
    }

    public Event getEvent(int id) {
        return events.get(id);
    }

    public Contact getContact(int id) {
        return contacts.get(id);
    }
}