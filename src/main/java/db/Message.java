package db;

public class Message {
    private final int eventId;
    private final String body;

    public Message(int eventId, String body) {
        this.eventId = eventId;
        this.body = body;
    }

    public int getEventId() {
        return eventId;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "eventId=" + eventId +
                ", body='" + body + '\'' +
                '}';
    }
}