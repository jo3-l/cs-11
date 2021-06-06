package liberryan;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDatabase {
    public static final char FIELD_SEPARATOR = 0xBEEF;
    public static final char ITEM_SEPARATOR = 0xF00D;

    private final List<Book> wantToRead = new ArrayList<>();
    private final List<Book> currentlyReading = new ArrayList<>();
    private final List<Book> alreadyRead = new ArrayList<>();

    private final String filepath;

    public BookDatabase(String filepath) {
        this.filepath = filepath;
    }

    public void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) {
            String encoded = encodeAllBooks(wantToRead)
                    + "\n" + encodeAllBooks(currentlyReading)
                    + "\n" + encodeAllBooks(alreadyRead);
            writer.write(encoded);
        }
    }

    public List<Book> getWantToRead() {
        return wantToRead;
    }

    public void addBookToWantToRead(Book book) {
        wantToRead.add(book);
    }

    public void removeBookFromWantToRead(Book book) {
        wantToRead.remove(book);
    }

    public List<Book> getCurrentlyReading() {
        return currentlyReading;
    }

    public void addBookToCurrentlyReading(Book book) {
        currentlyReading.add(book);
    }

    public void removeBookFromCurrentlyReading(Book book) {
        currentlyReading.remove(book);
    }

    public List<Book> getAlreadyRead() {
        return alreadyRead;
    }

    public void addBookToAlreadyRead(Book book) {
        alreadyRead.add(book);
    }

    public void removeBookFromAlreadyRead(Book book) {
        alreadyRead.remove(book);
    }

    public static BookDatabase fromFile(String filepath) throws IOException {
        BookDatabase db = new BookDatabase(filepath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            List<List<Book>> groups = reader
                    .lines()
                    .filter(line -> !line.isEmpty()) // filter out empty lines
                    .map(BookDatabase::decodeAllBooks) // decode book list
                    .collect(Collectors.toList());

            db.wantToRead.addAll(groups.get(0));
            db.currentlyReading.addAll(groups.get(1));
            db.alreadyRead.addAll(groups.get(2));
        }

        return db;
    }

    public static String encodeAllBooks(List<Book> books) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < books.size(); i++) {
            if (i > 0) buf.append(ITEM_SEPARATOR);
            Book book = books.get(i);
            buf.append(encodeBook(book));
        }
        return buf.toString();
    }

    public static List<Book> decodeAllBooks(String raw) {
        List<Book> books = new ArrayList<>();

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == ITEM_SEPARATOR) {
                String rawBook = buf.toString();
                books.add(decodeBook(rawBook));
                buf.setLength(0); // clear buffer
            } else {
                buf.append(c); // just append it to the buffer for the current book's value
            }
        }

        return books;
    }

    public static String encodeBook(Book book) {
        StringBuilder buf = new StringBuilder();
        buf.append(book.getGenre().name());
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getName());
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getAuthor());
        buf.append(FIELD_SEPARATOR);

        buf.append(book.getPageCount());
        buf.append(FIELD_SEPARATOR);

        if (book.getRating() != null) buf.append(book.getRating());
        buf.append(FIELD_SEPARATOR);

        if (book.getPublishedDate() != null) buf.append(book.getPublishedDate());
        return buf.toString();
    }

    public static Book decodeBook(String raw) {
        StringBuilder buf = new StringBuilder();
        Book book = new Book(null, "", "", 0, null, null);

        // field number we're on.
        int counter = 0;
        // loop through all characters:
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            // If the current character is the field separator, then set the current field's value.
            if (c == FIELD_SEPARATOR) {
                String val = buf.toString();
                switch (counter) {
                    case 0: // genre
                        book.setGenre(Genre.valueOf(val));
                        break;
                    case 1: // name
                        book.setName(val);
                        break;
                    case 2: // author
                        book.setAuthor(val);
                        break;
                    case 3: // page count
                        book.setPageCount(Integer.parseInt(val));
                        break;
                    case 4: // rating
                        // only set if the value isn't empty; empty denotes a null rating
                        if (!val.isEmpty()) book.setRating(Integer.parseInt(val));
                        break;
                    case 5: // published date
                        // same thing: only set if the value isn't empty; empty denotes a null date
                        if (!val.isEmpty()) book.setPublishedDate(LocalDate.parse(val));
                        break;
                }

                // clear buffer
                buf.setLength(0);
                // move onto the next field
                counter++;
            } else {
                // otherwise, just append it to the buffer for the current field's value
                buf.append(c);
            }
        }

        return book;
    }
}
