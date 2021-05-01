public class Main {
    public static int problemOne(String str) {
        // Problem one: Count the number of vowels in the string `str`.
        //
        // This solution simply iterates through all characters in `str`. If the current character is one of 'a', 'e',
        // 'i', 'o', or 'u', a counter is incremented. At the end of iteration we return the value of this counter.
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
        // Problem two: Count the number of occurrences of 'bob' within the string `str`.
        // This was the most fun problem for me personally -- this question is essentially a small twist on the age-old
        // problem of finding a substring within a string. One way to solve this is the so-called 'naive' algorithm.
        // This maintains a sliding window of length |S| (where S is the substring) during matching and compares it
        // with the substring until there are no more characters in the string left. Java's `String#indexOf()` uses this
        // approach. The slight problem with this method is that it takes O(mn) time to execute, where *m* is the length of
        // the substring and *n* is the length of the string. For small substrings / strings, this is not a huge issue,
        // but as the string lengths grow larger, the time taken may sometimes become an issue.
        //
        // This naive approach would work just fine for this question, given the small substring length. However, I thought
        // I'd try writing something better, based off my prior knowledge of string-matching algorithms.
        //
        // The approach I opted to take is a form of a deterministic finite automata, requiring constant space and N iterations
        // where N is the length of the string. It is essentially an encoding of the DFA variant of the Knuth-Muller-Pratt
        // algorithm, without needing any extra space.
        int bobCount = 0;

        // Current state of the machine.
        // 0 is the start state.
        // 1 is the state for when the first 'b' has matched.
        // 2 is the state for when the 'o' has matched.
        // 3 is a virtual state for when the second 'b' has matched. This causes the counter to be incremented and immediately afterwards
        // the automaton transitions to the first state.
        int state = 0;
        loop: for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (state) {
                case 0:
                    if (c == 'b') state = 1; // transition to state 1 (first 'b' matched)
                    continue loop; // otherwise remain on state 0
                case 1:
                    if (c == 'o') state = 2; // transition to state 2 ('o' matched)
                    else if (c != 'b') state = 0; // transition back to the start state if the character isn't 'b'
                    continue loop; // otherwise, if the character is still 'b', remain on state 1
                case 2:
                    if (c == 'b') {
                        // second 'b' matched, meaning that we found a 'bob', so we're now in the virtual state 3
                        ++bobCount;
                        // transition back to state 1 since the current character is a 'b'
                        state = 1;
                    } else {
                        state = 0; // transition back to state 0
                    }
            }
        }

        return bobCount;
    }

    public static String problemThree(String str) {
        // Fast path for empty string.
        if (str.isEmpty()) return "";

        // Longest substring that matches the criteria given.
        String longest = str.substring(0, 1);
        // String builder representing the current substring.
        StringBuilder buffer = new StringBuilder(longest);

        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            // Check if current character is before the previous character in the alphabet.
            if (c < str.charAt(i - 1)) {
                // If so, see if the current substring is longer than the current longest one. If so, set the current longest
                // substring to the current one.
                if (buffer.length() > longest.length()) longest = buffer.toString();
                // Clear the current substring.
                buffer.setLength(0);
            }
            buffer.append(c);
        }

        // Check if the content in the buffer is longer than the current longest substring, if so, update the current longest
        // substring value.
        if (buffer.length() > longest.length()) longest = buffer.toString();

        return longest;
    }
}
