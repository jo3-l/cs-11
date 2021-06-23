package liberryan.serialization;

import liberryan.Book;
import liberryan.BookBuilder;
import liberryan.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Implementation of a decoder/encoder for books.
public class BookSerializer {
    // The character used to separate individual fields of books.
    public static final char FIELD_SEPARATOR = 0xBEEF;
    // The character used to separate encoded books when encoding many books at once.
    public static final char SEPARATOR = 0xF00D;

    // Requires: List<Book> books.
    // Modifies: Nothing.
    // Effects: Encodes all books into a single string. It may be decoded back into its original form via
    // BookSerializer.decodeMany().
    //
    // The format is:
    //  (book + separator)*
    public static String encodeMany(List<Book> books) {
        StringBuilder buf = new StringBuilder();
        for (Book book : books) {
            buf.append(encode(book)).append(SEPARATOR);
        }
        return buf.toString();
    }

    // Requires: String raw.
    // Modifies: Nothing.
    // Effects: Decodes the string back into a list of book objects.
    public static List<Book> decodeMany(String raw) {
        List<Book> books = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == SEPARATOR) {
                books.add(decode(buf.toString()));
                // reset buffer
                buf.setLength(0);
            } else {
                // not a field separator, just add it to the end of the buffer
                buf.append(c);
            }
        }

        return books;
    }

    // Requires: Book book.
    // Modifies: Nothing.
    // Effects: Encodes the book as a string. It may be decoded back into its original form via BookSerializer.encode().
    //
    // The format used is:
    //  progress updates + field separator + genre + field separator + name + field separator + author + page count
    //  + field separator + rating + field separator + published date + field separator.
    public static String encode(Book book) {
        StringBuilder buf = new StringBuilder();

        buf.append(ProgressUpdateSerializer.encodeMany(book.getProgressUpdates()));
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getGenre().name());
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getName());
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getAuthor());
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getPageCount());
        buf.append(FIELD_SEPARATOR);

        if (book.getRating() != null) buf.append(RatingSerializer.encode(book.getRating()));
        buf.append(FIELD_SEPARATOR);

        if (book.getPublishedDate() != null) buf.append(book.getPublishedDate());
        buf.append(FIELD_SEPARATOR);
        return buf.toString();
    }

    // Requires: String raw.
    // Modifies: Nothing.
    // Effects: Decodes the string into a book object.
    public static Book decode(String raw) {
        StringBuilder buf = new StringBuilder();
        BookBuilder builder = new BookBuilder();

        Book.Field currentField = Book.Field.PROGRESS_UPDATES;
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            // If the current character is the field separator, then set the current field's value.
            if (c == FIELD_SEPARATOR) {
                // Set the corresponding field.
                String val = buf.toString();
                switch (currentField) {
                    case PROGRESS_UPDATES:
                        builder.setProgressUpdates(ProgressUpdateSerializer.decodeMany(val));
                        currentField = Book.Field.GENRE;
                        break;
                    case GENRE:
                        builder.setGenre(Genre.valueOf(val));
                        currentField = Book.Field.NAME;
                        break;
                    case NAME:
                        builder.setName(val);
                        currentField = Book.Field.AUTHOR;
                        break;
                    case AUTHOR:
                        builder.setAuthor(val);
                        currentField = Book.Field.PAGE_COUNT;
                        break;
                    case PAGE_COUNT:
                        builder.setPageCount(Integer.parseInt(val));
                        currentField = Book.Field.RATING;
                        break;
                    case RATING:
                        // only set if the value isn't empty; empty denotes a null rating
                        if (!val.isEmpty()) builder.setRating(RatingSerializer.decode(val));
                        currentField = Book.Field.PUBLISHED_DATE;
                        break;
                    case PUBLISHED_DATE:
                        // same thing: only set if the value isn't empty; empty denotes a null date
                        if (!val.isEmpty()) builder.setPublishedDate(LocalDate.parse(val));
                        break;
                }

                // reset buffer
                buf.setLength(0);
            } else {
                // not a field separator, just append it to the buffer for the current field's value
                buf.append(c);
            }
        }

        return builder.build();
    }
}
