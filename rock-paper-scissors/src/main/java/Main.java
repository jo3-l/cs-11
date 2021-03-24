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

        // Various other messages displayed to the user.
        String promptMessage = "Choose rock paper or scissors. 'r' for rock, 'p' for paper and 's' for scissors.";
        String invalidInputMessage = "Invalid selection please play again.";
        String choiceInfoFormatStr = "Computer Choice: %s  Player choice: %s\n";

        Scanner scanner = new Scanner(System.in);

        System.out.println(promptMessage);

        // Retrieve the next line the user types.
        String input = scanner.nextLine();

        // Map the user's input (short names of the choices, i.e. 'r'), to the long name of the choice (i.e. 'rock').
        String userChoice;
        if (input.equals("r")) userChoice = rock;
        else if (input.equals("p")) userChoice = paper;
        else if (input.equals("s")) userChoice = scissors;
        else {
            // If we've gotten to this else branch, then the input the user sent was invalid. Tell them to try again.
            System.out.println(invalidInputMessage);
            // Stop code execution here so no code beyond this point is executed.
            return;
        }

        // Generate a random integer from 0-2, inclusive.
        int computerChoiceInt = (int) (Math.random() * 3);
        // Map the integer to the long name of the choice it represents.
        String computerChoice;
        if (computerChoiceInt == 0) computerChoice = rock;
        else if (computerChoiceInt == 1) computerChoice = paper;
        else computerChoice = scissors;

        // Check whether the user's choice wins, using the `winningChoices` Map we constructed earlier.
        if (winningChoices.get(userChoice).equals(computerChoice)) System.out.println(winMessage);
        // If they're the same, then it's a draw.
        else if (userChoice.equals(computerChoice)) System.out.println(drawMessage);
        // Otherwise, it's a loss.
        else System.out.println(loseMessage);

        System.out.printf(choiceInfoFormatStr, computerChoice, userChoice);
    }
}
