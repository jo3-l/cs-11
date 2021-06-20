package liberryan;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RecommendationGenerator {
    private static boolean RANDOMIZE_RECOMMENDATIONS = true;

    private final Random random = new Random();
    private final BookDatabase database;
    private final StatisticsManager stats;

    public RecommendationGenerator(BookDatabase database, StatisticsManager stats) {
        this.database = database;
        this.stats = stats;
    }

    public Book getBookRecommendation() {
        List<StatisticsManager.GenreInfo> genres = stats.getGenresSortedByPopularity();
        List<StatisticsManager.AuthorInfo> authors = stats.getAuthorsSortedByPopularity();
        List<Book> topRated = database.getAllBooks()
                .stream()
                .sorted(Comparator.comparingDouble(book -> {
                    // take sum of their author and genre average ratings
                    double genreAvg = genres.stream()
                            .filter(info -> info.getGenre() == book.getGenre())
                            .findAny()
                            .map(StatisticsManager.GenreInfo::getAverageRating)
                            .orElse(0d);
                    double authorAvg = authors.stream()
                            .filter(info -> info.getName().equals(book.getAuthor()))
                            .findAny()
                            .map(StatisticsManager.AuthorInfo::getAverageRating)
                            .orElse(0d);
                    return genreAvg + authorAvg;
                }))
                .limit(5)
                .collect(Collectors.toList());

        if (topRated.isEmpty()) return null;
        return topRated.get(
                RANDOMIZE_RECOMMENDATIONS
                        ? random.nextInt(topRated.size())
                        : 1
        );
    }

    public static void setRandomizeRecommendations(boolean doRandomize) {
        RANDOMIZE_RECOMMENDATIONS = doRandomize;
    }
}
