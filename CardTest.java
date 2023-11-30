import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class CardTest {

    @Test
    public void testConstructor() {
        int expectedFaceValue = 10;
        Card card = new Card(expectedFaceValue);
        assertEquals(expectedFaceValue, card.faceValue());
    }

    @Test
    public void testDefaultConstructor() {
        Card card = new Card(0);
        assertEquals(0, card.faceValue());
    }

    @Test
    public void testGetFaceValue() {
        int expectedFaceValue = 2;
        Card card = new Card(expectedFaceValue);
        assertEquals(expectedFaceValue, card.faceValue());
    }
}
