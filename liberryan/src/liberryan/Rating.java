package liberryan;

import java.time.Instant;

public class Rating {
    private final Instant time;
    private final int rating;

    public Rating(int rating) {
        this(Time.currentInstant(), rating);
    }

    public Rating(Instant time, int rating) {
        this.time = time;
        this.rating = rating;
    }

    public Instant getTime() {
        return time;
    }

    public int getRating() {
        return rating;
    }
}
