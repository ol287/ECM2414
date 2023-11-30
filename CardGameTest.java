import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CardGameTest {

    @Test
    public void testGetValidFileName() {
        // Set up the input stream with the desired test cases
        String[] testInputs = {"", "\n", "valid_filename.txt\n"};
        InputStream inputStream = new ByteArrayInputStream(String.join("\n", testInputs).getBytes());
        Scanner mockScanner = new Scanner(inputStream);

        // Test the method with the mocked scanner
        String fileName = CardGame.getValidFileName(mockScanner);

        // Assert that the returned filename is the expected one
        assertEquals("valid_filename.txt", fileName);
    }

    @Test
    public void testIsGameInProgress() {
        // Test when the game is in progress
        CardGame cardGameInProgress = new CardGame();
        assertTrue(cardGameInProgress.isGameInProgress());

        // Test when the game is stopped
        CardGame cardGameStopped = new CardGame();
        cardGameStopped.stopGame(1);
        assertFalse(cardGameStopped.isGameInProgress());
    }

    @Test
    public void testGetValidNumPlayers() {
        // Test when a valid number of players is entered
        Scanner mockScanner1 = new Scanner(new ByteArrayInputStream("3\n".getBytes()));
        assertEquals(3, CardGame.getValidNumPlayers(mockScanner1));

        // Test when an invalid input (non-integer) is entered first, then a valid number
        Scanner mockScanner2 = new Scanner(new ByteArrayInputStream("invalid\n4\n".getBytes()));
        assertEquals(4, CardGame.getValidNumPlayers(mockScanner2));

        // Test when an invalid input (negative number) is entered first, then a valid number
        Scanner mockScanner3 = new Scanner(new ByteArrayInputStream("-2\n5\n".getBytes()));
        assertEquals(5, CardGame.getValidNumPlayers(mockScanner3));

        // Test when zero is entered (invalid)
        Scanner mockScanner4 = new Scanner(new ByteArrayInputStream("0\n2\n".getBytes()));
        assertEquals(2, CardGame.getValidNumPlayers(mockScanner4));

        // Test when a valid number is entered but with additional invalid input
        Scanner mockScanner5 = new Scanner(new ByteArrayInputStream("invalid\n-5\n3\n".getBytes()));
        assertEquals(3, CardGame.getValidNumPlayers(mockScanner5));
    }

    @Test
    public void testValidateInputPack_NumberOutOfRange() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(4);
        CardGame.cards.add(card1);
        CardGame.cards.add(card2);
        CardGame.cards.add(card3);
        CardGame.noOfPlayers = 2;
        Boolean result = CardGame.validateInputPack();
        assertFalse(result);
    }

    @Test
    public void testValidateInputPack_InvalidLength() {
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        CardGame.cards.add(card1);
        CardGame.cards.add(card2);
        CardGame.cards.add(card3);
        CardGame.noOfPlayers = 4;
        Boolean result = CardGame.validateInputPack();
        assertFalse(result);
    }

    @Test
    public void testValidateInputPack_NumberMissing() {
        Card card1 = new Card(1);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Card card4 = new Card(4);
        Card card5 = new Card(5);
        Card card6 = new Card(6);
        Card card7 = new Card(7);
        Card card8 = new Card(8);
        Card card9 = new Card(9);
        Card card10 = new Card(10);
        Card card11 = new Card(11);
        Card card12 = new Card(12);
        Card card13 = new Card(13);
        Card card14 = new Card(14);
        Card card15 = new Card(15);
        Card card16 = new Card(16);
        CardGame.cards.add(card1);
        CardGame.cards.add(card2);
        CardGame.cards.add(card3);
        CardGame.cards.add(card4);
        CardGame.cards.add(card5);
        CardGame.cards.add(card6);
        CardGame.cards.add(card7);
        CardGame.cards.add(card8);
        CardGame.cards.add(card9);
        CardGame.cards.add(card10);
        CardGame.cards.add(card11);
        CardGame.cards.add(card12);
        CardGame.cards.add(card13);
        CardGame.cards.add(card14);
        CardGame.cards.add(card15);
        CardGame.cards.add(card16);
        CardGame.noOfPlayers = 4;
        Boolean result = CardGame.validateInputPack();
        assertFalse(result);
    }













}

