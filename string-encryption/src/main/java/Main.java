import java.util.Random;

// TODO: Needs a lot more comments.
public class Main {
    // The Random instance we use to generate random integers.
    private static final Random random = new Random();

    public static void main(String[] args) {
        String str = "Hello world, foo bar baz buz :D";

        String encoded = encode(str, 123);
        System.out.println("Encoded: " + encoded);

        String decoded = decode(encoded, 123);
        System.out.println("Decoded: " + decoded);
    }

    /**
     * Encodes a string using the given key.
     *
     * @param str String to encode.
     * @param key Key to use when encoding the string. This should be a positive integer less than 500,000.
     * @return The encoded string.
     */
    private static String encode(String str, int key) {
        StringBuilder encoded = new StringBuilder();

        // We use a somewhat secure (but not very fast) algorithm for encoding strings. Essentially, we loop through
        // all characters of the string we wish to decode, and then get the character code. We then multiply this code
        // by 31. Then, if the number of iterations is a multiple of two, we add the key to the resulting number. Otherwise,
        // we subtract the key from the resulting number. We then pad this number to 7 digits using leading zeros, and then
        // write it to the decoded string. To further throw people off, we then append 2 random characters in the ASCII
        // range to the string.
        boolean isNegative = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            int toAdd = isNegative ? key : -key;
            isNegative = !isNegative;

            // The maximum value of the `char` datatype is 65535. Thus, the maximum value of a `char` * 31 is 2031585,
            // which is 7 digits long. As such, let us pad to 7 zeros.
            String value = padStart(String.valueOf(c * 31 + toAdd), 7);

            encoded.append(value);

            // Generate two random characters to append to the encoded string.
            encoded.append((char) (32 + random.nextInt(95)));
            encoded.append((char) (32 + random.nextInt(95)));
        }

        return encoded.toString();
    }

    /**
     * Decodes a string encoded using `encode()` using the given key.
     *
     * @param str String to decode.
     * @param key Key to use when decoding the string.
     * @return The decoded string, or `null` if it was invalid.
     */
    private static String decode(String str, int key) {
        if (str.length() % 9f != 0) return null;

        StringBuilder decoded = new StringBuilder();

        int i = 0;
        boolean isNegative = false;
        while (i < str.length()) {
            String value = str.substring(i, i + 7);

            int toSub = isNegative ? key : -key;
            isNegative = !isNegative;

            char actual = (char) ((Integer.parseInt(value) - toSub) / 31);
            decoded.append(actual);

            i += 9;
        }

        return decoded.toString();
    }

    /**
     * Pads a string to a given length with leading zeros.
     *
     * @param str String to pad.
     * @param expectedLength The length to pad to.
     * @return The padded string.
     */
    private static String padStart(String str, int expectedLength) {
        int delta = expectedLength - str.length();
        if (delta >= 0) return "0".repeat(delta) + str;
        else return str;
    }
}
