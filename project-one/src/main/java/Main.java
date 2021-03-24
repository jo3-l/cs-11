// TODO: Comments
public class Main {
    public static int problemOne(String str) {
        int vowelCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isVowel(c)) ++vowelCount;
        }

        return vowelCount;
    }

    private static boolean isVowel(char c) {
        switch (c) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }

    public static int problemTwo(String str) {
        int bobCount = 0;

        int i = 0;
        while (i <= str.length() - 3) {
            if (str.charAt(i) != 'b') {
                i += 1;
                continue;
            }

            char next = str.charAt(i + 1);
            if (next != 'o') {
                i += (next == 'b') ? 1 : 2;
                continue;
            }

            if (str.charAt(i + 2) != 'b') {
                i += 3;
                continue;
            }

            ++bobCount;
            i += 2;
        }

        return bobCount;
    }

    public static String problemThree(String str) {
        if (str.length() == 0) return "";

        String longest = str.substring(0, 1);
        StringBuilder buffer = new StringBuilder(longest);

        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < str.charAt(i - 1)) {
                if (buffer.length() > longest.length()) longest = buffer.toString();
                buffer.setLength(0);
            }
            buffer.append(c);
        }

        if (buffer.length() > longest.length()) longest = buffer.toString();

        return longest;
    }
}
