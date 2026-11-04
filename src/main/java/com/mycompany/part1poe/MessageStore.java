/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1poe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



/**
 *Holds the arrays for POE Part 3 and everything that works on them:
 * searching, deleting, reporting, and reading/writing the JSON file of
 * stored messages.
 * 
 * 
 * @author Student
 */
public class MessageStore {
    //Flags describing what happend to a message
    public static final String SENT = "Sent";
    public static final String STORED = "Stored";
    public static final String DISREGARDED = "Disregarded";
    
    //The most messages each array can hold.
    private static final int MAX_MESSAGES = 100;
    
   // The three arrays the rubric asks for. Each one holds the text of the messages that were sent, disregarded or stored, and has a counter that
   // says how many of its positions are in use
    private static String[] sentMessages = new String[MAX_MESSAGES];
    private static int sentCount = 0;
    private static String[] disregardedMessages = new String[MAX_MESSAGES];
    private static int disregardedCount = 0;
    private static String[] storedMessages = new String[MAX_MESSAGES];
    private static int storedCount = 0;
    
    // Parallel arrays: position 0 in every one of these describes the first message, position 1 the second, and so on. This lets a search in one
    // array (e.g. the hashes) find the matching value in another (e.g. the message text). The message ID and message hash arrays hold the ID and
    // hash of EVERY message, whatever its flag.
    private static String[] messageIDs = new String[MAX_MESSAGES];
    private static String[] messageHashes = new String[MAX_MESSAGES];
    private static int[] messageNumbers = new int[MAX_MESSAGES];
    private static String[] messageSenders = new String[MAX_MESSAGES];
    private static String[] messageRecipients = new String[MAX_MESSAGES];
    private static String[] messageTexts = new String[MAX_MESSAGES];
    private static String[] messageFlags = new String[MAX_MESSAGES];
    private static int totalCount = 0;
    
    // The logged in user, recorded as the sender of every new message.
    private static String currentSender = "Unknown";

    // The JSON file that stored messages are written to and read from.
    private static String storageFile = "message.json";

    public static void setSender(String sender) {
        currentSender = sender;
    }

    public static void setStorageFile(String path) {
        storageFile = path;
    }

    // Empties every array by setting each counter back to zero. Does not touch the JSON file on disk.
    public static void reset() {
        sentCount = 0;
        disregardedCount = 0;
        storedCount = 0;
        totalCount = 0;
    }

    // Adds a message created by the logged in user to the arrays. Stored messages are also written to the JSON file straight away.
    public static void addMessage(String id, int number, String hash,
            String recipient, String text, String flag) {
        boolean added = addEntry(id, number, hash, currentSender, recipient, text, flag);
        if (added && flag.equals(STORED)) {
            saveStoredMessages();
        }
    }

    // Puts one message into the parallel arrays and into the array for its flag. Used both for new messages and for messages read from JSON.
    // Returns false if the arrays are full.
    private static boolean addEntry(String id, int number, String hash,
            String sender, String recipient, String text, String flag) {
        if (totalCount >= MAX_MESSAGES) {
            System.out.println("The message arrays are full - this message was not saved.");
            return false;
        }
    
        messageIDs[totalCount] = id;
        messageNumbers[totalCount] = number;
        messageHashes[totalCount] = hash;
        messageSenders[totalCount] = sender;
        messageRecipients[totalCount] = recipient;
        messageTexts[totalCount] = text;
        messageFlags[totalCount] = flag;
        totalCount++;

        if (flag.equals(SENT)) {
            sentMessages[sentCount] = text;
            sentCount++;
        } else if (flag.equals(STORED)) {
            storedMessages[storedCount] = text;
            storedCount++;
        } else {
            disregardedMessages[disregardedCount] = text;
            disregardedCount++;
        }
        return true;
    }

    // The getters return a copy that only contains the positions in use.
    public static String[] getSentMessages() {
        return Arrays.copyOf(sentMessages, sentCount);
    }

    public static String[] getDisregardedMessages() {
        return Arrays.copyOf(disregardedMessages, disregardedCount);
    }

    public static String[] getStoredMessages() {
        return Arrays.copyOf(storedMessages, storedCount);
    }

    public static String[] getMessageHashes() {
        return Arrays.copyOf(messageHashes, totalCount);
    }

    public static String[] getMessageIDs() {
        return Arrays.copyOf(messageIDs, totalCount);
    }

    // Looks through the first "count" positions of an array and returns the
    // position of the value, or -1 if it is not there.
    private static int findPosition(String[] array, int count, String value) {
        for (int i = 0; i < count; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    // Removes a value from an array by moving every later value one place
    // to the left. Returns the new count.
    private static int removeText(String[] array, int count, String value) {
        int position = findPosition(array, count, value);
        if (position == -1) {
            return count;
        }
        for (int i = position; i < count - 1; i++) {
            array[i] = array[i + 1];
        }
        return count - 1;
    }

    // Menu option a: the sender and recipient of every stored message.
    public static String displaySenderAndRecipient() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals(STORED)) {
                result.append("Sender: ").append(messageSenders[i])
                        .append(" | Recipient: ").append(messageRecipients[i])
                        .append("\n");
            }
        }
        if (result.length() == 0) {
            return "There are no stored messages.";
        }
        return result.toString();
    }

    // Menu option b: the longest stored message.
    public static String getLongestStoredMessage() {
        String longest = "";
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        if (longest.isEmpty()) {
            return "There are no stored messages.";
        }
        return longest;
    }
    
    // Menu option c: searches the message ID array and returns the
    // recipient (position 0) and message (position 1) at the same position
    // in the parallel arrays, or null if the ID is not found.
    public static String[] searchByMessageID(String id) {
        int position = findPosition(messageIDs, totalCount, id);
        if (position == -1) {
            return null;
        }
        return new String[]{messageRecipients[position], messageTexts[position]};
    }

    // Menu option d: every sent or stored message for one recipient.
    public static String[] searchByRecipient(String recipient) {
        String[] found = new String[totalCount];
        int foundCount = 0;
        for (int i = 0; i < totalCount; i++) {
            boolean sentOrStored = messageFlags[i].equals(SENT)
                    || messageFlags[i].equals(STORED);
            if (sentOrStored && messageRecipients[i].equals(recipient)) {
                found[foundCount] = messageTexts[i];
                foundCount++;
            }
        }
        return Arrays.copyOf(found, foundCount);
    }

    // Menu option e: finds the message with this hash in the hash array and
    // removes it from every array. If it was a stored message, the JSON file
    // is updated too.
    public static String deleteByHash(String hash) {
        int position = findPosition(messageHashes, totalCount, hash);
        if (position == -1) {
            return "Message hash not found.";
        }
    
     String text = messageTexts[position];
        String flag = messageFlags[position];

        // Move every later message one place to the left in every parallel
        // array, so the gap left by the deleted message is closed.
        for (int i = position; i < totalCount - 1; i++) {
            messageIDs[i] = messageIDs[i + 1];
            messageNumbers[i] = messageNumbers[i + 1];
            messageHashes[i] = messageHashes[i + 1];
            messageSenders[i] = messageSenders[i + 1];
            messageRecipients[i] = messageRecipients[i + 1];
            messageTexts[i] = messageTexts[i + 1];
            messageFlags[i] = messageFlags[i + 1];
        }
        totalCount--;

        if (flag.equals(SENT)) {
            sentCount = removeText(sentMessages, sentCount, text);
        } else if (flag.equals(STORED)) {
            storedCount = removeText(storedMessages, storedCount, text);
            saveStoredMessages();
        } else {
            disregardedCount = removeText(disregardedMessages, disregardedCount, text);
        }

        return "Message: \"" + text + "\" successfully deleted.";
    }

    // Menu option f (and the sent report): the full details of every message
    // with the given flag.
    public static String generateReport(String flag) {
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals(flag)) {
                report.append("Message ID: ").append(messageIDs[i]).append("\n");
                report.append("Message Hash: ").append(messageHashes[i]).append("\n");
                report.append("Recipient: ").append(messageRecipients[i]).append("\n");
                report.append("Message: ").append(messageTexts[i]).append("\n");
                report.append("Sender: ").append(messageSenders[i]).append("\n\n");
            }
        }
        if (report.length() == 0) {
            return "There are no " + flag.toLowerCase() + " messages.";
        }
        return report.toString();
    }

    // Writes every stored message to the JSON file as a JSON array.
    //
    // References (writing text to a file with java.nio, and the JSON format):
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html
    // https://www.baeldung.com/java-json
    public static void saveStoredMessages() {
        StringBuilder json = new StringBuilder("[\n");
        boolean firstEntry = true;
        for (int i = 0; i < totalCount; i++) {
            if (messageFlags[i].equals(STORED)) {
                if (!firstEntry) {
                    json.append(",\n");
                }
                json.append("  {")
                        .append("\"messageID\":\"").append(escape(messageIDs[i])).append("\",")
                        .append("\"messageNumber\":").append(messageNumbers[i]).append(",")
                        .append("\"sender\":\"").append(escape(messageSenders[i])).append("\",")
                        .append("\"recipient\":\"").append(escape(messageRecipients[i])).append("\",")
                        .append("\"message\":\"").append(escape(messageTexts[i])).append("\",")
                        .append("\"messageHash\":\"").append(escape(messageHashes[i])).append("\"")
                        .append("}");
                firstEntry = false;
            }
        }
        json.append("\n]\n");

        try {
            Files.write(Paths.get(storageFile), json.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Could not store messages to the JSON file: " + e.getMessage());
        }
    }

    // Reads the JSON file back into the arrays as stored messages and
    // returns how many were loaded. Messages already in the arrays (same
    // message ID) are not added twice.
    //
    // Reference (reading a text file line by line with java.nio):
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html
    public static int loadStoredMessages() {
        Path file = Paths.get(storageFile);
        if (!Files.exists(file)) {
            return 0;
        }
    
    
}
