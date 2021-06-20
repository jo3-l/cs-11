package liberryan.serialization;

import liberryan.ProgressUpdate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProgressUpdateSerializer {
    public static final char FIELD_SEPARATOR = 0xBAAA;
    public static final char SEPARATOR = 0xCAFE;

    public static String encodeMany(List<ProgressUpdate> updates) {
        StringBuilder buf = new StringBuilder();
        for (ProgressUpdate update : updates) {
            buf.append(encode(update)).append(SEPARATOR);
        }
        return buf.toString();
    }

    public static List<ProgressUpdate> decodeMany(String raw) {
        List<ProgressUpdate> updates = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            // If the current character is the field separator, then set the current field's value.
            if (c == SEPARATOR) {
                updates.add(decode(buf.toString()));
                // reset buffer
                buf.setLength(0);
            } else {
                // not a field separator, just add it to the end of the buffer
                buf.append(c);
            }
        }

        return updates;
    }

    public static String encode(ProgressUpdate update) {
        return update.getTime().toString()
                + FIELD_SEPARATOR
                + update.getPagesRead()
                + FIELD_SEPARATOR;
    }

    public static ProgressUpdate decode(String raw) {
        Instant time = null;
        int pagesRead = 0;

        String currentField = "time";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            // If the current character is the field separator, then set the current field's value.
            if (c == FIELD_SEPARATOR) {
                if (currentField.equals("time")) {
                    time = Instant.parse(buf.toString());
                    currentField = "pagesRead";
                } else {
                    pagesRead = Integer.parseInt(buf.toString());
                }

                // reset buffer
                buf.setLength(0);
            } else {
                // not a field separator, just add it to the end of the buffer for the current element
                buf.append(c);
            }
        }

        return new ProgressUpdate(time, pagesRead);
    }
}
