/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1poe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.io.FileWriter;

/**
 *
 * @author Student
 */
public class Message {
    
    // Keeps every message that was actually chosen to be SENT during this run of the program, so printMessages() can list them and returnTotalMessages() can count them.
    private static List<Message> sentMessages = new ArrayList<>();
    
     // Counts messages as they are CREATED (not just sent) - this is the "Num messages sent" value the rubric describes as starting at a
    // number and auto-incrementing as more messages are entered. It is used as the message number inside the message hash.
    private static int messageCounter = 0;
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageNumber = messageCounter;
        messageCounter++;
        this.messageHash = createMessageHash(this.messageID, this.messageNumber, this.messageText);
    }
    
    // Generates a random 10-digit message ID, used to uniquely track each message.
    private static String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
        }  
    
    //Returns the confirmation message once a messages ID has been generated
    public String checkMessageIDCreated() {
        return "Message ID generated: " + this.messageID;
              }
    
    // Ensures that the message ID is not more than ten characters.
    public static boolean checkMessageID(String messageID) {
        if (messageID == null) {
            return false;
        }
        return messageID.length() <=10;
    }
     //Ensures that the recipient cell number is no more than ten characters long and starts with an international code.
     public static String checkRecipientCell(String cellNumber) {
        if (Login.checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number successfully captured.";
        } 
        else {
            return "Cell phone number is incorrectly formatted or does not " + "contain an international code. Please correct the " + "number and try again.";
        }
    }
     
     // Checks the message is no more than 250 characters long, not a named method
     public static String checkMessageLength(String messageText) {
         if (messageText.length() <= 250) {
            return "Message ready to send.";
     }
         else {
             int excess = messageText.length()-250;
             return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
         }
     }
     // Creates and returns the Message Hash: the first two digits of the message ID, a colon, the message number, a colon, and the first and last words 
     // of the message, all in capitals.
     public static String createMessageHash(String messageID, int messageNumber, String messageText) {
         String idPrefix = messageID.substring(0, 2);
         
         String[] words = messageText.trim().split("\\s+");
         String firstWord = words[0].replaceAll("[^a-zA-z0-9]", "");
         String lastWord = words[words.length-1].replaceAll("[^a-zA-z0-9]", "");
         
         return idPrefix + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
     }
    //  Lets the user choose whether to send, store, or disregard the message, and returns the matching confirmation message.
     public String sentMessage(String choice) {
         if (choice.equalsIgnoreCase("send")) {
            sentMessages.add(this);
            return "Message successfully sent.";
        } 
         else if (choice.equalsIgnoreCase("store")) {
            storeMessage();
            return "Message successfully stored.";
        } 
         else if (choice.equalsIgnoreCase("disregard")) {
            return "Press 0 to delete the message.";
        } 
         else {
            return "Invalid option selected.";
        }
       }
     
     //// Own defined storeMessage() method. Appends this message to messages.json as a simple JSON array entry.
    //  https://www.baeldung.com/java-json
     public void storeMessage() {
         String json = "{" + "\"messageID\":\"" + messageID + "\","
                    + "\"messageNumber\":" + messageNumber + ","
                    + "\"recipient\":\"" + recipient + "\","
                    + "\"message\":\"" + messageText.replace("\"", "\\\"") + "\","
                    + "\"messageHash\":\"" + messageHash + "\"" + "}";
         
         try (FileWriter writer = new FileWriter("message.json", true)) {
              writer.write(json + System.lineSeparator());
         }
         catch (IOException e) {
             System.out.println("Could not store message to JSON file: " + e.getMessage());
         }
     }
    
     // Returns the details of every message that was sent while the program is running, in the order: Message ID, Message Hash, Recipient, Message.
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m: sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }
    // Returns the total number of messages sent.
    public static int returnTotalMessages() {
        return sentMessages.size();
    }
    
    // Resets all static state - used between JUnit tests so one test's messages don't leak into another's counts.
    public static void resetForTesting() {
        sentMessages.clear();
        messageCounter = 0;
    }
    
    public String getMessageID() {
        return messageID;
    }
    
    public int getMessageNumber() {
        return messageNumber;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public String getMessageHash() {
        return messageHash;
        
    }
}
