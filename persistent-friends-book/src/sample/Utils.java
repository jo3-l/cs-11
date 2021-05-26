package sample;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Utils {
    // 3 numbers, then a hyphen, then 3 numbers, then a hyphen, and finally 4 numbers.
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
    // Any characters, then an '@', then any characters, then a ., and finally any characters.
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^.]+\\..+");

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean isEmailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isBirthdayValid(LocalDate birthday) {
        return birthday.isBefore(LocalDate.now());
    }

    public static String getOrdinalRepresentation(int num) {
        String numStr = Integer.toString(num);
        int cent = num % 100;
        int dec = num % 10;

        // 10s are always -th
        if (cent >= 10 && cent <= 19) return numStr + "th";

        switch (dec) {
            case 1:
                return numStr + "st";
            case 2:
                return numStr + "nd";
            case 3:
                return numStr + "rd";
            default:
                return numStr + "th";
        }
    }
}
