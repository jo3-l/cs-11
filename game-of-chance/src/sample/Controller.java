package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ComboBox<GuessKind> higherLowerComboBox;
    public Button cashOutBtn;
    public Button rollDiceBtn;
    public Label currentStateLabel;
    public Label currentWinningsLabel;
    public Label roundNumberLabel;
    public Label totalWinningsLabel;
    public Label currentNumberLabel;

    private int currentNumber;
    private double currentWinChance;
    private int currentRound;
    private int currentWinnings;
    private int totalWinnings;

    private final Random random = new Random();

    public void handleRollDice(ActionEvent actionEvent) {
        GuessKind guess = higherLowerComboBox.getSelectionModel().getSelectedItem();
        if (guess == null) {
            setLabelText(currentStateLabel, "You need to select a guess.");
            return;
        }

        updateCurrentWinChance();
        int N = genInt(guess);

        boolean won = (N > currentNumber && guess.equals(GuessKind.HIGHER))
                || (N < currentNumber && guess.equals(GuessKind.LOWER));
        if (won) {
            setLabelText(currentStateLabel, "You won! I chose " + N + ", while the previous number was " + currentNumber + ". With another round, you can win $" + (currentRound + 1) * 5 + " more.");
            currentNumber = N;
            currentWinnings += currentRound * 5;
            currentRound++;

            refreshDynamicFields();
        } else {
            setLabelText(currentStateLabel, "You didn't quite get it. I chose " + N + ", while the previous number was " + currentNumber + ". Let's move onto another game, shall we?");
            beginNewGame(true);
        }
    }

    public void handleCashOut(ActionEvent actionEvent) {
        setLabelText(currentStateLabel, "Smart move to cash out early...");
        beginNewGame(false);
    }

    private int genInt(GuessKind guess) {
        double p = random.nextDouble();

        if (p <= currentWinChance) {
            if (guess.equals(GuessKind.HIGHER)) {
                if (currentNumber == 6) return randIntBetween(1, 6);
                int begin = currentNumber + 1;
                return randIntBetween(begin, 6);
            }

            if (currentNumber == 1) return randIntBetween(1, 6);
            return randIntBetween(1, currentNumber - 1);
        }

        if (guess.equals(GuessKind.HIGHER)) {
            return randIntBetween(1, currentNumber);
        }

        return randIntBetween(currentNumber, 6);
    }

    private void updateCurrentWinChance() {
        if (currentRound < 5) currentWinChance -= 0.03;
        else if (currentRound < 10) currentWinChance -= 0.02;
        else if (currentRound < 20) currentWinChance -= 0.015;
        else currentWinChance -= 0.01;

        if (currentWinChance <= 0) currentWinChance = 0.01;
    }

    private void beginNewGame(boolean didLose) {
        if (!didLose) totalWinnings += currentWinnings;
        currentNumber = randIntBetween(1, 6);
        currentWinChance = 0.5;
        currentWinnings = 0;
        currentRound = 1;

        refreshDynamicFields();
    }

    private void refreshDynamicFields() {
        setLabelText(currentNumberLabel, "Current number: " + currentNumber);
        setLabelText(totalWinningsLabel, "Total winnings across all games: $" + totalWinnings);
        setLabelText(currentWinningsLabel, "Current winnings: $" + currentWinnings);
        setLabelText(roundNumberLabel, "Round number: " + currentRound);
    }

    private void setLabelText(Label label, String text) {
        label.setText(text);
        label.setMinWidth(Region.USE_PREF_SIZE);
        label.setAlignment(Pos.CENTER);
    }

    private int randIntBetween(int begin, int end) {
        if (begin == end) return begin;
        return begin + random.nextInt(end - begin + 1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        higherLowerComboBox.getItems().addAll(GuessKind.values());
        beginNewGame(false);
    }

    public enum GuessKind {
        HIGHER("Higher"),
        LOWER("Lower");

        private final String value;

        GuessKind(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
