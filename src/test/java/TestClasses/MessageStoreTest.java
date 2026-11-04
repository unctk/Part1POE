/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasses;

import com.mycompany.part1poe.Message;
import com.mycompany.part1poe.MessageStore;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
    
}
