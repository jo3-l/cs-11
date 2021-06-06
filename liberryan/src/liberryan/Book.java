package liberryan;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private final List<ProgressSnapshot> progressSnapshots = new ArrayList<>();
    private Genre genre;
    private String name;
    private String author;
    private int pageCount;
    private Rating rating;
    private LocalDate publishedDate;

    public Book(Genre genre, String name, String author, int pageCount, Rating rating, LocalDate publishedDate) {
        this.genre = genre;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.rating = rating;
        this.publishedDate = publishedDate;
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

    public int getCurrentPage() {
        if (progressSnapshots.isEmpty()) return 0;
        ProgressSnapshot latestSnapshot = progressSnapshots.get(progressSnapshots.size() - 1);
        return latestSnapshot.getPagesRead();
    }

    public void setCurrentPage(int page) {
        ProgressSnapshot snapshot = new ProgressSnapshot(page);
        progressSnapshots.add(snapshot);
    }

    public int getPagesReadBetween(Instant startTime, Instant endTime) {
        // find the first snapshot made after startTime, then access its page number
        // if no such snapshot exists, simply default to 0
        int firstPage = progressSnapshots
                .stream()
                .filter(snapshot -> snapshot.getTime().isAfter(startTime))
                .findFirst()
                .map(ProgressSnapshot::getPagesRead)
                .orElse(0);

        // find the last snapshot made before endTime, then access its page number
        // if no such snapshot exists, default to the current page
        int lastPage = progressSnapshots
                .stream()
                .filter(snapshot -> snapshot.getTime().isBefore(endTime))
                .reduce((a, b) -> b) // get the last value in the stream through a reduction that always return the second value.
                .map(ProgressSnapshot::getPagesRead)
                .orElseGet(this::getCurrentPage);

        return lastPage - firstPage;
    }
}
