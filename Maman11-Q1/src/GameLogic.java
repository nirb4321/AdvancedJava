import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameLogic {
    private String targetNumber;
    private int numOfGuesses;
    private ArrayList<String> guessHistory;

    // Constructor
    public GameLogic() {
        this.targetNumber = generateRandomString();
        this.numOfGuesses = 0; 
        this.guessHistory = new ArrayList<>();
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
    }

    public int getNumOfGuesses() {
        return numOfGuesses;
    }

    public void setNumOfGuesses(int numOfGuesses) {
        this.numOfGuesses = numOfGuesses;
    }

    public ArrayList<String> getGuessHistory() {
        return guessHistory;
    }

    public void addGuess(String guess) {
        guessHistory.add(guess);
        numOfGuesses++;
    }
    
    // Generate a 4 letter string without duplicate characters
    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        Set<Character> usedChars = new HashSet<>();

        while (result.length() < 4) {
            int randomNumber = random.nextInt(10); // Generate a random number from 0 to 9
            char letter = (char) ('A' + randomNumber); // Map 0-9 to 'A'-'J'
            
            // Add the letter only if it's not already in the result
            if (usedChars.add(letter)) {
                result.append(letter);
            }
        }

        return result.toString();
    }
}
