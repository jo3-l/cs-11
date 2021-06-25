package liberryan;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

// Tracks user reading activity.
public class ActivityTracker {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.CANADA)
            .withZone(ZoneId.systemDefault());

    private final BookDatabase database;

    public ActivityTracker(BookDatabase database) {
        this.database = database;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Gets the recent activity of the user, sorted by the date it was done. More recent activity comes first.
    // Reading more pages of a book / rating a book are the two types of activity.
    public List<ActivityLogEntry> getRecentActivity() {
        List<ActivityLogEntry> entries = new ArrayList<>();
        for (Book book : database.getAllBooks()) {
            if (book.getRating() != null) {
                String message = "Rated the book '" + book.getName() + "' " + book.getRating().getRating()
                        + " stars (" + formatter.format(book.getRating().getTime()) + ")";
                entries.add(new ActivityLogEntry(message, book.getRating().getTime()));
            }

            for (ProgressUpdate update : book.getProgressUpdates()) {
                String message = "Read " + update.getPagesRead() + " pages of '" + book.getAuthor()
                        + "' (" + formatter.format(update.getTime()) + ")";
                entries.add(new ActivityLogEntry(message, update.getTime()));
            }
        }

        entries.sort(Comparator.comparing(ActivityLogEntry::getCreatedAt).reversed());
        return entries;
    }

    // Requires: Instant startTime, Instant endTime.
    // Modifies: Nothing.
    // Effects: Determines how many pages a person read between startTime and endTime, and groups the result
    // by weekday. Returns a list of reading activity entries sorted by their weekday.
    public List<ReadingActivityEntry> getReadingActivityByWeekdayBetween(Instant startTime,
                                                                         Instant endTime) {
        List<ReadingActivityEntry> entries = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            ReadingActivityEntry entry = new ReadingActivityEntry(dayOfWeek, 0);
            entries.add(entry);
        }

        for (Book book : database.getAllBooks()) {
            for (ProgressUpdate update : book.getProgressUpdates()) {
                if (update.getTime().isBefore(startTime) || update.getTime().isAfter(endTime)) continue;
                // ChronoField.DAY_OF_WEEK returns the day of week as a 1-based integer.
                // We want 0-based in order to index entries.
                int offset = update.getTime().atZone(ZoneId.systemDefault()).getDayOfWeek().getValue() - 1;
                entries.get(offset).incrementPagesReadBy(update.getPagesRead());
            }
        }

        return entries;
    }

    // An entry in the activity log.
    public static class ActivityLogEntry {
        private final String message;
        private final Instant createdAt;

        public ActivityLogEntry(String message, Instant createdAt) {
            this.message = message;
            this.createdAt = createdAt;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the formatted message for this activity log entry.
        public String getMessage() {
            return message;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the time that this entry was created at.
        public Instant getCreatedAt() {
            return createdAt;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    // Tracks reading activity for a given weekday.
    public static class ReadingActivityEntry {
        private final DayOfWeek dayOfWeek;
        private int pagesRead;

        public ReadingActivityEntry(DayOfWeek dayOfWeek, int pagesRead) {
            this.dayOfWeek = dayOfWeek;
            this.pagesRead = pagesRead;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the day of the week that this entry is for.
        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        // Requires: int n.
        // Modifies: this, pagesRead
        // Effects: Increments the number of pages read by N.
        public void incrementPagesReadBy(int n) {
            this.pagesRead += n;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the number of pages read on this weekday.
        public int getPagesRead() {
            return pagesRead;
        }

        @Override
        public String toString() {
            return getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CANADA) + " - Read " + pagesRead + " pages";
        }
    }
}
