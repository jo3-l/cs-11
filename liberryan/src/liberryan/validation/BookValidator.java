package liberryan.validation;

import liberryan.Book;
import liberryan.Time;

import java.util.EnumSet;
import java.util.Set;

public class BookValidator {
    public static void validate(Book book, ValidationErrorList<Book.Field> errors) {
        validate(book, errors, EnumSet.noneOf(Book.Field.class));
    }

    public static void validate(Book book, ValidationErrorList<Book.Field> errors, Set<Book.Field> skip) {
        if (!skip.contains(Book.Field.NAME) && book.getName().isEmpty()) {
            errors.add(Book.Field.NAME, "Book name is required");
        }

        if (!skip.contains(Book.Field.GENRE) && book.getGenre() == null) {
            errors.add(Book.Field.GENRE, "Book genre is required");
        }

        if (!skip.contains(Book.Field.AUTHOR) && book.getAuthor().isEmpty()) {
            errors.add(Book.Field.AUTHOR, "Book author is required");
        }

        if (!skip.contains(Book.Field.PAGE_COUNT) && book.getPageCount() <= 0) {
            errors.add(Book.Field.PAGE_COUNT, "Book page count must be positive");
        }

        if (!skip.contains(Book.Field.PUBLISHED_DATE)
                && book.getPublishedDate() != null
                && book.getPublishedDate().isBefore(Time.currentLocalDate())) {
            errors.add(Book.Field.PUBLISHED_DATE, "Book published date must be before now");
        }
    }
}