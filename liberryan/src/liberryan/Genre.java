package liberryan;

// An enumeration of book genres.
public enum Genre {
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    MYSTERY("Mystery"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    HISTORICAL_FICTION("Historical Fiction"),
    ROMANCE("Romance"),
    SHORT_STORY("Short Story"),
    GRAPHIC_NOVEL("Graphic Novel"),
    YOUNG_ADULT("Young Adult");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
