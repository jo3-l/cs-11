package sample;

import java.time.LocalDate;

public class SerializationUtils {
    // very rare character that separates the fields of the friend object
    private static final char fieldSeparator = 0xbeef;

    public static String serializeFriend(Friend friend) {
        String birthdayPart = friend.getBirthday() != null
                ? friend.getBirthday().toString()
                : "";

        // format: <name><sep><phone><sep><email><sep><birthday>
        return friend.getName() +
                fieldSeparator +
                friend.getPhone() +
                fieldSeparator +
                friend.getEmail() +
                fieldSeparator +
                birthdayPart;
    }

    public static Friend deserializeFriend(String data) {
        // use -1 as the limit rather than the default to make sure trailing empty strings (the birthday field)
        // are not discarded
        String[] parts = data.split(Character.toString(fieldSeparator), -1);

        Friend friend = new Friend(parts[0], parts[1], parts[2], null);
        // empty birthday => no birthday, so only set if it isn't empty
        if (!parts[3].isEmpty()) {
            friend.setBirthday(LocalDate.parse(parts[3]));
        }

        return friend;
    }
}
