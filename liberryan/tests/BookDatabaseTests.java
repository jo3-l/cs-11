import liberryan.Book;
import liberryan.BookDatabase;

import liberryan.BookFolder;
import liberryan.Genre;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookDatabaseTests {
    private BookDatabase database;

    @Before
    public void setup() {
        database = new BookDatabase();
    }

    @Test
    public void testAddBookToWantToRead() {
        Book book = new Book(null, Genre.MYSTERY, "ac", "bc", 500, null, null);
        database.addBookToList(BookFolder.WANT_TO_READ, book);

        // make sure book was added
        List<Book> books = database.getBooksInFolder(BookFolder.WANT_TO_READ);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));

        // make sure it was added to only that folder
        assertTrue(database.getBooksInFolder(BookFolder.CURRENTLY_READING).isEmpty());
        assertTrue(database.getBooksInFolder(BookFolder.ALREADY_READ).isEmpty());
    }

    @Test
    public void testAddBookToCurrentlyReading() {
        Book book = new Book(null, Genre.MYSTERY, "ac", "bc", 500, null, null);
        database.addBookToList(BookFolder.CURRENTLY_READING, book);

        // make sure book was added
        List<Book> books = database.getBooksInFolder(BookFolder.CURRENTLY_READING);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));

        // make sure it was added to only that folder
        assertTrue(database.getBooksInFolder(BookFolder.WANT_TO_READ).isEmpty());
        assertTrue(database.getBooksInFolder(BookFolder.ALREADY_READ).isEmpty());
    }

    @Test
    public void testAddBookToAlreadyRead() {
        Book book = new Book(null, Genre.MYSTERY, "ac", "bc", 500, null, null);
        database.addBookToList(BookFolder.ALREADY_READ, book);

        // make sure book was added
        List<Book> books = database.getBooksInFolder(BookFolder.ALREADY_READ);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));

        // make sure it was added to only that folder
        assertTrue(database.getBooksInFolder(BookFolder.WANT_TO_READ).isEmpty());
        assertTrue(database.getBooksInFolder(BookFolder.CURRENTLY_READING).isEmpty());
    }

    @Test
    public void testAddMultipleBooksToDifferentFolders() {
        Book wantToReadBook0 = new Book(null, Genre.YOUNG_ADULT, "asdfa", "a", 200, null, null);
        Book wantToReadBook1 = new Book(null, Genre.FANTASY, "asdfasdf", "4", 330, null, null);
        Book currentlyReadingBook0 = new Book(null, Genre.HISTORICAL_FICTION, "asdfasdfasdf", "444", 345, null, null);
        Book alreadyReadBook0 = new Book(null, Genre.ROMANCE, "asdfasdfasdf", "4", 366, null, null);

        database.addBookToList(BookFolder.WANT_TO_READ, wantToReadBook0);
        database.addBookToList(BookFolder.CURRENTLY_READING, currentlyReadingBook0);
        database.addBookToList(BookFolder.WANT_TO_READ, wantToReadBook1);
        database.addBookToList(BookFolder.ALREADY_READ, alreadyReadBook0);

        // make sure they were added to their proper folders.
        List<Book> wantToRead = database.getBooksInFolder(BookFolder.WANT_TO_READ);
        assertFalse(wantToRead.isEmpty());
        assertEquals(2, wantToRead.size());
        assertEquals(wantToReadBook0, wantToRead.get(0));
        assertEquals(wantToReadBook1, wantToRead.get(1));

        List<Book> currentlyReading = database.getBooksInFolder(BookFolder.CURRENTLY_READING);
        assertFalse(currentlyReading.isEmpty());
        assertEquals(1, currentlyReading.size());
        assertEquals(currentlyReadingBook0, currentlyReading.get(0));

        List<Book> alreadyRead = database.getBooksInFolder(BookFolder.ALREADY_READ);
        assertFalse(alreadyRead.isEmpty());
        assertEquals(1, alreadyRead.size());
        assertEquals(alreadyReadBook0, alreadyRead.get(0));
    }

    @Test
    public void testGetAllBooks() {
        Book book0 = new Book(null, Genre.YOUNG_ADULT, "asdfa", "a", 200, null, null);
        Book book1 = new Book(null, Genre.FANTASY, "asdfasdf", "4", 330, null, null);
        Book book2 = new Book(null, Genre.HISTORICAL_FICTION, "asdfasdfasdf", "444", 345, null, null);
        Book book3 = new Book(null, Genre.ROMANCE, "asdfasdfasdf", "4", 366, null, null);

        database.addBookToList(BookFolder.WANT_TO_READ, book0);
        database.addBookToList(BookFolder.CURRENTLY_READING, book2);
        database.addBookToList(BookFolder.WANT_TO_READ, book1);
        database.addBookToList(BookFolder.ALREADY_READ, book3);

        List<Book> books = database.getAllBooks();
        assertFalse(books.isEmpty());
        assertEquals(4, books.size());
        assertEquals(book0, books.get(0));
        assertEquals(book1, books.get(1));
        assertEquals(book2, books.get(2));
        assertEquals(book3, books.get(3));
    }

    @Test
    public void testRemoveFromBookFolderWhenBookNotThere() {
        Book book = new Book(null, Genre.MYSTERY, "ac", "bc", 500, null, null);
        database.addBookToList(BookFolder.ALREADY_READ, book);

        // nonexistent book
        database.removeBookFromList(BookFolder.ALREADY_READ, new Book(null, Genre.SCIENCE_FICTION, "ac", "bc", 444, null, null));
        List<Book> books = database.getBooksInFolder(BookFolder.ALREADY_READ);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(book, books.get(0)); // make sure book is still there
    }

    @Test
    public void testRemoveFromBookFolderWithWrongFolder() {
        Book book = new Book(null, Genre.MYSTERY, "ac", "bc", 500, null, null);
        database.addBookToList(BookFolder.ALREADY_READ, book);

        // correct book, but wrong folder.
        database.removeBookFromList(BookFolder.CURRENTLY_READING, book);
        List<Book> books = database.getBooksInFolder(BookFolder.ALREADY_READ);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(book, books.get(0)); // make sure book is still there
    }

    @Test
    public void testRemoveFromBookFolderValidTarget() {
        Book wantToReadBook0 = new Book(null, Genre.YOUNG_ADULT, "asdfa", "a", 200, null, null);
        Book wantToReadBook1 = new Book(null, Genre.FANTASY, "asdfasdf", "4", 330, null, null);
        Book currentlyReadingBook0 = new Book(null, Genre.HISTORICAL_FICTION, "asdfasdfasdf", "444", 345, null, null);
        Book alreadyReadBook0 = new Book(null, Genre.ROMANCE, "asdfasdfasdf", "4", 366, null, null);

        database.addBookToList(BookFolder.WANT_TO_READ, wantToReadBook0);
        database.addBookToList(BookFolder.CURRENTLY_READING, currentlyReadingBook0);
        database.addBookToList(BookFolder.WANT_TO_READ, wantToReadBook1);
        database.addBookToList(BookFolder.ALREADY_READ, alreadyReadBook0);

        database.removeBookFromList(BookFolder.ALREADY_READ, alreadyReadBook0);

        // all other books should still be there.
        List<Book> wantToRead = database.getBooksInFolder(BookFolder.WANT_TO_READ);
        assertFalse(wantToRead.isEmpty());
        assertEquals(2, wantToRead.size());
        assertEquals(wantToReadBook0, wantToRead.get(0));
        assertEquals(wantToReadBook1, wantToRead.get(1));

        List<Book> currentlyReading = database.getBooksInFolder(BookFolder.CURRENTLY_READING);
        assertFalse(currentlyReading.isEmpty());
        assertEquals(1, currentlyReading.size());
        assertEquals(currentlyReadingBook0, currentlyReading.get(0));

        List<Book> alreadyRead = database.getBooksInFolder(BookFolder.ALREADY_READ);
        assertTrue(alreadyRead.isEmpty());
    }
}
