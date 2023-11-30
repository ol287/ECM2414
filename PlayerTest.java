import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void playerIndexAsOne() {
        Player player = new Player(1,new CardDeck(1),new CardDeck(2), new CardGame());
        assertEquals (1,player.getPlayerIndex());
    }


    @Test
    void testHandAsString() {
        Player player = new Player(2, new CardDeck(2), new CardDeck(3), new CardGame());
        // Create a sample hand of cards
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Call the setHand method
        player.setHand(testHand);

        String result = player.handAsString();
        // Check if the hand is set correctly
        assertEquals("1 2 3 4 ", result);
    }

    @Test
    void testDropCard() {
        Player player = new Player(3, new CardDeck(3), new CardDeck(4), new CardGame());
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));

        // Call the setHand method
        player.setHand(testHand);
        assertEquals(testHand.get(0), player.dropCard());
    }

    @Test
    void testCheckForWinningHandFalse() {
        Player player = new Player(4, new CardDeck(4), new CardDeck(1), new CardGame());
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(1));
        testHand.add(new Card(2));
        testHand.add(new Card(3));
        testHand.add(new Card(4));
        player.setHand(testHand);
        assertEquals(false, player.checkForWinningHand());
    }

    @Test
    void testCheckForWinningHandTrue() {
        Player player = new Player(4, new CardDeck(4), new CardDeck(1), new CardGame());
        ArrayList<Card> testHand = new ArrayList<>();
        testHand.add(new Card(4));
        testHand.add(new Card(4));
        testHand.add(new Card(4));
        testHand.add(new Card(4));
        player.setHand(testHand);
        assertEquals(true, player.checkForWinningHand());
    }

}