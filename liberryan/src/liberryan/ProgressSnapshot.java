package liberryan;

import java.time.Instant;

public class ProgressSnapshot {
    private final Instant time;
    private final int pagesRead;

    public ProgressSnapshot(int pagesRead) {
        this(Instant.now(), pagesRead);
    }

    public ProgressSnapshot(Instant time, int pagesRead) {
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
