package liberryan;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

// The statistics engine, responsible for keeping track of the most popular authors and genres and
// generating recommendations.
public class StatisticsEngine {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private final BookDatabase database;

    public StatisticsEngine(BookDatabase database) {
        this.database = database;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Sorts the unread books in the database by their rating. A book's rating is defined to be
    // its author's average rating plus its genre's average rating.
    public List<Book> getUnreadBooksSortedByRating() {
        List<GenreData> genres = getGenreDataSortedByPopularity();
        List<AuthorData> authors = getAuthorsSortedByPopularity();
        return database.getBooksInFolder(BookFolder.WANT_TO_READ).stream()
                .sorted(Comparator.comparingDouble(book -> {
                    double genreAverageRating = genres.stream()
                            .filter(data -> data.getGenre() == book.getGenre())
                            .findFirst()
                            .map(GenreData::getAverageRating)
                            .orElse(0d);
                    double authorAverageRating = authors.stream()
                            .filter(data -> data.getName().equals(book.getAuthor()))
                            .findFirst()
                            .map(AuthorData::getAverageRating)
                            .orElse(0d);
                    return authorAverageRating + genreAverageRating;
                }))
                .collect(Collectors.toList());
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns a collection of genre data sorted by popularity. A genre is more popular than another
    // if its average rating is greater than the other's. More popular genres come first.
    public List<GenreData> getGenreDataSortedByPopularity() {
        List<GenreData> genres = new ArrayList<>();
        // add placeholder data for all genres
        for (Genre genre : Genre.values()) {
            GenreData genreInfo = new GenreData(genre, 0, 0);
            genres.add(genreInfo);
        }

        for (Book book : database.getAllBooks()) {
            // only consider books with a rating
            if (book.getRating() == null) continue;

            // we constructed a list of genres above; to get the data for a specific genre we can just index
            // by its ordinal value.
            GenreData genreInfo = genres.get(book.getGenre().ordinal());
            genreInfo.incrementRatedBookCount();
            genreInfo.incrementTotalRatingBy(book.getRating().getRating());
        }

        genres.sort(Comparator.comparing(GenreData::getAverageRating).reversed());
        return genres;
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Returns a collection of author data sorted by popularity. An author is more popular than another
    // if their average rating is greater than the other. More popular authors come first.
    public List<AuthorData> getAuthorsSortedByPopularity() {
        // O(n^2) algorithm, but we don't learn hash tables in the course so, so be it
        List<AuthorData> authors = new ArrayList<>();
        for (Book book : database.getAllBooks()) {
            // only consider books with a rating
            if (book.getRating() == null) continue;

            // search for this author in the ones we already have
            Optional<AuthorData> opt = authors.stream()
                    .filter(data -> data.getName().equals(book.getAuthor()))
                    .findFirst();

            AuthorData data;
            // author was not seen before, add them.
            if (!opt.isPresent()) {
                data = new AuthorData(book.getAuthor(), 0, 0);
                authors.add(data);
            } else {
                data = opt.get(); // unwrap the Optional
            }

            data.incrementRatedBookCount();
            data.incrementTotalRatingBy(book.getRating().getRating());
        }

        authors.sort(Comparator.comparing(AuthorData::getAverageRating).reversed());
        return authors;
    }

    // Data regarding an author.
    public static class AuthorData extends TracksRatings {
        private final String name;

        public AuthorData(String name, int totalRating, int ratedBookCount) {
            this.name = name;
            this.totalRating = totalRating;
            this.ratedBookCount = ratedBookCount;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the name of the author this information is for.
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name + " | Average rating of " + decimalFormat.format(getAverageRating());
        }
    }

    // Data regarding a genre.
    public static class GenreData extends TracksRatings {
        private final Genre genre;

        public GenreData(Genre genre, int totalRating, int ratedBookCount) {
            this.genre = genre;
            this.totalRating = totalRating;
            this.ratedBookCount = ratedBookCount;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the genre this information is for.
        public Genre getGenre() {
            return genre;
        }

        @Override
        public String toString() {
            return genre.getName() + " | Average rating of " + decimalFormat.format(getAverageRating());
        }
    }

    // An abstract class meant to be extended that tracks ratings.
    private static abstract class TracksRatings {
        protected int totalRating;
        protected int ratedBookCount;

        // Requires: int n - amount to increment total rating by.
        // Modifies: totalRating.
        // Effects: Increments the total ratings books of this genre have gotten by N.
        public void incrementTotalRatingBy(int n) {
            totalRating += n;
        }

        public int getTotalRating() {
            return totalRating;
        }

        // Requires: Nothing.
        // Modifies: ratedBookCount.
        // Effects: Increments the total number of books of this genre that have been rated by 1.
        public void incrementRatedBookCount() {
            ratedBookCount++;
        }

        public int getRatedBookCount() {
            return ratedBookCount;
        }

        // Requires: Nothing.
        // Modifies: Nothing.
        // Effects: Returns the average rating books of this genre have gotten.
        public double getAverageRating() {
            if (ratedBookCount == 0) return 0;
            return (double) totalRating / ratedBookCount;
        }
    }
}
