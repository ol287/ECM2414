import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The CardDeck class represents a deck of cards in a card game.
 * Each deck has an index indicating its position in the game, and it contains a list of cards.
 * It provides methods to manipulate and retrieve information about the deck.
 */
public class CardDeck {
    /**
     * The index of the deck in the game.
     */
    private final int deckIndex;
    /**
     * The list of cards in the deck.
     */
    private final ArrayList<Card> deck;

    /**
     * Constructs a new CardDeck instance with the specified deck index.
     * It creates an empty ArrayList for the cards to be later stored in.
     *
     * @param deckID the index of the deck in the game
     */
    public CardDeck(int deckID) {
        this.deck = new ArrayList<>();
        deckIndex = deckID;
    }

    /**
     * Gets the index of the deck in the game.
     *
     * @return the index of the deck
     */
    public int getDeckIndex() {
        return deckIndex;
    }


    /**
     * Sets the initial cards of the deck.
     *
     * @param startDeck the list of cards to set as the initial deck
     */
    public void setDeck (ArrayList<Card> startDeck){
        deck.addAll(startDeck);
    }

    /**
     * Returns a string representation of the deck, used in the output file.
     *
     * @return a string containing the face values of the cards in the deck
     */
    public String deckAsString (){
        String sDeck = "";
        for(Card card : deck){
            sDeck = sDeck + card.faceValue() + " ";
        }
        return sDeck;
    }

    /**
     * Deletes the top card from the deck.
     *
     * @return the top card from the deck
     */
    public synchronized Card takeCard(){
        Card topCard = deck.get(0);
        deck.remove(0);
        return topCard;
    }

    /**
     * Adds a card to the bottom of the deck.
     *
     * @param card the card to be added to the deck
     */
    public synchronized void addCard(Card card){
        deck.add(card);
    }

    /**
     * Writes the contents of the deck to a file.
     * The file is named "deck[deckIndex]_output.txt".
     */
    public synchronized void writeDeck(){
        String fileName = "deck"+deckIndex+"_output.txt";
        try{
            // Create a new file with the specified file name
            File myObj = new File(fileName);
            myObj.createNewFile();
        } catch (IOException e){
            // Handle IOException if there are issues creating the file
            System.out.println("IOException");
        }
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write("deck"+deckIndex+" contents: "+ deckAsString());
            myWriter.write("\n");
            // Close the FileWriter
            myWriter.close();
        }catch (IOException e){
            // Handle IOException if there are issues writing to the file
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}
