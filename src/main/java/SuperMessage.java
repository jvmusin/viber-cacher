import db.Contact;
import db.Event;
import db.Message;

public class SuperMessage {
    private final Message message;
    private final Event event;
    private final Contact contact;

    public SuperMessage(Message message, Event event, Contact contact) {
        this.message = message;
        this.event = event;
        this.contact = contact;
    }

    public String getBody() {
        return message.getBody();
    }

    public Event getEvent() {
        return event;
    }

    public Contact getContact() {
        return contact;
    }
}
