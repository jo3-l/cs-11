package liberryan.serialization;

import liberryan.ProgressUpdate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// Implementation of an encoder/decoder for progress updates.
public class ProgressUpdateSerializer {
    // The character used to separate individual fields of progress updates.
    public static final char FIELD_SEPARATOR = 0xBAAA;
    // The character used to separate encoded progress updates when encoding many progress updates at once.
    public static final char SEPARATOR = 0xCAFE;

    // Requires: List<ProgressUpdate> updates.
    // Modifies: Nothing.
    // Effects: Encodes all progress updates into a single string. It may then be decoded back into its original
    // form via ProgressUpdateSerializer.decodeMany().
    //
    // The format is:
    //  (progress update + separator)*
    public static String encodeMany(List<ProgressUpdate> updates) {
        StringBuilder buf = new StringBuilder();
        for (ProgressUpdate update : updates) {
            buf.append(encode(update)).append(SEPARATOR);
        }
        return buf.toString();
    }

    // Requires: String raw.
    // Modifies: Nothing.
    // Effects: Decodes the string back into a list of progress update objects.
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

    // Requires: ProgressUpdate update.
    // Modifies: Nothing.
    // Effects: Encodes the progress update into a string. It may then be decoded back into its original
    // form via ProgressUpdateSerializer.decode().
    //
    // The format is:
    //  time + field separator + pages read + field separator.
    public static String encode(ProgressUpdate update) {
        return update.getTime().toString()
                + FIELD_SEPARATOR
                + update.getPagesRead()
                + FIELD_SEPARATOR;
    }

    // Requires: String raw.
    // Modifies: Nothing.
    // Effects: Decodes the string back into a progress update object.
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
