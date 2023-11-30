/**
 * The Card class represents a playing card with a face value.
 * Each card has a face value, which is an integer.
 */
public class Card {
    /**
     * The face value of the card.
     */
    private final int faceValue;

    /**
     * Constructs a new Card with the specified face value.
     *
     * @param faceValue the face value of the card
     */
    public Card(int faceValue) {
        this.faceValue = faceValue;
    }

    /**
     * Gets the face value of the card.
     *
     * @return the face value of the card
     */
    public int faceValue() {
        return faceValue;
    }
}
