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

    // Requires: List<ProgressUpdate> progressUpdates.
    // Modifies: this, progressUpdates.
    // Effects: Sets the progress updates of this book.
    public void setProgressUpdates(List<ProgressUpdate> progressUpdates) {
        this.progressUpdates = progressUpdates;
    }

    // Requires: Genre genre.
    // Modifies: this, genre.
    // Effects: Sets the genre of this book.
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // Requires: String name.
    // Modifies: this, name.
    // Effects: Sets the name of this book.
    public void setName(String name) {
        this.name = name;
    }

    // Requires: String author.
    // Modifies: this, author.
    // Effects: Sets the author of this book.
    public void setAuthor(String author) {
        this.author = author;
    }

    // Requires: int pageCount.
    // Modifies: this, pageCount.
    // Effects: Sets the page count of this book.
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    // Requires: Rating rating.
    // Modifies: this, rating.
    // Effects: Sets the rating of this book.
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    // Requires: LocalDate publishedDate.
    // Modifies: this, publishedDate.
    // Effects: Sets the published date of this book.
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
