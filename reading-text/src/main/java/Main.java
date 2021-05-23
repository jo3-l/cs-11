import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("File to read from: ");
        String file = scanner.nextLine();
        List<String> lines = readLinesFromFile(file);

        System.out.print("Word you would like to search for: ");
        String word = scanner.nextLine();

        List<Integer> foundLines = searchForWordInLines(lines, word);
        if (foundLines.isEmpty()) {
            System.out.println("That word was not found on any lines.");
        } else {
            System.out.print("That word was found on the following lines (0-indexed): ");

            StringBuilder lineStr = new StringBuilder();
            for (int i = 0; i < foundLines.size(); i++) {
                if (i > 0) lineStr.append(", ");
                lineStr.append(foundLines.get(i));
            }

            System.out.println(lineStr);
        }
    }

    public static List<String> readLinesFromFile(String file) throws IOException {
        List<String> lines = new ArrayList<>();

        FileInputStream in = new FileInputStream(file);
        StringBuilder currentLine = new StringBuilder();
        for (int c = in.read(); c != -1; c = in.read()) {
            if (c == '\n') {
                lines.add(currentLine.toString());
                currentLine.setLength(0);
            } else {
                currentLine.append((char) c);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    public static List<Integer> searchForWordInLines(List<String> lines, String word) {
        List<Integer> foundLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(word)) {
                foundLines.add(i);
            }
        }

        return foundLines;
    }
}
