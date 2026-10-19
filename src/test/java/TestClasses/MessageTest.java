/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasses;

import com.mycompany.part1poe.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Student
 */
public class MessageTest {
    
   @BeforeEach
    public void setUp() {
        // Reset static state (sent messages list and the message counter) before every test, so tests don't affect each other.
        Message.resetForTesting();
    }
    
    // The message should not be more than 250 characters
    
    @Test
    public void testMessageLength_Success() {
        String message = "Hi Mike, can you join us for dinner tonight?";
        String expected = "Message ready to send.";
        String actual = Message.checkMessageLength(message);
        assertEquals(expected, actual, "Expect a message under 250 "+ "characters to be ready to send");
    }
    
    @Test
    public void testMessagesLength_Failure() {
        // Build a message that is 5 characters over the 250 limit.
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            longMessage.append("a");
        }
        
        String expected = "Message exceeds 250 characters by 5; please reduce the size.";
        String actual = Message.checkMessageLength(longMessage.toString());
        assertEquals(expected, actual, "Expect a message over 250 " + "characters to report exactly how many characters over it is");
    }
    // The recipient number is correctly formatted and (Reuses the same check used for Part 1's cell phone number).
    
    @Test
    public void testRecipientCell_Success() {
        String expected = "Cell phone number successfully captured.";
        String actual = Message.checkRecipientCell("+27718693002");
        assertEquals(expected, actual, "Expect a correctly formatted "
                + "recipient number to be captured");
    }
    @Test
    public void testRecipientCell_Failure() {
        String expected = "Cell phone number is incorrectly formatted or "
                + "does not contain an international code. Please correct "
                + "the number and try again.";
        String actual = Message.checkRecipientCell("08575975889");
        assertEquals(expected, actual, "Expect a recipient number without "
                + "an international code to be rejected");
    }
    
    // Message hash is correct
    }
}
