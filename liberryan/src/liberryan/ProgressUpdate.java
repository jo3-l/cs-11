package liberryan;

import java.time.Instant;

// Represents some progress towards finishing a book.
public class ProgressUpdate {
    private final Instant time;
    private final int pagesRead;

    // Telescopic constructor that defaults the time to the current instant, so clients don't have to provide it every time.
    public ProgressUpdate(int pagesRead) {
        this(Time.currentInstant(), pagesRead);
    }

    public ProgressUpdate(Instant time, int pagesRead) {
        this.time = time;
        this.pagesRead = pagesRead;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the time at which this progress update was created.
    public Instant getTime() {
        return time;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the number of pages read.
    public int getPagesRead() {
        return pagesRead;
    }
}
