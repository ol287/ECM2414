import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * The CardGame class represents the main class for a card game.
 * It manages the players, card decks, and game flow.
 */
public class CardGame {
    /**
     * List of players participating in the game.
     */
    private static ArrayList<Player> listPlayers = new ArrayList<>();

    /**
     * List of card decks used in the game.
     */
    private static ArrayList<CardDeck> listCardDecks = new ArrayList<>();

    /**
     * List of individual cards used in the game.
     */
    public static ArrayList<Card> cards = new ArrayList<>();

    /**
     * Number of players in the game.
     */
    public static int noOfPlayers;

    /**
     * Flag indicating whether the game is in progress.
     */
    private volatile boolean gameInProgress = true;

    /**
     * List of the threads associated with each player.
     */
    private static ArrayList<Thread> playerThreads = new ArrayList<>();


    /**
     * Main method to start the card game.
     * Takes user input for the number of players and the filename of the card pack,
     * initialises the game components, deals the cards, and starts player threads.
     */
    public static void main(String[] args) {
        // Uses scanner to retrieve user input
        Scanner scanner = new Scanner(System.in);
        noOfPlayers = getValidNumPlayers(scanner);
        String fileName = getValidFileName(scanner);
        scanner.close();

        // Created new instance of CardGame
        CardGame cardGame = new CardGame();

        // Creates the rights amount of players and decks
        createPlay(cardGame);

        // Reads game pack from the given file and validates it
        readPackFromFile(fileName);

        // Deals the cards out to the players and decks in a round-robin style
        deal();


        // Output player's initial hand to external files
        for (Player player : listPlayers) {
            player.writeInitialHand();
        }

        // Create and start a thread for each player
        for (Player player : listPlayers) {
            Thread playerThread = new Thread(player);
            playerThreads.add(playerThread);
            playerThread.start();
        }

        // Ensure threads run concurrently and simultaneously
        for (Thread playerThread : playerThreads) {
            try {
                playerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Stops the game, writes deck contents to files, assigns a winner ID, and terminates all player threads.
     */
    public synchronized void stopGame(int winnerID) {
        gameInProgress = false;
        // End of game outputs
        for (Player player : listPlayers) {
            player.writeFinalHand(winnerID);
        }
        for (CardDeck cardDeck : listCardDecks) {
            cardDeck.writeDeck();
        }
    }

    /**
     * Checks if the game is still in progress.
     *
     * @return {@code true} if the game is in progress, {@code false} otherwise
     */
    public boolean isGameInProgress() {
        return gameInProgress;
    }


    /**
     * Prompts the user to enter the number of players and validates the input.
     * Keeps prompting until a valid positive integer is entered.
     *
     * @param scanner the Scanner object for user input
     * @return the validated number of players entered by the user
     */
    public static int getValidNumPlayers(Scanner scanner) {
        while (true) {
            System.out.print("Please enter the number of players: ");
            if (scanner.hasNextInt()) {
                int numPlayers = scanner.nextInt();
                // A valid number of players is one that is bigger than 1
                if (numPlayers > 1) {
                    scanner.nextLine();
                    return numPlayers;
                } else {
                    System.out.println("Number of players must be greater than 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Prompts the user to enter the location of the card pack file and validates the input.
     * Keeps prompting until a non-empty string is entered.
     *
     * @param scanner the Scanner object for user input
     * @return the validated filename entered by the user
     */
    public static String getValidFileName(Scanner scanner) {
        while (true) {
            System.out.print("Please enter the location of pack to load: ");
            String fileName = scanner.nextLine();
            // Only accepts a not empty sting for the file name
            if (fileName != null && !fileName.isEmpty()) {
                return fileName;
            } else {
                System.out.println("Invalid filename. Please enter a non-empty string.");
            }
        }
    }

    /**
     * Validates the input card pack based on specific criteria.
     * Checks the length, range, and occurrence of card values in the pack.
     *
     * @return {@code true} if the card pack is valid, {@code false} otherwise
     */
    public static Boolean validateInputPack() {
        int expectedLength = 8 * noOfPlayers;
        int maxNumber = 2 * noOfPlayers;
        ArrayList<Integer> cardValues = new ArrayList<>();;
        for (Card card : cards) {
            cardValues.add(card.faceValue());
        }
        // Invalid length
        if (cardValues.size() != expectedLength) {
            return false;
        }
        // Test for number out of range
        boolean[] foundNumbers = new boolean[maxNumber + 1];
        for (int number : cardValues) {
            if (number < 1 || number > maxNumber) {
                return false;
            }
            foundNumbers[number] = true;
        }
        // Test for missing numbers
        for (int i = 1; i <= maxNumber; i++) {
            if (!foundNumbers[i]) {
                return false;
            }
        }
        // Check if each number appears exactly four times
        int[] count = new int[maxNumber + 1];
        for (int number : cardValues) {
            count[number]++;
        }
        // Test for numbers appearing the wrong number of times
        for (int i = 1; i <= maxNumber; i++) {
            if (count[i] != 4) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reads card values from a file and creates Card objects, populating the 'cards' list.
     * Validates the input pack using the 'validateInputPack' method.
     *
     * @param fileName the name of the file containing card values
     */
    public static void readPackFromFile(String fileName) {
        // Reads each value from the file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                int value = Integer.parseInt(line.trim());
                // Creates a Card object for each value
                cards.add(new Card(value));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Check that the pack is valid
        if (!validateInputPack()) {
            System.out.println("Invalid pack. There can be no winner.");
        }
    }

    /**
     * Creates objects for card decks and players based on the number of players.
     * Populates the 'listCardDecks' and 'listPlayers' lists.
     *
     * @param cardGame the CardGame instance associated with the created players
     */
    public static void createPlay(CardGame cardGame) {
        // Creates decks
        for (int i = 1; i <= noOfPlayers; i++) {
            listCardDecks.add(new CardDeck(i));
        }
        // Create players
        for (int i = 1; i <= noOfPlayers; i++) {
            // Set the decks either side of the player
            CardDeck leftDeck = listCardDecks.get(i - 1);
            CardDeck rightDeck = listCardDecks.get(i % noOfPlayers);
            // Creates new instance of the Player class
            Player player = new Player(i, leftDeck, rightDeck, cardGame);
            listPlayers.add(player);
        }
    }

    /**
     * Deals cards to players and card decks based on a round-robin method.
     * Initialises the hands of players and the contents of card decks.
     */
    public static void deal() {
        // Deal players cards
        for (Player player : listPlayers) {
            int playerID = player.getPlayerIndex();
            ArrayList<Card> startHand = new ArrayList<>();
            int loopCount = 0;
            // Round-robin style
            for (int i = 1; i <= 4; i++) {
                Card cardToAdd = cards.get((playerID + (4 * loopCount)) - 1);
                startHand.add(cardToAdd);
                loopCount++;
            }
            player.setHand(startHand);
        }

        // Get remaining cards that have not been dealt
        ArrayList<Card> remainingCards;
        remainingCards = new ArrayList<>(cards.subList(noOfPlayers * 4, cards.size()));

        // Deal card deck cards
        for (CardDeck deck : listCardDecks) {
            int deckID = deck.getDeckIndex();
            ArrayList<Card> startDeck = new ArrayList<>();
            int loopCount = 0;
            // Round-robin style
            for (int i = 1; i <= 4; i++) {
                Card cardToAdd = remainingCards.get((deckID + (4 * loopCount)) - 1);
                startDeck.add(cardToAdd);
                loopCount++;
            }
            deck.setDeck(startDeck);
        }
    }
}















