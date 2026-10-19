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
        assertEquals(expected, actual, "Expect a correctly formatted " + "recipient number to be captured");
    }
    
    @Test
    public void testRecipientCell_Failure() {
        String expected = "Cell phone number is incorrectly formatted or " + "does not contain an international code. Please correct "
                        + "the number and try again.";
        String actual = Message.checkRecipientCell("08575975889");
        assertEquals(expected, actual, "Expect a recipient number without " + "an international code to be rejected");
    }
    
    // Message hash is correct
    
    @Test
    public void testMessageHash_TestCase1() {
        // Test Case 1 data: messageID starting with "00", message number 0.
        String expected = "00:0:HITONIGHT";
        String actual = Message.createMessageHash("0012345678", 0,
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals(expected, actual, "Expect the hash to combine the " + "first two ID digits, the message number, and the "
                     + "first and last words in capitals");
    }
    
    @Test
    public void testMessageHash_TestCase2() {
        // Looping through the remainder of the test data (message 2),as the rubric asks for the hashes to be tested in a loop.
        String expected = "08:1:HIPAYMENT";
        String actual = Message.createMessageHash("0855667788", 1,
                "Hi Keegan, did you receive the payment?");
        assertEquals(expected, actual, "Expect the hash for the second " + "message to follow the same pattern");
    }
    
    // Message ID is created 

    @Test
    public void testMessageIDCreated() {
        Message message = new Message(i, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        String expected = "Message ID generated: " + message.getMessageID();
        String actual = message.checkMessageIDCreated();
        assertEquals(expected, actual, "Expect the confirmation message to " + "contain the generated message ID");
    }
    
    //  MessageSent (Send / Disregard / Store) 

    @Test
    public void testMessageSent_UserSelectedSend() {
        Message message = new Message(i, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");

        String expected = "Message successfully sent.";
        String actual = message.sentMessage("send");
        assertEquals(expected, actual, "Expect sending a message to " + "confirm it was successfully sent");
    }
    
    @Test
    public void testMessageSent_UserSelectedDisregard() {
        Message message = new Message(i, "08575975889",
                "Hi Keegan, did you receive the payment?");

        String expected = "Press 0 to delete the message.";
        String actual = message.sentMessage("disregard");
        assertEquals(expected, actual, "Expect disregarding a message to " + "prompt deletion");
    }
    
     @Test
    public void testMessageSent_UserSelectedStore() {
        Message message = new Message(i, "+27821234567",
                "Testing the store feature");

        String expected = "Message successfully stored.";
        String actual = message.sentMessage("store");
        assertEquals(expected, actual, "Expect storing a message to " + "confirm it was successfully stored");
    
    }
}
