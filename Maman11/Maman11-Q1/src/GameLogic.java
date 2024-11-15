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
    
    public void setGuessHistory( ArrayList<String> guessHistory) {
        this.guessHistory= guessHistory;
    }

    public void addGuess(String guess) {
        guessHistory.add(guess);
        numOfGuesses++;
    }
    
    // Generate a 4 letter string without duplicate characters
    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        Set<Integer> usedNumbers = new HashSet<>();

        while (result.length() < 4) {
            int randomNumber = random.nextInt(10); // Generate a random number from 0 to 9
            
            // Add the number only if it's not already in the result
            if (usedNumbers.add(randomNumber)) {
                result.append(randomNumber);
            }
        }

        return result.toString();
    }
    
    
    public boolean CheckInput(String guess) {
    	
    	return guess.equals(targetNumber);
    }
    
    //print the number of bools and hits
    public void printDifferences(String guess) {
    	int bools = 0;
    	int hits = 0;
    	
    	for (int i = 0; i < targetNumber.length(); i++) {
            char targetChar = targetNumber.charAt(i);
            char inputChar = (i < guess.length()) ? guess.charAt(i) : '\0'; // Check to avoid IndexOutOfBounds

            if (inputChar == targetChar) {
            	bools++;
            } else if (guess.indexOf(targetChar) != -1) {
            	hits++;
            }
        }
    	
    	System.out.println("Bools: " + bools);
    	System.out.println("Hits: " + hits);
    }
    
    // Check if guess is valid according the the rules: 4 uniqe digits
    public boolean isValidGuess(String guess) {
        // Check if guess has exactly 4 characters
        if (guess.length() != 4) {
            return false;
        }

        // Use a Set to check for duplicates and ensure only digits are used
        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : guess.toCharArray()) {
            if (!Character.isDigit(c)) { // Check if it's a digit
                return false;
            }
            if (!uniqueDigits.add(c)) { // Check if the digit is unique
                return false;
            }
        }

        return true; // Guess is valid if all checks passed
    }
    
}
