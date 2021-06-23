package liberryan;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Represents a book.
public class Book {
    private final List<ProgressUpdate> progressUpdates;
    private int totalPagesRead = 0;
    private Genre genre;
    private String name;
    private String author;
    private int pageCount;
    private Rating rating;
    private LocalDate publishedDate;

    public Book(List<ProgressUpdate> progressUpdates,
                Genre genre,
                String name,
                String author,
                int pageCount,
                Rating rating,
                LocalDate publishedDate) {
        // default progressUpdates to an empty list.
        this.progressUpdates = progressUpdates == null ? new ArrayList<>() : progressUpdates;
        this.totalPagesRead = computeTotalPagesRead();
        this.genre = genre;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.rating = rating;
        this.publishedDate = publishedDate;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Computes the total number of pages read by summing up the pages read of every progress update so far.
    private int computeTotalPagesRead() {
        return progressUpdates.stream().mapToInt(ProgressUpdate::getPagesRead).sum();
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the total number of pages read in this book so far.
    public int getCurrentPage() {
        return totalPagesRead;
    }

    // Requires: int page.
    // Modifies: this, progressUpdates, totalPagesRead.
    // Effects: Sets the current page in the book. Internally, the cached number of total pages read is updated and
    // a progress update is added.
    public void setCurrentPage(int page) {
        ProgressUpdate update = new ProgressUpdate(page - totalPagesRead);
        totalPagesRead = page;
        progressUpdates.add(update);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the list of progress updates.
    public List<ProgressUpdate> getProgressUpdates() {
        return progressUpdates;
    }

    // Requires: Instant startTime, Instant endTime.
    // Modifies: Nothing.
    // Effects: Returns the total number of pages read in this book between startTime and endTime.
    public int getPagesReadBetween(Instant startTime, Instant endTime) {
        int firstPage = progressUpdates
                .stream()
                .filter(update -> update.getTime().isBefore(startTime))
                .mapToInt(ProgressUpdate::getPagesRead)
                .sum();
        int lastPage = progressUpdates
                .stream()
                .filter(update -> update.getTime().isBefore(endTime))
                .mapToInt(ProgressUpdate::getPagesRead)
                .sum();
        return lastPage - firstPage;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the genre of the book.
    public Genre getGenre() {
        return genre;
    }

    // Requires: Genre genre.
    // Modifies: this, genre.
    // Effects: Sets the genre of the book.
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the name of the book.
    public String getName() {
        return name;
    }

    // Requires: String name.
    // Modifies: this, name.
    // Effects: Sets the name of the book.
    public void setName(String name) {
        this.name = name;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the author of this book.
    public String getAuthor() {
        return author;
    }

    // Requires: String author.
    // Modifies: this, author.
    // Effects: Sets the author of this book.
    public void setAuthor(String author) {
        this.author = author;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the page count of this book.
    public int getPageCount() {
        return pageCount;
    }

    // Requires: int pageCount.
    // Modifies: this, pageCount.
    // Effects: Sets the page count of this book.
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the rating of this book.
    public Rating getRating() {
        return rating;
    }

    // Requires: int rating.
    // Modifies: this, rating.
    // Effects: Sets the rating of this book.
    public void setRating(int rating) {
        this.rating = new Rating(rating);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns the published date of this book.
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    // Requires: LocalDate publishedDate.
    // Modifies: this, publishedDate.
    // Effects: Sets the published date of this book.
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    // An enumeration of all publicly exposed fields on a book.
    public enum Field {
        PROGRESS_UPDATES,
        GENRE,
        NAME,
        AUTHOR,
        PAGE_COUNT,
        RATING,
        PUBLISHED_DATE,
    }
}
