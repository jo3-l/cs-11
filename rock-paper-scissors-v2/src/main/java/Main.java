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

        // A banner of asterisks used to separate different messages.
        String separator = "*".repeat(50);

        // How many wins and losses the user has.
        int userWins = 0;
        int userLosses = 0;

        System.out.println("Choose rock paper or scissors. 'r' for rock, 'p' for paper and 's' for scissors. To exit choose 'x'");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        // Main game loop. We will break out of this loop when the user sends an 'x' to exit.
        while (true) {
            System.out.println("Wins: " + userWins + " Losses: " + userLosses);

            // Retrieve the next line the user types.
            String input = scanner.nextLine();

            // Map the user's input (short names of the choices, i.e. 'r'), to the long name of the choice (i.e. 'rock').
            String userChoice;
            if (input.equals("r")) {
                userChoice = rock;
            } else if (input.equals("p")) {
                userChoice = paper;
            } else if (input.equals("s")) {
                userChoice = scissors;
            } else if (input.equals("x")) {
                System.out.println("Thank you for playing!");
                // Stop code execution at this point so no code beyond this point is executed.
                return;
            } else {
                // If we've gotten to this branch, then the input the user sent was invalid; tell them to try again.
                System.out.println("Invalid selection please play again.");
                System.out.println(separator);
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

            // Check whether the user's choice wins, using the `winningChoices` Map we constructed earlier.
            if (winningChoices.get(userChoice).equals(computerChoice)) {
                System.out.println("You Win!");
                ++userWins;
            } else if (userChoice.equals(computerChoice)) {
                // If they're the same, then it's a draw.
                System.out.println("Draw!");
            } else {
                // Otherwise, it's a loss.
                System.out.println("You Lose!");
                ++userLosses;
            }

            System.out.println("Computer Choice: " + computerChoice + "  Player choice: " + userChoice);
            System.out.println(separator);
        }
    }
}
