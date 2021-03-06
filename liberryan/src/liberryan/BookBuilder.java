package liberryan;

import java.time.LocalDate;
import java.util.List;

// A builder for books.
public class BookBuilder {
    private List<ProgressUpdate> progressUpdates;
    private Genre genre;
    private String name;
    private String author;
    private int pageCount;
    private Rating rating;
    private LocalDate publishedDate;

    public void setProgressUpdates(List<ProgressUpdate> progressUpdates) {
        this.progressUpdates = progressUpdates;
    }

    // Setters
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Builds the book and returns the result.
    public Book build() {
        return new Book(progressUpdates, genre, name, author, pageCount, rating, publishedDate);
    }
}
