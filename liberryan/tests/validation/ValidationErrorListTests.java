package validation;

import liberryan.Book;
import liberryan.validation.ValidationError;
import liberryan.validation.ValidationErrorList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationErrorListTests {
    private ValidationErrorList<Book.Field> errors;

    @Before
    public void setup() {
        errors = new ValidationErrorList<>();
    }

    @Test
    public void testAddSingleError() {
        errors.add(Book.Field.PAGE_COUNT, "error message 1");
        // make sure it was added, and has the correct values.
        assertEquals(1, errors.getErrors().size());
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("error message 1", error.getMessage());
        assertEquals(Book.Field.PAGE_COUNT, error.getField());
    }

    @Test
    public void testAddManyErrors() {
        errors.add(Book.Field.PAGE_COUNT, "error message 1");
        // make sure it was added, and has the correct values.
        assertEquals(1, errors.getErrors().size());
        ValidationError<Book.Field> error = errors.getErrors().get(0);
        assertEquals("error message 1", error.getMessage());
        assertEquals(Book.Field.PAGE_COUNT, error.getField());

        errors.add(Book.Field.AUTHOR, "error message 2");
        // make sure it was added to the end of the list, and has the correct values.
        assertEquals(2, errors.getErrors().size());
        error = errors.getErrors().get(1);
        assertEquals("error message 2", error.getMessage());
        assertEquals(Book.Field.AUTHOR, error.getField());
    }

    @Test
    public void testHasErrorsWithoutAnyErrors() {
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testHasErrorsWithErrors() {
        errors.add(Book.Field.PAGE_COUNT, "error message");
        assertTrue(errors.hasErrors());
    }
}
