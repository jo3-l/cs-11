import liberryan.Book;
import liberryan.Genre;
import liberryan.ProgressUpdate;
import liberryan.Time;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

// TODO: test getPagesReadBetween
public class BookTests {
    @Test
    public void testConstructBook() {
        // regression test for an error that crept in when progressUpdates = null, a perfectly valid input,
        // which errored due to computeTotalPagesRead() assuming that it was non-null.
        // Now, it should default to an empty list if its value was null.
        try {
            Book __ = new Book(null, Genre.MYSTERY, "abc", "bcd", 5, null, null);
        } catch (Exception unused) {
            fail("Got an exception where none was expected.");
        }
    }

    @Test
    public void testTotalPagesReadCalculation() {
        // total pages read is computed based off all progress updates concerning this book.
        List<ProgressUpdate> updates = new ArrayList<>();
        updates.add(new ProgressUpdate(5)); // read 5 pages
        updates.add(new ProgressUpdate(10)); // read 10 more pages

        Book book = new Book(updates, Genre.MYSTERY, "abc", "bcd", 20, null, null);
        assertEquals(15, book.getCurrentPage());
    }

    @Test
    public void testTotalPagesReadCalculationWhenProgressUpdatesIsNull() {
        Book book = new Book(null, Genre.MYSTERY, "abc", "bcd", 5, null, null);
        assertEquals(0, book.getCurrentPage());
    }

    @Test
    public void testTotalPagesReadCalculationWhenProgressUpdatesIsEmptyList() {
        // should have the same effect as above test.
        List<ProgressUpdate> updates = new ArrayList<>();
        Book book = new Book(updates, Genre.MYSTERY, "abc", "bcd", 5, null, null);
        assertEquals(0, book.getCurrentPage());
    }

    @Test
    // we test this getter method as it contains a bit more logic than simply setting some field.
    public void testSetCurrentPageOnce() {
        // for deterministic tests, use a fixed time.
        Instant currentTime = Instant.parse("2007-12-03T10:15:30.00Z");
        Time.useFixedInstant(currentTime);

        Book book = new Book(null, Genre.MYSTERY, "abc", "bcd", 5, null, null);
        book.setCurrentPage(10);
        assertEquals(10, book.getCurrentPage());

        // make sure a progress update was added with the correct value.
        List<ProgressUpdate> updates = book.getProgressUpdates();
        assertFalse(updates.isEmpty());
        assertEquals(1, updates.size());

        ProgressUpdate update = updates.get(0);
        assertEquals(currentTime, update.getTime());
        assertEquals(10, update.getPagesRead());
    }

    @Test
    public void testSetCurrentPageSeveralTimes() {
        // for deterministic tests, use a fixed time.
        Instant currentTime = Instant.parse("2009-12-03T10:15:30.00Z");
        Time.useFixedInstant(currentTime);

        Book book = new Book(null, Genre.MYSTERY, "abc", "bcd", 5, null, null);
        book.setCurrentPage(15);
        assertEquals(15, book.getCurrentPage());

        // make sure a progress update was added with the correct value.
        List<ProgressUpdate> updates = book.getProgressUpdates();
        assertFalse(updates.isEmpty());
        assertEquals(1, updates.size());

        ProgressUpdate update = updates.get(0);
        assertEquals(currentTime, update.getTime());
        assertEquals(15, update.getPagesRead());

        // now, set it again.
        book.setCurrentPage(20);
        assertEquals(20, book.getCurrentPage());

        // make sure another progress update was added.
        // the pages read should only be 5, not 20, since we were already at page 15 before.
        assertFalse(updates.isEmpty());
        assertEquals(2, updates.size());

        update = updates.get(1);
        assertEquals(currentTime, update.getTime());
        assertEquals(5, update.getPagesRead());
    }
}
