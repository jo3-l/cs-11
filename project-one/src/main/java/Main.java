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

        int state = 0;
        loop: for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (state) {
                case 0:
                    if (c == 'b') state = 1;
                    continue loop;
                case 1:
                    if (c == 'o') state = 2;
                    else if (c != 'b') state = 0;
                    continue loop;
                case 2:
                    if (c == 'b') {
                        ++bobCount;
                        state = 1;
                    } else {
                        state = 0;
                    }
            }
        }

        return bobCount;
    }

    public static String problemThree(String str) {
        if (str.isEmpty()) return "";

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
