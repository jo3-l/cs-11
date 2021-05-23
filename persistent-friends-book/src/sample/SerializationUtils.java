package sample;

import java.time.LocalDate;

public class SerializationUtils {
    private static final char fieldSeparator = 0xbeef;

    public static String serializeFriend(Friend friend) {
        String birthdayPart = friend.getBirthday() != null
                ? friend.getBirthday().toString()
                : "";

        return friend.getName() +
                fieldSeparator +
                friend.getPhone() +
                fieldSeparator +
                friend.getEmail() +
                fieldSeparator +
                birthdayPart;
    }

    public static Friend deserializeFriend(String data) {
        String[] parts = data.split(Character.toString(fieldSeparator), -1);

        Friend friend = new Friend(parts[0], parts[1], parts[2], null);
        if (!parts[3].isEmpty()) {
            friend.setBirthday(LocalDate.parse(parts[3]));
        }

        return friend;
    }
}
