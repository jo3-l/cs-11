package sample;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtils {
    public static List<Friend> readFile(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader
                    .lines()
                    .filter(line -> !line.isEmpty())
                    .map(SerializationUtils::deserializeFriend)
                    .collect(Collectors.toList());
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
