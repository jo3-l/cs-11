import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        int[] values = {};
        System.out.println(Arrays.toString(insertAt(values, 0, 5)));

        values = new int[]{1, 2, 3};
        System.out.println(Arrays.toString(insertAt(values, 1, 5)));

        values = new int[]{1, 2, 3, 4};
        System.out.println(Arrays.toString(insertAt(values, 4, 5)));

        values = new int[]{};
        System.out.println(Arrays.toString(add(values, 1)));

        values = new int[]{1, 2, 3};
        System.out.println(Arrays.toString(add(values, 123)));

        values = new int[]{2, 3, 5};
        System.out.println(Arrays.toString(insertAt(values, 2, 4)));

        values = new int[]{1, 2};
        System.out.println(Arrays.toString(pop(values)));
    }

    /**
     * Makes a copy of the array with the element added to the back.
     *
     * @param array   Array to add to.
     * @param element Element to add.
     * @return A copy of the array with the element added to the end.
     */
    public static int[] add(int[] array, int element) {
        // `add` calls `insertAt` with the index set to the array length, which will append an element to the end.
        return insertAt(array, array.length, element);
    }

    /**
     * Makes a copy of the array without the last element.
     *
     * @param array Array to pop from.
     * @return A copy of the array without the last element.
     * @throws NoSuchElementException If the array is empty.
     */
    public static int[] pop(int[] array) {
        if (array.length == 0) throw new NoSuchElementException("Cannot pop from an array with no elements.");

        // Allocate a new array with a length of 1 less than the original one.
        int[] newArray = new int[array.length - 1];

        // Copy all elements from the original array into the new array, except the last one.
        // Can also be written as: System.arraycopy(array, 0, newArray, 0, array.length - 1);
        for (int i = 0; i < array.length - 1; i++) newArray[i] = array[i];

        return newArray;
    }

    /**
     * Returns a copy of the array with the element added at the index given.
     *
     * @param array   Array to add to.
     * @param index   Index at which the element should be added. The new element will be inserted immediately before the
     *                element currently at that position. If <code>array.length</code> is provided as the value of this
     *                parameter, the element will be added to the end of the array.
     * @param element Element to add.
     * @return A copy of the array with the element added at the index given.
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds.
     */
    public static int[] insertAt(int[] array, int index, int element) {
        if (index < 0 || index > array.length) throw new ArrayIndexOutOfBoundsException();

        // Allocate an array with a length of 1 more than the original one.
        int[] newArray = new int[array.length + 1];
        // Add the element at the specified index.
        newArray[index] = element;

        // Copy all elements from the original array with indices lower than the index given into the new array.
        // Can also be written as: System.arraycopy(array, 0, newArray, 0, index);
        for (int i = 0; i < index; i++) newArray[i] = array[i];
        // Then, do the same for elements with indices greater than the index given, but shift all indices up one (since
        // we just added an element).
        // Can also be written as: if (array.length - index >= 0) System.arraycopy(array, index, newArray, index + 1, array.length - index);
        for (int i = index; i < array.length; i++) newArray[i + 1] = array[i];

        return newArray;
    }
}
