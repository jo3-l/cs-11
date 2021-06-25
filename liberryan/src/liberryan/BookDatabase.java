package liberryan;

import liberryan.serialization.BookSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Stores books.
public class BookDatabase {
    private final List<Book> wantToRead = new ArrayList<>();
    private final List<Book> currentlyReading = new ArrayList<>();
    private final List<Book> alreadyRead = new ArrayList<>();

    // Requires: String filepath - file path to save to.
    // Modifies: Nothing.
    // Effects: Attempts to save the state of the book database to disk at the filepath provided.
    // The current value of the file will be overwritten.
    // On failure, an IOException is thrown.
    //
    // The format is:
    //  books in 'want to read' folder + newline
    //  + books in 'currently reading' folder + newline
    //  + books in 'already read' folder
    public void save(String filepath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) {
            String encoded = BookSerializer.encodeMany(wantToRead)
                    + "\n" + BookSerializer.encodeMany(currentlyReading)
                    + "\n" + BookSerializer.encodeMany(alreadyRead);
            writer.write(encoded);
        }
    }

    // Requires: BookFolder folder - folder to get.
    // Modifies: Nothing.
    // Effects: Gets the list of books in the folder provided.
    public List<Book> getBooksInFolder(BookFolder folder) {
        switch (folder) {
            case WANT_TO_READ:
                return wantToRead;
            case CURRENTLY_READING:
                return currentlyReading;
            case ALREADY_READ:
                return alreadyRead;
            default:
                throw new IllegalArgumentException("Invalid book folder type; this should never happen.");
        }
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Gets all books stored in the database.
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>(wantToRead.size() + currentlyReading.size() + alreadyRead.size());
        books.addAll(wantToRead);
        books.addAll(currentlyReading);
        books.addAll(alreadyRead);
        return books;
    }

    // Requires: BookFolder folder - folder to remove from, Book book - book to remove.
    // Modifies: wantToRead, currentlyReading, alreadyRead.
    // Effects: Removes the book from the folder provided. If the book is not a member of the list, this function does
    // nothing.
    public void removeBookFromList(BookFolder folder, Book book) {
        getBooksInFolder(folder).remove(book);
    }

    // Requires: BookFolder folder - folder to add book to, Book book - book to add.
    // Modifies: wantToRead, currentlyReading, alreadyRead.
    // Effects: Adds the book to the folder provided.
    public void addBookToList(BookFolder folder, Book book) {
        getBooksInFolder(folder).add(book);
    }

    // Requires: BookFolder from - source folder, BookFolder to - destination folder, Book book - target book.
    // Modifies: wantToRead, currentlyReading, alreadyRead.
    // Effects: Moves the book from the folder 'from' to the folder 'to'.
    public void moveBookToFolder(BookFolder from, BookFolder to, Book book) {
        removeBookFromList(from, book);
        addBookToList(to, book);
    }

    // Requires: String filepath - filepath to read from.
    // Modifies: Nothing.
    // Effects: Creates a book database instance from the data at the filepath provided.
    // On failure, an IOException is thrown.
    public static BookDatabase fromFile(String filepath) throws IOException {
        BookDatabase db = new BookDatabase();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            List<List<Book>> groups = reader
                    .lines()
                    .filter(line -> !line.isEmpty()) // filter out empty lines
                    .map(BookSerializer::decodeMany) // decode book list
                    .collect(Collectors.toList());

            // add books to state
            db.wantToRead.addAll(groups.get(0));
            db.currentlyReading.addAll(groups.get(1));
            db.alreadyRead.addAll(groups.get(2));
        }

        return db;
    }
}
