/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1poe;



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
    
    
    
    
    
    
    
}
