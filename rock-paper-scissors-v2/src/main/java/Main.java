import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Constants for the long names of the possible choices.
        String rock = "rock";
        String paper = "paper";
        String scissors = "scissors";

        // A map of choice -> choice it beats.
        Map<String, String> winningChoices = new HashMap<>();
        winningChoices.put(rock, scissors); // Rock beats scissors.
        winningChoices.put(scissors, paper); // Scissors beats paper.
        winningChoices.put(paper, rock); // Paper beats rock.

        // Messages for different outcomes.
        String winMessage = "You Win!";
        String drawMessage = "Draw!";
        String loseMessage = "You Lose!";

        // The format string for information about the computer & player choice, to be passed to the `printf` function.
        String choiceInfoFormatStr = "Computer Choice: %s  Player choice: %s\n";
        // The format string for information displayed at the start of the round.
        String roundStartFormatStr = "Wins: %d Losses: %d\nPlayers Choice:\n";

        // Message sent when the user first starts the program.
        String initialMessage = "Choose rock paper or scissors. 'r' for rock, 'p' for paper and 's' for scissors. To exit choose 'x'";
        // Message sent if the user's input was invalid.
        String invalidInputMessage = "Invalid selection please play again.";
        // Message sent when the game ends.
        String gameEndMessage = "Thank you for playing!";
        // A banner of asterisks used to separate different messages we display.
        String banner = "*".repeat(50);

        Scanner scanner = new Scanner(System.in);

        // Counters for wins/losses from the perspective of the user.
        int userWins = 0;
        int userLosses = 0;

        // Send the initial message.
        System.out.println(initialMessage);
        System.out.println(banner);

        while (true) {
            // Display the round start message.
            System.out.printf(roundStartFormatStr, userWins, userLosses);
            // Retrieve the next line the user types.
            String input = scanner.nextLine();

            // Map the user's input (short names of the choices, i.e. 'r'), to the long name of the choice (i.e. 'rock').
            String userChoice;
            if (input.equals("r")) userChoice = rock;
            else if (input.equals("p")) userChoice = paper;
            else if (input.equals("s")) userChoice = scissors;
            else if (input.equals("x")) {
                System.out.println(gameEndMessage);
                // Stop code execution at this point so no code beyond this point is executed.
                return;
            } else {
                // If we've gotten to this else branch, then the input the user sent was invalid. Tell them to try again.
                System.out.println(invalidInputMessage);
                System.out.println(banner);
                // Continue to the next iteration of the game loop.
                continue;
            }

            // Generate a random integer from 0-2, inclusive.
            int computerChoiceInt = (int) (Math.random() * 3);
            // Map the integer to the long name of the choice it represents.
            String computerChoice;
            if (computerChoiceInt == 0) computerChoice = rock;
            else if (computerChoiceInt == 1) computerChoice = paper;
            else computerChoice = scissors;

            if (winningChoices.get(userChoice).equals(computerChoice)) {
                // Check whether the user's choice wins, using the `winningChoices` Map we constructed earlier.
                System.out.println(winMessage);
                // Add one to the user win counter.
                ++userWins;
            } else if (userChoice.equals(computerChoice)) {
                // If they're the same, then it's a draw.
                System.out.println(drawMessage);
            } else {
                // Otherwise, it's a loss.
                System.out.println(loseMessage);
                // Add one to the user losses counter.
                ++userLosses;
            }

            // Print out some information about the computer & user choice.
            System.out.printf(choiceInfoFormatStr, computerChoice, userChoice);
            System.out.println(banner);
        }
    }
}
