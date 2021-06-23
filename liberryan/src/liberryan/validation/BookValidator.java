package liberryan.validation;

import liberryan.Book;
import liberryan.Genre;
import liberryan.Time;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Validator for books.
public class BookValidator {
    // Requires: Book book, ValidationErrorList<Book.Field> errors.
    // Modifies: errors.
    // Effects: Validates the book, adding any relevant errors to the errors list provided.
    // See the documentation comment on the longer overload for validate() for a discussion of what validations are applied.
    public static void validate(Book book, ValidationErrorList<Book.Field> errors) {
        validate(book, errors, new ArrayList<>());
    }

    // Requires: Book book, ValidationErrorList<Book.Field> errors, List<Book.Field> skip.
    // Modifies: errors.
    // Effects: Validates the book, adding any relevant errors to the errors list provided. Validations for fields
    // which are members of the list skip are skipped.
    //
    // Validations applied:
    //  - Whether the book name is non-empty.
    //  - Whether the book genre was supplied.
    //  - Whether the author name is non-empty.
    //  - Whether the page count is positive.
    //  - Whether the published date is either not present or is not after the current time.
    public static void validate(Book book, ValidationErrorList<Book.Field> errors, List<Book.Field> skip) {
        if (!skip.contains(Book.Field.NAME)) validateName(book.getName(), errors);
        if (!skip.contains(Book.Field.GENRE)) validateGenre(book.getGenre(), errors);
        if (!skip.contains(Book.Field.AUTHOR)) validateAuthor(book.getAuthor(), errors);
        if (!skip.contains(Book.Field.PAGE_COUNT)) validatePageCount(book.getPageCount(), errors);
        if (!skip.contains(Book.Field.PUBLISHED_DATE)) validatePublishedDate(book.getPublishedDate(), errors);
    }

    // Requires: String name, ValidationErrorList<Book.Field> errors.
    // Modifies: errors.
    // Effects: Validates that the name is not empty, adding any error to the errors list provided if it was invalid.
    // Returns whether the name was valid.
    public static boolean validateName(String name, ValidationErrorList<Book.Field> errors) {
        if (!name.isEmpty()) return true;
        errors.add(Book.Field.NAME, "Name is required");
        return false;
    }

    // Requires: Genre genre, ValidationErrorList<Book.Field> errors.
    // Modifies: errors.
    // Effects: Validates that the genre is non-null, adding any error to the errors list provided if it was invalid.
    // Returns whether the genre was valid.
    public static boolean validateGenre(Genre genre, ValidationErrorList<Book.Field> errors) {
        if (genre != null) return true;
        errors.add(Book.Field.GENRE, "Genre is required");
        return false;
    }

    // Requires: String author, ValidationErrorList<Book.Field> errors.
    // Modifies: errors.
    // Effects: Validates that the author name is not empty, adding any error to the errors list provided if it was invalid.
    // Returns whether the author name was valid.
    public static boolean validateAuthor(String author, ValidationErrorList<Book.Field> errors) {
        if (!author.isEmpty()) return true;
        errors.add(Book.Field.AUTHOR, "Author name is required");
        return false;
    }

    // Requires: int pageCount, ValidationErrorList<Book.Field> errors.
    // Modifies: errors.
    // Effects: Validates that pageCount is positive, adding any error to the errors list provided if it was invalid.
    // Returns whether the author name was valid.
    public static boolean validatePageCount(int pageCount, ValidationErrorList<Book.Field> errors) {
        if (pageCount > 0) return true;
        errors.add(Book.Field.PAGE_COUNT, "Page count must be positive");
        return false;
    }

    // Requires: LocalDate publishedDate, ValidationErrorList<Book.Field> errors.
    // Modifies: errors.
    // Effects: validates that publishedDate is either null or isn't after the current time, adding any error to the errors
    // list provided if it was invalid. Returns whether the published date was valid.
    public static boolean validatePublishedDate(LocalDate publishedDate, ValidationErrorList<Book.Field> errors) {
        if (publishedDate == null) return true; // published date is an optional field
        if (!publishedDate.isAfter(Time.currentLocalDate())) return true;
        errors.add(Book.Field.PUBLISHED_DATE, "Published date must not be after the current time");
        return false;
    }
}