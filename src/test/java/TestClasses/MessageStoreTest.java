/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasses;

import com.mycompany.part1poe.Message;
import com.mycompany.part1poe.MessageStore;

import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

/**
 *Unit tests for POE Part 3 (the arrays, searching, deleting, reporting and
 * JSON storage in MessageStore), using the test data from the rubric.
 * 
 * @author Student
 */
public class MessageStoreTest {
    
    // The text of each test message, kept in variables so the tests can
    // refer to them without repeating the strings.
    private static final String MESSAGE_1 = "Did you get the cake?";
    private static final String MESSAGE_2 = "Where are you? You are late! I have asked you to be on time.";
    private static final String MESSAGE_3 = "Yohoooo, I am at your gate.";
    private static final String MESSAGE_4 = "It is dinner time!";
    private static final String MESSAGE_5 = "Ok, I am leaving without you.";

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        // Start every test with empty arrays and a temporary JSON file.
        Message.resetForTesting();
        MessageStore.setStorageFile(tempDir.resolve("messages.json").toString());
        MessageStore.setSender("Developer");

        // Populate the arrays with the rubric's test data, using the same
        // code the application uses (no hard-coded arrays).
        message1 = new Message(0, "+27834557896", MESSAGE_1);
        message1.sentMessage("send");

        message2 = new Message(1, "+27838884567", MESSAGE_2);
        message2.sentMessage("store");

        message3 = new Message(2, "+27834484567", MESSAGE_3);
        message3.sentMessage("disregard");

        message4 = new Message(3, "0838884567", MESSAGE_4);
        message4.sentMessage("send");

        message5 = new Message(4, "+27838884567", MESSAGE_5);
        message5.sentMessage("store");
    }

    // --- Sent Messages array correctly populated ---

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        String[] expected = {MESSAGE_1, MESSAGE_4};
        String[] actual = MessageStore.getSentMessages();
        assertArrayEquals(expected, actual, "Expect the sent messages array to "
                + "contain messages 1 and 4");
    }

    @Test
    public void testDisregardedMessagesArrayCorrectlyPopulated() {
        String[] expected = {MESSAGE_3};
        String[] actual = MessageStore.getDisregardedMessages();
        assertArrayEquals(expected, actual, "Expect the disregarded messages "
                + "array to contain message 3");
    }

    @Test
    public void testStoredMessagesArrayCorrectlyPopulated() {
        String[] expected = {MESSAGE_2, MESSAGE_5};
        String[] actual = MessageStore.getStoredMessages();
        assertArrayEquals(expected, actual, "Expect the stored messages array "
                + "to contain messages 2 and 5");
    }

    @Test
    public void testMessageHashAndIDArraysContainEveryMessage() {
        String[] expectedHashes = {message1.getMessageHash(),
                message2.getMessageHash(), message3.getMessageHash(),
                message4.getMessageHash(), message5.getMessageHash()};
        String[] expectedIDs = {message1.getMessageID(),
                message2.getMessageID(), message3.getMessageID(),
                message4.getMessageID(), message5.getMessageID()};

        assertArrayEquals(expectedHashes, MessageStore.getMessageHashes(),
                "Expect the hash array to contain the hash of every message");
        assertArrayEquals(expectedIDs, MessageStore.getMessageIDs(),
                "Expect the ID array to contain the ID of every message");
    }
    
     // --- Display the longest message ---

    @Test
    public void testDisplayLongestMessage() {
        String actual = MessageStore.getLongestStoredMessage();
        assertEquals(MESSAGE_2, actual, "Expect the longest stored message "
                + "to be message 2");
    }

    // --- Search for messageID ---

    @Test
    public void testSearchForMessageID() {
        String[] actual = MessageStore.searchByMessageID(message4.getMessageID());
        assertEquals("0838884567", actual[0], "Expect the recipient of "
                + "message 4 to be returned");
        assertEquals(MESSAGE_4, actual[1], "Expect the text of message 4 "
                + "to be returned");
    }

    @Test
    public void testSearchForMessageID_NotFound() {
        assertNull(MessageStore.searchByMessageID("0000000000"),
                "Expect a message ID that does not exist to return nothing");
    }

    // --- Search all the messages sent or stored for a recipient ---

    @Test
    public void testSearchMessagesForRecipient() {
        String[] expected = {MESSAGE_2, MESSAGE_5};
        String[] actual = MessageStore.searchByRecipient("+27838884567");
        assertArrayEquals(expected, actual, "Expect messages 2 and 5 for "
                + "+27838884567");
    }

    // --- Delete a message using a message hash ---

    @Test
    public void testDeleteMessageUsingHash() {
        String expected = "Message: \"" + MESSAGE_2 + "\" successfully deleted.";
        String actual = MessageStore.deleteByHash(message2.getMessageHash());
        assertEquals(expected, actual, "Expect message 2 to be deleted");

        assertArrayEquals(new String[]{MESSAGE_5}, MessageStore.getStoredMessages(),
                "Expect message 2 to be gone from the stored messages array");
    }

    @Test
    public void testDeleteMessageUsingHash_NotFound() {
        assertEquals("Message hash not found.",
                MessageStore.deleteByHash("00:0:NOTHING"),
                "Expect an unknown hash to report that it was not found");
    }

    // --- Display report ---

    @Test
    public void testDisplayReportOfSentMessages() {
        String expected = report(message1) + report(message4);
        String actual = MessageStore.generateReport(MessageStore.SENT);
        assertEquals(expected, actual, "Expect the report to list every "
                + "sent message with its ID, hash, recipient and message");
    }

    @Test
    public void testDisplayReportOfStoredMessages() {
        String expected = report(message2) + report(message5);
        String actual = MessageStore.generateReport(MessageStore.STORED);
        assertEquals(expected, actual, "Expect the report to list every "
                + "stored message");
    }

    // --- Reading the JSON file into the array ---

    @Test
    public void testStoredMessagesReadBackFromJSONFile() {
        // Empty the arrays, as if the program had just been started again.
        MessageStore.reset();
        assertEquals(0, MessageStore.getStoredMessages().length);

        int loaded = MessageStore.loadStoredMessages();

        assertEquals(2, loaded, "Expect both stored messages to be loaded");
        assertArrayEquals(new String[]{MESSAGE_2, MESSAGE_5},
                MessageStore.getStoredMessages(),
                "Expect the stored messages array to be filled from the file");
        assertArrayEquals(new String[]{message2.getMessageHash(), message5.getMessageHash()},
                MessageStore.getMessageHashes(),
                "Expect the hashes to be read back from the file too");
    }

    @Test
    public void testDeletedMessageIsRemovedFromJSONFile() {
        MessageStore.deleteByHash(message2.getMessageHash());

        MessageStore.reset();
        MessageStore.loadStoredMessages();

        assertArrayEquals(new String[]{MESSAGE_5}, MessageStore.getStoredMessages(),
                "Expect the deleted message to be gone from the file as well");
    }

    // Builds the report text expected for one message, in the same format
    // MessageStore.generateReport() uses.
    private String report(Message message) {
        return "Message ID: " + message.getMessageID() + "\n"
                + "Message Hash: " + message.getMessageHash() + "\n"
                + "Recipient: " + message.getRecipient() + "\n"
                + "Message: " + message.getMessageText() + "\n"
                + "Sender: Developer\n\n";
    }
    
}
