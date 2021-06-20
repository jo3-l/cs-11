package liberryan;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

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
        this.progressUpdates = progressUpdates;
        this.totalPagesRead = computeTotalPagesRead();
        this.genre = genre;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.rating = rating;
        this.publishedDate = publishedDate;
    }

    private int computeTotalPagesRead() {
        return progressUpdates.stream().mapToInt(ProgressUpdate::getPagesRead).sum();
    }

    public int getCurrentPage() {
        return totalPagesRead;
    }

    public void setCurrentPage(int page) {
        totalPagesRead += page;
        ProgressUpdate update = new ProgressUpdate(totalPagesRead);
        progressUpdates.add(update);
    }

    public List<ProgressUpdate> getProgressUpdates() {
        return progressUpdates;
    }

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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = new Rating(rating);
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

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
