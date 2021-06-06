package liberryan.validation;

import liberryan.Book;
import liberryan.Time;

public class BookValidator {
    private static Validator<Book, Field> instance;

    public static Validator<Book, Field> getInstance() {
        if (instance != null) return instance;
        return instance = new Validator<Book, Field>()
                .addValidation(
                        book -> book.getGenre() == null,
                        "Book genre is required",
                        Field.BOOK_GENRE
                )
                .addValidation(
                        book -> book.getAuthor().isEmpty(),
                        "Book author is a required field",
                        Field.AUTHOR
                )
                .addValidation(
                        book -> book.getName().isEmpty(),
                        "Book name is a required field",
                        Field.BOOK_NAME
                )
                .addValidation(
                        book -> book.getPageCount() < 0,
                        "Book page count cannot be negative",
                        Field.PAGE_COUNT
                )
                .addValidation(
                        book -> book.getPublishedDate() != null
                                && book.getPublishedDate().isBefore(Time.currentLocalDate()),
                        "Published date must be before the current date",
                        Field.PUBLISHED_DATE
                );
    }

    public static ValidationErrorDisplaySynchronizer<Field> getDisplaySynchronizer() {
        return new ValidationErrorDisplaySynchronizer<>(Field.class);
    }

    public enum Field {
        BOOK_GENRE,
        AUTHOR,
        BOOK_NAME,
        PAGE_COUNT,
        PUBLISHED_DATE,
    }
}
