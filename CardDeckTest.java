import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import static org.junit.Assert.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {

    @Test
    void testGetDeckIndex() {
        CardDeck cardDeck = new CardDeck(1);
        assertEquals(1, cardDeck.getDeckIndex());
    }

    @Test
    void testDeckAsString() {
        CardDeck cardDeck = new CardDeck(1);
        ArrayList<Card> testDeck = new ArrayList<>();
        testDeck.add(new Card(1));
        testDeck.add(new Card(2));
        testDeck.add(new Card(3));
        testDeck.add(new Card(4));

        // Call the setHand method
        cardDeck.setDeck(testDeck);

        String result = cardDeck.deckAsString();
        // Check if the hand is set correctly
        assertEquals("1 2 3 4 ", result);
    }

    @Test
    void testTakeCard() {
        CardDeck cardDeck = new CardDeck(1);
        ArrayList<Card> testDeck = new ArrayList<>();
        testDeck.add(new Card(1));
        testDeck.add(new Card(2));
        testDeck.add(new Card(3));
        testDeck.add(new Card(4));

        // Call the setHand method
        cardDeck.setDeck(testDeck);
        assertEquals(testDeck.get(0), cardDeck.takeCard());
    }
}
