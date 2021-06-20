package liberryan;

import java.time.Instant;

public class ProgressUpdate {
    private final Instant time;
    private final int pagesRead;

    public ProgressUpdate(int pagesRead) {
        this(Time.currentInstant(), pagesRead);
    }

    public ProgressUpdate(Instant time, int pagesRead) {
        this.time = time;
        this.pagesRead = pagesRead;
    }

    public Instant getTime() {
        return time;
    }

    public int getPagesRead() {
        return pagesRead;
    }
}
