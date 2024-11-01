import java.util.Scanner;

public class MainGame {

    public static void main(String[] args) {
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);

        while (playAgain) {
            GameLogic gl = new GameLogic();
           // System.out.println("Target Number (for testing): " + gl.getTargetNumber());
            System.out.println("Welcome to BoolsEye! Please enter your first guess:");

            // Get the initial valid guess from the user
            String userInput = getValidGuess(scanner, gl);

            
            // Main game loop for guessing
            while (!gl.CheckInput(userInput)) {
                gl.addGuess(userInput); // Add guess to history
                gl.printDifferences(userInput);
                System.out.println("Try Again:");
                userInput = getValidGuess(scanner, gl);
            }

            // Final message on successful guess
            System.out.println("Congrats! You have won the game");
            System.out.println("It took you " + gl.getNumOfGuesses() + " attempts to guess correctly.");
            System.out.println("Your Guess History: " + gl.getGuessHistory());

            // Check if the user wants to play again
            playAgain = askToPlayAgain(scanner);
        }

        System.out.println("Hope to see you soon!");
        scanner.close();
    }

    //  Get a valid guess from the user
    private static String getValidGuess(Scanner scanner, GameLogic gl) {
        String userInput = scanner.nextLine();
        gl.addGuess(userInput);
        while (!gl.isValidGuess(userInput)) {
            System.out.println("Your guess must contain 4 digits without duplicates.");
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    // Ask if the user wants to play again
    private static boolean askToPlayAgain(Scanner scanner) {
        System.out.println("Do you want to play again? Press 'Y' for yes, or any other key for no.");
        String playAgainInput = scanner.next();
        scanner.nextLine();
        return playAgainInput.equalsIgnoreCase("Y");
    }
}
