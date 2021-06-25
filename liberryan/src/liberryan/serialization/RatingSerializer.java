package liberryan.serialization;

import liberryan.Rating;

import java.time.Instant;

// Implementation of a decoder/encoder for ratings.
public class RatingSerializer {
    private static final char FIELD_SEPARATOR = 0xFACE;

    // Requires: Rating rating - rating to encode.
    // Modifies: Nothing.
    // Effects: Encodes the rating into a string. It may then be decoded back into its original form using
    // RatingSerializer.decode().
    //
    // The format is:
    //  time + field separator + rating number + field separator.
    public static String encode(Rating rating) {
        return rating.getTime().toString()
                + FIELD_SEPARATOR
                + rating.getRating()
                + FIELD_SEPARATOR;
    }

    // Requires: String raw - string to decode.
    // Modifies: Nothing.
    // Effects: Decodes the string into a rating object.
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
