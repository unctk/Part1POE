/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1poe;

import java.util.Random;

/**
 *
 * @author Student
 */
public class Message {
    
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
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
         String firstWord = words[0].replaceAll("[^a-zA-Z0-9]", "");
         String lastWord = words[words.length-1].replaceAll("[^a-zA-Z0-9]", "");
         
         return idPrefix + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase();
     }
    //  Lets the user choose whether to send, store, or disregard the message, and returns the matching confirmation message.
     public String sentMessage(String choice) {
         if (choice.equalsIgnoreCase("send")) {
             MessageStore.addMessage(messageID, messageNumber, messageHash, recipient, messageText, MessageStore.SENT);
            return "Message successfully sent.";
        } 
         else if (choice.equalsIgnoreCase("store")) {
            storeMessage();
            return "Message successfully stored.";
        } 
         else if (choice.equalsIgnoreCase("disregard")) {
              MessageStore.addMessage(messageID, messageNumber, messageHash, recipient, messageText, MessageStore.DISREGARDED);
            return "Press 0 to delete the message.";
        } 
         else {
            return "Invalid option selected.";
        }
       }
     
     // Own defined storeMessage() method. Adds this message to the stored messages array; MessageStore then writes every stored message to the JSON file.
     // https://www.baeldung.com/java-json
     public void storeMessage() {
         MessageStore.addMessage(messageID, messageNumber, messageHash, recipient, messageText, MessageStore.STORED); 
     }
     // Returns the details of every message that was sent while the program is running, in the order: Message ID, Message Hash, Recipient, Message.
    public static String printMessages() {
        return MessageStore.generateReport(MessageStore.SENT);
    }
        
    // Returns the total number of messages sent.
    public static int returnTotalMessages() {
        return MessageStore.getSentMessages().length;
    }
    
    // Empties all the message arrays - used between JUnit tests so one test's messages don't leak into another's.
    public static void resetForTesting() {
        MessageStore.reset();
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
