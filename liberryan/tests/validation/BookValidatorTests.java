package validation;

import liberryan.Book;
import liberryan.Genre;
import liberryan.Time;
import liberryan.validation.BookValidator;
import liberryan.validation.ValidationError;
import liberryan.validation.ValidationErrorList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.Assert.*;

// TODO: Test validate() method
public class BookValidatorTests {
    private ValidationErrorList<Book.Field> errors;

    @Before
    public void setup() {
        errors = new ValidationErrorList<>();
    }

    @After
    public void teardown() {
        Time.resetInternalClock();
    }

    @Test
    public void testValidateNameWithEmptyName() {
        // empty names should not be valid.
        assertFalse(BookValidator.validateName("", errors));
        // make sure an error was added to the error list.
        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getErrors().size());

        // make sure the error is what we think it is.
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("Name is required", error.getMessage());
        assertEquals(Book.Field.NAME, error.getField());
    }

    @Test
    public void testValidateNameWithNonEmptyName() {
        // non-empty names should be valid.
        assertTrue(BookValidator.validateName("Harry Potter", errors));
        // make sure nothing was added to the error list.
        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getErrors().size());
    }

    @Test
    public void testValidateGenreWithNullValue() {
        // null genres should not be accepted.
        assertFalse(BookValidator.validateGenre(null, errors));
        // make sure an error was added to the error list.
        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getErrors().size());

        // make sure the error is what we think it is.
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("Genre is required", error.getMessage());
        assertEquals(Book.Field.GENRE, error.getField());
    }

    @Test
    public void testValidateGenreWithNonNullValue() {
        // non-null genres should be valid.
        assertTrue(BookValidator.validateGenre(Genre.FANTASY, errors));
        // make sure nothing was added to the error list.
        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getErrors().size());
    }

    @Test
    public void testValidateAuthorWithEmptyAuthor() {
        // empty author names should not be valid.
        assertFalse(BookValidator.validateAuthor("", errors));
        // make sure an error was added to the error list.
        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getErrors().size());

        // make sure the error is what we think it is.
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("Author name is required", error.getMessage());
        assertEquals(Book.Field.AUTHOR, error.getField());
    }

    @Test
    public void testValidateAuthorWithNonEmptyAuthor() {
        // non-empty author names should be valid.
        assertTrue(BookValidator.validateAuthor("Bob", errors));
        // make sure nothing was added to the error list.
        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getErrors().size());
    }

    @Test
    public void testValidatePageCountWithNegativePageCount() {
        // negative page count should not be accepted.
        assertFalse(BookValidator.validatePageCount(-5, errors));
        // make sure an error was added to the error list.
        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getErrors().size());

        // make sure the error is what we think it is.
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("Page count must be positive", error.getMessage());
        assertEquals(Book.Field.PAGE_COUNT, error.getField());
    }

    @Test
    public void testValidatePageCountWithZeroPageCount() {
        // page count = 0 should not be accepted.
        assertFalse(BookValidator.validatePageCount(0, errors));
        // make sure an error was added to the error list.
        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getErrors().size());

        // make sure the error is what we think it is.
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("Page count must be positive", error.getMessage());
        assertEquals(Book.Field.PAGE_COUNT, error.getField());
    }

    @Test
    public void testValidatePageCountWithPositivePageCount() {
        // positive page count should be valid.
        assertTrue(BookValidator.validatePageCount(1, errors));
        // make sure nothing was added to the error list.
        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getErrors().size());
    }

    @Test
    public void testValidatePublishedDateWithTimeAfterCurrentLocalDate() {
        // for deterministic tests, let's set the current time to a fixed one.
        Time.useFixedInstant(LocalDate.of(2007, 12, 3)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        // one day after.
        LocalDate publishedAt = LocalDate.of(2007, 12, 4);

        // a published time after the current time should not be accepted.
        assertFalse(BookValidator.validatePublishedDate(publishedAt, errors));
        // make sure an error was added to the error list.
        assertTrue(errors.hasErrors());
        assertEquals(1, errors.getErrors().size());

        // make sure the error is what we think it is.
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("Published date must not be after the current time", error.getMessage());
        assertEquals(Book.Field.PUBLISHED_DATE, error.getField());
    }

    @Test
    public void testValidatePublishedDateWithTimeEqualToCurrentTime() {
        // same as above; use a fixed time.
        Time.useFixedInstant(LocalDate.of(2007, 12, 3)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        System.out.println(Time.currentLocalDate());
        // exact same time.
        LocalDate publishedAt = LocalDate.of(2007, 12, 3);

        // a published time exactly equal to the current time should be valid.
        assertTrue(BookValidator.validatePublishedDate(publishedAt, errors));
        // make sure nothing was added to the error list.
        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getErrors().size());
    }

    @Test
    public void testValidatePublishedDateWithTimeAfterCurrentTime() {
        // same as above; use a fixed time.
        Time.useFixedInstant(LocalDate.of(2007, 12, 3)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        // two days before.
        LocalDate publishedAt = LocalDate.of(2007, 12, 1);

        // a published time before the current time should be valid.
        assertTrue(BookValidator.validatePublishedDate(publishedAt, errors));
        // make sure nothing was added to the error list.
        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getErrors().size());
    }
}
