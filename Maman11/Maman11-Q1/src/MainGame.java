import javax.swing.*;
import java.util.List;

public class MainGame {

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {	
            GameLogic gl = new GameLogic();
           // System.out.println(gl.getTargetNumber());
            // Welcome message
            JOptionPane.showMessageDialog(null, "Welcome to BullsEye! Let's start the game.");

            // Get the initial valid guess from the user
            String userInput = getValidGuess(gl);

            // Main game loop for guessing
            while (!gl.CheckInput(userInput)) {
                gl.addGuess(userInput); // Add guess to history
                String differences = getDifferences(gl, userInput);
                String history = formatGuessHistory(gl.getGuessHistory());
                JOptionPane.showMessageDialog(null, "Incorrect! Here's the feedback:\n" + differences
                        + "\n\nYour Guess History:\n" + history);
                userInput = getValidGuess(gl);
            }

            // Final message on successful guess
            String finalHistory = formatGuessHistory(gl.getGuessHistory());
            JOptionPane.showMessageDialog(null, "Congrats! You have won the game!\n"
                    + "It took you " + gl.getNumOfGuesses() + " attempts.\n\nYour Guess History:\n" + finalHistory);

            // Check if the user wants to play again
            playAgain = askToPlayAgain();
        }

        JOptionPane.showMessageDialog(null, "Thanks for playing! Hope to see you soon!");
    }

    // Get a valid guess from the user
    private static String getValidGuess(GameLogic gl) {
        String userInput;
        while (true) {
            userInput = JOptionPane.showInputDialog("Enter your guess (4 unique digits):");
            if (userInput == null) { // Handle cancel button
                JOptionPane.showMessageDialog(null, "Game cancelled!");
                System.exit(0);
            }
            if (gl.isValidGuess(userInput)) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Invalid guess! Your guess must contain 4 unique digits.");
        }
        return userInput;
    }

    // Generate feedback on differences
    private static String getDifferences(GameLogic gl, String guess) {
        StringBuilder feedback = new StringBuilder();
        int bulls = 0, hits = 0;

        for (int i = 0; i < gl.getTargetNumber().length(); i++) {
            char targetChar = gl.getTargetNumber().charAt(i);
            char inputChar = (i < guess.length()) ? guess.charAt(i) : '\0';

            if (inputChar == targetChar) {
                bulls++;
            } else if (guess.indexOf(targetChar) != -1) {
                hits++;
            }
        }

        feedback.append("Bulls: ").append(bulls).append("\nHits: ").append(hits);
        return feedback.toString();
    }

    // Format guess history for display
    private static String formatGuessHistory(List<String> guessHistory) {
        StringBuilder formattedHistory = new StringBuilder();
        for (int i = 0; i < guessHistory.size(); i++) {
            formattedHistory.append(i + 1).append(". ").append(guessHistory.get(i)).append("\n");
        }
        return formattedHistory.toString();
    }

    // Ask if the user wants to play again
    private static boolean askToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again?",
                JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }
}
