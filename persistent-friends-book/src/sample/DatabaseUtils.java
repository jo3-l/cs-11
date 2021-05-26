package sample;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtils {
    public static List<Friend> readFile(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader
                    .lines()
                    .filter(line -> !line.isEmpty()) // safeguard against empty lines, i.e. at the end of text
                    .map(SerializationUtils::deserializeFriend) // deserialize line into a Friend object
                    .collect(Collectors.toList()); // return as list
        }
    }

    public static void writeFile(String file, List<Friend> friends) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Friend friend : friends) {
                writer.append(SerializationUtils.serializeFriend(friend)).append("\n");
            }
        }
    }
}
