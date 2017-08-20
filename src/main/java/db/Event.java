package db;

public class Event {
    private final int eventId;
    private final int timeStamp;
    private final int direction;
    private final int type;
    private final int chatId;
    private final int contactId;

    public Event(int eventId, int timeStamp, int direction, int type, int chatId, int contactId) {
        this.eventId = eventId;
        this.timeStamp = timeStamp;
        this.direction = direction;
        this.type = type;
        this.chatId = chatId;
        this.contactId = contactId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public int getDirection() {
        return direction;
    }

    public int getType() {
        return type;
    }

    public int getChatId() {
        return chatId;
    }

    public int getContactId() {
        return contactId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", timeStamp=" + timeStamp +
                ", direction=" + direction +
                ", type=" + type +
                ", chatId=" + chatId +
                ", contactId=" + contactId +
                '}';
    }
}