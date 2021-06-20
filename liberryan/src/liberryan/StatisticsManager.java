package liberryan;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsManager {
    private final BookDatabase database;

    public StatisticsManager(BookDatabase database) {
        this.database = database;
    }

    public List<WeekDayReadingActivityInfo> getReadingActivityBetween(Instant startTime, Instant endTime) {
        List<WeekDayReadingActivityInfo> activity = new ArrayList<>();
        for (int day = 1; day <= 7; day++) {
            WeekDayReadingActivityInfo info = new WeekDayReadingActivityInfo(DayOfWeek.of(day), 0);
            activity.add(info);
        }

        for (Book book : database.getAllBooks()) {
            for (ProgressUpdate update : book.getProgressUpdates()) {
                // only consider progress updates in the time range provided
                if (update.getTime().isBefore(startTime) || update.getTime().isAfter(endTime)) continue;
                // ChronoField.DAY_OF_WEEK returns a 1-based integer, but we need 0-based for index.
                int offset = update.getTime().get(ChronoField.DAY_OF_WEEK) - 1;
                activity.get(offset).incrementPagesReadBy(update.getPagesRead());
            }
        }

        return activity;
    }

    public List<GenreInfo> getGenresSortedByPopularity() {
        List<GenreInfo> allGenreInfo = new ArrayList<>();
        for (Genre genre : Genre.values()) {
            GenreInfo genreInfo = new GenreInfo(genre, 0, 0);
            allGenreInfo.add(genreInfo);
        }

        for (Book book : database.getAllBooks()) {
            // only consider books with a rating
            if (book.getRating() == null) continue;
            GenreInfo genreInfo = allGenreInfo.get(book.getGenre().ordinal());
            genreInfo.incrementRatedBookCount();
            genreInfo.incrementTotalRatingBy(book.getRating().getRating());
        }

        return allGenreInfo.stream()
                .sorted(Comparator.comparingDouble(GenreInfo::getAverageRating))
                .collect(Collectors.toList());
    }

    public List<AuthorInfo> getAuthorsSortedByPopularity() {
        Map<String, AuthorInfo> allAuthorInfo = new HashMap<>();
        for (Book book : database.getAllBooks()) {
            // only consider books with a rating
            if (book.getRating() == null) continue;
            allAuthorInfo.putIfAbsent(book.getAuthor(), new AuthorInfo(book.getAuthor(), 0, 0));
            AuthorInfo info = allAuthorInfo.get(book.getAuthor());
            info.incrementRatedBookCount();
            info.incrementTotalRatingBy(book.getRating().getRating());
        }

        return allAuthorInfo.values()
                .stream()
                .sorted(Comparator.comparingDouble(AuthorInfo::getAverageRating))
                .collect(Collectors.toList());
    }

    public static class AuthorInfo {
        private final String name;
        private int totalRating;
        private int ratedBookCount;

        public AuthorInfo(String name, int totalRating, int ratedBookCount) {
            this.name = name;
            this.totalRating = totalRating;
            this.ratedBookCount = ratedBookCount;
        }

        public String getName() {
            return name;
        }

        public void incrementTotalRatingBy(int n) {
            totalRating += n;
        }

        public int getTotalRating() {
            return totalRating;
        }

        public void incrementRatedBookCount() {
            ratedBookCount++;
        }

        public int getRatedBookCount() {
            return ratedBookCount;
        }

        public double getAverageRating() {
            return (double) totalRating / ratedBookCount;
        }
    }

    public static class GenreInfo {
        private final Genre genre;
        private int totalRating;
        private int ratedBookCount;

        public GenreInfo(Genre genre, int totalRating, int ratedBookCount) {
            this.genre = genre;
            this.totalRating = totalRating;
            this.ratedBookCount = ratedBookCount;
        }

        public Genre getGenre() {
            return genre;
        }

        public void incrementTotalRatingBy(int n) {
            totalRating += n;
        }

        public int getTotalRating() {
            return totalRating;
        }

        public void incrementRatedBookCount() {
            ratedBookCount++;
        }

        public int getRatedBookCount() {
            return ratedBookCount;
        }

        public double getAverageRating() {
            return (double) totalRating / ratedBookCount;
        }
    }

    public static class WeekDayReadingActivityInfo {
        private final DayOfWeek dayOfWeek;
        private int pagesRead;

        public WeekDayReadingActivityInfo(DayOfWeek dayOfWeek, int pagesRead) {
            this.dayOfWeek = dayOfWeek;
            this.pagesRead = pagesRead;
        }

        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        public void incrementPagesReadBy(int n) {
            pagesRead += n;
        }

        public int getPagesRead() {
            return pagesRead;
        }
    }
}
