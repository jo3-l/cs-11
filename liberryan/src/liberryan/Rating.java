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

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the time at which this rating was made.
    public Instant getTime() {
        return time;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the rating.
    public int getRating() {
        return rating;
    }
}
