import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

/**
 * The Player class represents a player in a card game.
 * Each player its own attributes and methods to assist game play.
 * It implements the Runnable interface for concurrent execution in a separate thread.
 */
public class Player implements Runnable{
    /**
     * The index of the player.
     */
    private final int playerIndex;

    /**
     * The hand of cards held by the player.
     */
    private ArrayList<Card> hand;

    /**
     * The left adjacent card deck.
     */
    private final CardDeck leftDeck;

    /**
     * The right adjacent card deck.
     */
    private final CardDeck rightDeck;

    /**
     * A flag indicating whether the player has won the game.
     */
    private boolean hasWon;

    /**
     * The name of the output file associated with the player.
     */
    private final String outputFile;

    /**
     * The CardGame instance associated with the player.
     */
    private static CardGame cardGame;

    /**
     * Constructs a new player with the specified parameters.
     *
     * @param playerIndex the index of the player
     * @param leftDeck the left adjacent card deck
     * @param rightDeck the right adjacent card deck
     * @param cardGame the CardGame instance associated with the player
     */
    public Player(int playerIndex, CardDeck leftDeck, CardDeck rightDeck, CardGame cardGame) {
        this.playerIndex = playerIndex;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
        this.hand = new ArrayList<>();
        this.hasWon = false;
        this.outputFile = "player"+ playerIndex + "_output.txt";
        try{
            File f = new File(this.outputFile);
            f.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write("");
        } catch (IOException e){
            System.out.println("Failed to create an output file for player" + playerIndex);
        }
        this.cardGame = cardGame;
    }

    /**
     * Gets the index of the player.
     *
     * @return the index of the player
     */
    public int getPlayerIndex() {
        return playerIndex;
    }


    /**
     * Sets the initial hand of cards for the player.
     *
     * @param startHand the initial hand of cards
     */
    public void setHand(ArrayList<Card> startHand) {
        this.hand = startHand;
    }


    /**
     * Converts the player's hand to a string representation.
     *
     * @return a string containing the face values of the cards in the player's hand
     */
    public String handAsString (){
        String sHand = "";
        for(Card card : hand){
            sHand = sHand + card.faceValue() + " ";
        }
        return sHand;
    }

    /**
     * Logs an action to the player's output file.
     *
     * @param action the action to be logged
     */
    private synchronized void logAction(String action) {
        try (FileWriter writer = new FileWriter(outputFile, true)) {
            writer.write(action + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
    }

    /**
     * Writes the player's initial hand to the output file, using the logAction method.
     */
    public void writeInitialHand(){
        String action = "player " + playerIndex + " initial hand " + handAsString();
        logAction(action);
    }

    /**
     * Writes the player's current hand to the output file, using the logAction method.
     */
    private synchronized void writeCurrentHand(){
        String action = "player "+ playerIndex +" current hand is "+ handAsString();
        logAction(action);
    }

    /**
     * Writes the action of a player drawing a card from a deck to the output file, using the logAction method.
     * @param card the face value of the drawn card
     * @param deck the index of the deck from which the card is drawn
     */
    private synchronized void writeDraw(int card, int deck){
        String action = "player "+ playerIndex +" draws a "+ card + " from deck " + deck;
        logAction(action);
    }

    /**
     * Writes the action of discarding a card to the output file, using the logAction method.
     *
     * @param card the face value of the discarded card
     * @param deck the index of the deck to which the card is discarded
     */
    private synchronized void writeDiscard(int card, int deck){
        String action = "player "+ playerIndex +" discards a "+ card + " to deck " + deck;
        logAction(action);
    }

    /**
     * Writes the final hand of the player to the output file, using the logAction method.
     *
     * @param playerIndexOfWinner the index of the winning player
     */
    public synchronized void writeFinalHand(int playerIndexOfWinner){
        String action;
        if (hasWon){
            action = "player " + playerIndex + " wins\nplayer " + playerIndex + " exits\nplayer " + playerIndex + " final hand " + handAsString();
        }
        else {
            action = "player " + playerIndexOfWinner + " has informed player " + playerIndex + " that player " + playerIndexOfWinner + " has won\nplayer " + playerIndex + " exits\nplayer " + playerIndex + " final hand " + handAsString();
        }
        logAction(action);
    }

    /**
     * Simulates the player discarding a card from their hand.
     *
     * @return the discarded card
     */
    public synchronized Card dropCard() {
        int cardIndexToRemove = 0;
        Card discardedCard = null;
        for (Card card : hand) {
            if (card.faceValue() != playerIndex) {
                discardedCard = card;
                break;
            }
            cardIndexToRemove++;
        }
        hand.remove(cardIndexToRemove);
        return discardedCard;
    }

    /**
     * Simulates the player picking up a card and adding it to their hand.
     *
     * @param card the card to be picked up
     */
    public synchronized void pickUpCard(Card card) {
        hand.add(card);
    }

    /**
     * Checks if the player's hand constitutes a winning hand.
     *
     * @return {@code true} if the player has a winning hand, {@code false} otherwise
     */
    public synchronized boolean checkForWinningHand() {
        Card firstCard = hand.get(0);
        int firstValue = firstCard.faceValue();
        for (int i = 1; i < hand.size(); i++) {
            Card currentCard = hand.get(i);
            if (currentCard.faceValue() != firstValue) {
                // If any value is different, return false
                return false;
            }
        }
        return true;
    }



    @Override
    public void run() {
        // Loops until a player has won
        while (cardGame.isGameInProgress()) {
            // Simulate drawing a card from the left deck
            Card drawnCard = leftDeck.takeCard();
            writeDraw(drawnCard.faceValue(), leftDeck.getDeckIndex());
            pickUpCard(drawnCard);

            // Simulate discarding a card to the right deck
            Card discardedCard = dropCard();
            rightDeck.addCard(discardedCard);
            writeDiscard(discardedCard.faceValue(), rightDeck.getDeckIndex());

            writeCurrentHand();

            // Check for a winning hand
            if (checkForWinningHand()) {
                hasWon = true;
                cardGame.stopGame(playerIndex);
                break;
            }

        }
    }




}



