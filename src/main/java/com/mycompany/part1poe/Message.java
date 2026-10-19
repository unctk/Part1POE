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
    
    
    
    
    
}
