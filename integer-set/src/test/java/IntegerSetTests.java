import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class IntegerSetTests {
    IntegerSet testSet;

    @Before
    public void setup() {
        testSet = new IntegerSet();
    }

    @Test
    public void testInsertNotThere() {
        // Check that the number is not already in the set.
        assertEquals(0, testSet.size());
        assertFalse(testSet.contains(3));

        // Insert a number.
        testSet.insert(3);

        // Check that the number is in the set.
        assertEquals(1, testSet.size());
        assertTrue(testSet.contains(3));
    }

    @Test
    public void testInsertAlreadyThere() {
        // Insert a number.
        testSet.insert(3);
        // Assert that our number was inserted correctly.
        assertEquals(1, testSet.size());
        assertTrue(testSet.contains(3));

        // Insert the same number again.
        testSet.insert(3);
        // Assert that the size of the set has not increased, as it already contains the number we wish to insert.
        assertEquals(1, testSet.size());
        // Assert that the set still contains the given number.
        assertTrue(testSet.contains(3));
    }
}
