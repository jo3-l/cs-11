package liberryan;

import liberryan.serialization.BookSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDatabase {
    private final List<Book> wantToRead = new ArrayList<>();
    private final List<Book> currentlyReading = new ArrayList<>();
    private final List<Book> alreadyRead = new ArrayList<>();

    private final String filepath;

    public BookDatabase(String filepath) {
        this.filepath = filepath;
    }

    public void save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) {
            String encoded = BookSerializer.encodeMany(wantToRead)
                    + "\n" + BookSerializer.encodeMany(currentlyReading)
                    + "\n" + BookSerializer.encodeMany(alreadyRead);
            writer.write(encoded);
        }
    }

    public List<Book> getBooksInFolder(BookFolder folder) {
        switch (folder) {
            case WANT_TO_READ:
                return wantToRead;
            case CURRENTLY_READING:
                return currentlyReading;
            case ALREADY_READ:
                return alreadyRead;
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>(wantToRead.size() + currentlyReading.size() + alreadyRead.size());
        books.addAll(wantToRead);
        books.addAll(currentlyReading);
        books.addAll(alreadyRead);
        return books;
    }

    public void removeBookFromList(BookFolder folder, Book book) {
        getBooksInFolder(folder).remove(book);
    }

    public void addBookToList(BookFolder folder, Book book) {
        getBooksInFolder(folder).add(book);
    }

    public void moveBookToFolder(BookFolder from, BookFolder to, Book book) {
        removeBookFromList(from, book);
        addBookToList(to, book);
    }

    public static BookDatabase fromFile(String filepath) throws IOException {
        BookDatabase db = new BookDatabase(filepath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            List<List<Book>> groups = reader
                    .lines()
                    .filter(line -> !line.isEmpty()) // filter out empty lines
                    .map(BookSerializer::decodeMany) // decode book list
                    .collect(Collectors.toList());

            db.wantToRead.addAll(groups.get(0));
            db.currentlyReading.addAll(groups.get(1));
            db.alreadyRead.addAll(groups.get(2));
        }

        return db;
    }
}
