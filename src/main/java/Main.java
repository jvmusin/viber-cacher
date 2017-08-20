import db.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Main implements Callable<Void> {

    private final Path jarPath = getJarPath();
    private final ViberDB viber = new ViberDB(jarPath.resolve("viber.db").toString());
    private final LocalDB local = new LocalDB(jarPath.resolve("local.db").toString());
    private final MessageToStringConverter converter = new MessageToStringConverter();

    @Override
    public Void call() throws Exception {
        local.update(viber);

        Path history = jarPath.resolve("history");
        if (!Files.exists(history)) Files.createDirectory(history);

        Map<Contact, List<SuperMessage>> byUser = loadAllMessages();

        byUser.forEach((contact, messages) -> {
            try {
                String separator = System.lineSeparator();
                String toWrite = messages.stream().map(converter::convert)
                        .collect(Collectors.joining(separator + separator));

                Files.write(history.resolve(contact.getNumber() + ".txt"), toWrite.getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return null;
    }

    private static Path getJarPath() {
        try {
            URI uri = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            return Paths.get(uri).getParent();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Map<Contact, List<SuperMessage>> loadAllMessages() {
        return local.getAllMessages().stream()
                .map(this::createSuperMessage)
                .sorted(Comparator.comparingInt(m -> m.getEvent().getEventId()))
                .collect(Collectors.groupingBy(SuperMessage::getContact));
    }

    private SuperMessage createSuperMessage(Message message) {
        Event event = local.getEvent(message.getEventId());
        Contact contact = local.getContact(event.getContactId());
        return new SuperMessage(message, event, contact);
    }

    public static void main(String[] args) throws Exception {
        new Main().call();
    }
}