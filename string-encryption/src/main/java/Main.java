import java.util.Random;


public class Main {
    // The Random instance we use to generate random integers.
    public static final Random random = new Random();

    public static void main(String[] args) {
        String str = "Hello world, foo bar baz buz :D";
        int key = 123;

        String encoded = encode(str, key);
        System.out.println("Encoded: " + encoded);

        String decoded = decode(encoded, key);
        System.out.println("Decoded: " + decoded);
    }

    /**
     * Encodes a string using the given key.
     *
     * @param str String to encode.
     * @param key Key to use when encoding the string. This should be a positive integer less than 500,000.
     * @return The encoded string.
     */
    public static String encode(String str, int key) {
        StringBuilder encoded = new StringBuilder();

        // Short overview of the steps used for encoding a string:
        //
        // - For each character code in the string:
        //  - Multiple the character code by 31.
        //  - If the number of iterations done so far is a multiple of 2, then add the `key` to the resulting value.
        //  - Otherwise, subtract the `key` from the resulting value.
        //  - Pad the value with zeros up to 7 digits.
        //  - Append two random ASCII characters.
        boolean shouldAdd = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            int encodedChar = c * 31;
            if (shouldAdd) encodedChar += key;
            else encodedChar -= key;

            // Pad with zeros to 7 digits.
            String value = padStart(String.valueOf(encodedChar), 7);

            encoded.append(value);

            // Generate two random characters to append to the encoded string.
            encoded.append((char) (32 + random.nextInt(95)));
            encoded.append((char) (32 + random.nextInt(95)));

            shouldAdd = !shouldAdd;
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
    public static String decode(String str, int key) {
        // A string encoded using the above algorithm must have a length that is a multiple of 9. This is because each
        // character in the original string becomes 7 characters + 2 random characters in the encoded string. As such,
        // if the string's length is _not_ a multiple of 9, it is invalid.
        if (str.length() % 9f != 0) return null;

        StringBuilder decoded = new StringBuilder();

        // Current index in the string.
        int i = 0;
        boolean shouldSubtract = true;
        while (i < str.length()) {
            // Get the next 7 characters from the string.
            String value = str.substring(i, i + 7);

            int decodedChar = Integer.parseInt(value);
            if (shouldSubtract) decodedChar -= key;
            else decodedChar += key;
            decodedChar /= 31;

            decoded.append((char) decodedChar);

            shouldSubtract = !shouldSubtract;
            // Skip past the 7 characters we just read + the 2 random characters, which are useless.
            i += 9;
        }

        return decoded.toString();
    }

    /**
     * Pads a string to a given length with leading zeros.
     *
     * @param str    String to pad.
     * @param length The length to pad to.
     * @return The padded string.
     */
    public static String padStart(String str, int length) {
        // Compute the numbers of leading zeros we need to add.
        int delta = length - str.length();
        // If the string's length is greater than or equal to the target length, return the string.
        if (delta <= 0) return str;
        else return "0".repeat(delta) + str;
    }
}
