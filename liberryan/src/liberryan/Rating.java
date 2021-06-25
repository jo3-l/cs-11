package liberryan;

import java.time.Instant;

// A rating for a book.
public class Rating {
    private final Instant time;
    private final int rating;

    // Telescopic constructor defaulting time to the current time, so clients don't have to provide it each time.
    public Rating(int rating) {
        this(Time.currentInstant(), rating);
    }

    public Rating(Instant time, int rating) {
        this.time = time;
        this.rating = rating;
    }

    // Getters

    public Instant getTime() {
        return time;
    }

    public int getRating() {
        return rating;
    }
}
