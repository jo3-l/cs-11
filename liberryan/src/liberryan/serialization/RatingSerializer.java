package liberryan.serialization;

import liberryan.Rating;

import java.time.Instant;

public class RatingSerializer {
    public static final char FIELD_SEPARATOR = 0xFACE;

    public static String encode(Rating rating) {
        return rating.getTime().toString()
                + FIELD_SEPARATOR
                + rating.getRating()
                + FIELD_SEPARATOR;
    }

    public static Rating decode(String raw) {
        Instant time = null;
        int rating = 0;

        String currentField = "time";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            // If the current character is the field separator, then set the current field's value.
            if (c == FIELD_SEPARATOR) {
                if (currentField.equals("time")) {
                    time = Instant.parse(buf.toString());
                    currentField = "rating";
                } else {
                    rating = Integer.parseInt(buf.toString());
                }

                // reset buffer
                buf.setLength(0);
            } else {
                // not a field separator, just add it to the end of the buffer for the current element
                buf.append(c);
            }
        }

        return new Rating(time, rating);
    }
}
