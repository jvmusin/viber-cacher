import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.StringJoiner;

class MessageToStringConverter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    String convert(SuperMessage message) {
        String sender;
        if (message.getEvent().getDirection() == 1) {
            sender = "Me";
        } else {
            if (message.getContact().getClientName() != null) sender = message.getContact().getClientName();
            else if (message.getContact().getName() != null) sender = message.getContact().getName();
            else sender = message.getContact().getNumber();
        }
        sender += format(message.getEvent().getTimeStamp());
        sender += ":";

        StringJoiner sj = new StringJoiner("\n");
        sj.add(sender);

        String body = message.getBody();
        if (body != null)
            for (String line : body.split(System.lineSeparator()))
                sj.add("\t" + line);
        else sj.add("\t---EMPTY--- (May be picture or something like this)");

        return sj.toString();
    }

    private String format(int epochSeconds) {
        Instant instant = Instant.ofEpochMilli(epochSeconds * 1000L);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return format(formatter.format(dateTime));
    }

    private String format(Object s) {
        return " (" + s + ")";
    }
}