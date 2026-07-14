import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class DecodeLabs_Java_P1 {

    // ANSI Color Codes for Terminal UI
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;

        System.out.println(CYAN + "=======================================" + RESET);
        System.out.println(PURPLE + "   WELCOME TO DECODELABS NUMBER GAME   " + RESET);
        System.out.println(CYAN + "=======================================" + RESET);

        // Session Loop (Allows multiple rounds)
        while (playAgain) {
            // Generate stochastic number (1-100)
            int targetNumber = random.nextInt(100) + 1;
            int maxAttempts = 7; // Binary search optimization limit
            int attempts = 0;
            boolean hasWon = false;

            System.out.println("\n" + YELLOW + "I have generated a number between 1 and 100." + RESET);
            System.out.println(YELLOW + "Can you guess it? You have " + maxAttempts + " attempts!" + RESET);

            // Live Game Loop
            while (attempts < maxAttempts) {
                System.out.print("\nEnter your guess: ");
                int userGuess = 0;

                // Defensive Engineering: Input Validation using try-catch
                try {
                    userGuess = scanner.nextInt();
                    scanner.nextLine(); // Fix the 'Scanner Trap' / Flush buffer
                } catch (InputMismatchException e) {
                    // Red error message for invalid text input
                    System.out.println(RED + "❌ ERROR: Invalid input! Please enter a valid integer." + RESET);
                    scanner.nextLine(); // Flush buffer after exception
                    continue; 
                }

                attempts++;

                // Feedback Logic with Color Coding
                if (userGuess == targetNumber) {
                    hasWon = true;
                    break;
                } else if (userGuess > targetNumber) {
                    System.out.println(RED + "📉 Too High! Try a smaller number. (Attempts left: " + (maxAttempts - attempts) + ")" + RESET);
                } else {
                    System.out.println(RED + "📈 Too Low! Try a bigger number. (Attempts left: " + (maxAttempts - attempts) + ")" + RESET);
                }
            }

            // Round End State Evaluation
            if (hasWon) {
                // Accurate/Success state in Green
                int score = (maxAttempts - attempts + 1) * 10;
                System.out.println("\n" + GREEN + "🎉 ACCURATE! You guessed the correct number in " + attempts + " attempts." + RESET);
                System.out.println(GREEN + "🏆 Your Score for this round: " + score + "/70" + RESET);
            } else {
                System.out.println("\n" + RED + "💥 Game Over! You've run out of attempts." + RESET);
                System.out.println(YELLOW + "The correct number was: " + targetNumber + RESET);
            }

            // Session Persistence Prompt
            System.out.print("\nDo you want to play another round? (Y/N): ");
            String response = scanner.next().trim().toLowerCase();
            scanner.nextLine(); // Clear buffer

            if (!response.equals("y")) {
                playAgain = false;
            }
        }

        System.out.println("\n" + PURPLE + "Thank you for playing! Engine shutting down cleanly." + RESET);
        scanner.close();
    }
}