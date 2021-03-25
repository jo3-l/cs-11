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
        //
        // Firstly, here is another way of doing this, akin to maintaining a sliding window of text and comparing it to
        // the string 'bob' until we hit the end.
        //
        // int bobCount = 0;
        // for (int i = 0; i < str.length(); i++) {
        //  if (str.startsWith("bob", i)) ++bobCount;
        // }
        //
        // However, this solution is (ever so slightly) inefficient. It tries to find 'bob' at the current index and
        // always backtracks. To provide an example of where we can improve, given the string 'bobob', once we
        // match 'bob' at the first position we can immediately skip to the 3rd character, as the second character is 'o'
        // and 'bob' starts with a 'b'. The solution above, however, still checks the second character.
        //
        // In contrast, the below solution does not backtrack at all. It handles characters one-by-one in the loop, only
        // ever moving forwards, similar to the Knuth-Morris-Pratt algorithm for matching substrings within strings. It
        // resembles a deterministic finite automata with 3 states: 0, 1, and 2, but counts occurrences instead of accepting
        // input. See comments below for further explanation.
        int bobCount = 0;

        // Current state of the automaton. One of 0, 1 or 2.
        // State 0 means we are matching the first 'b' in 'bob'.
        // State 1 means we are matching the 'o' in 'bob'.
        // State 2 means we are matching the second 'b' in 'bob'. If this is successful, we increment the counter.
        int state = 0;
        loop: for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (state) {
                case 0:
                    // If the current character is 'b', then move on to the next state (1), which tries to match 'o'.
                    // Otherwise, stay on state 0 - try to match another 'b' next iteration.
                    if (c == 'b') state = 1;
                    continue loop;
                case 1:
                    // If the current character is 'o', then move on to the next state (2), which tries to match the last
                    // 'b'.
                    if (c == 'o') state = 2;
                    // Otherwise, if the current character isn't a 'b', return to state 0 and attempt matching another 'b'.
                    // However, if it is a 'b', remain on state 1 and try to match another 'o' after the current 'b'.
                    else if (c != 'b') state = 0;
                    continue loop;
                case 2:
                    // If the current character is 'b', then increment `bobCount` as we just found an occurrence of 'bob'
                    // within the string. Then, return to state 1 and attempt matching another 'o' after the current 'b'.
                    if (c == 'b') {
                        ++bobCount;
                        state = 1;
                    } else {
                        // If we were not successful in matching a 'b', then return to state 0 and try matching 'b' again.
                        state = 0;
                    }
            }
        }

        return bobCount;
    }

    public static String problemThree(String str) {
        // Problem three: Find the longest substring within String `str` that is in alphabetical order.
        // In short, this solution iterates over all characters of the string. It keeps track of the current substring
        // and the longest substring that match the criteria given. Once a character that causes the current substring
        // to fall out of alphabetical order is reached, it checks whether the length of the string is greater than the
        // longest substring. If so, it sets the longest substring to the current substring, clears the current substring,
        // and moves on.
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
